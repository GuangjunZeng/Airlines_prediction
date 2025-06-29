from flask import Flask, render_template, url_for, redirect, request
from flask_sqlalchemy import SQLAlchemy
from flask_login import UserMixin, login_user, LoginManager, login_required, logout_user, current_user
from flask_wtf import FlaskForm
from wtforms import StringField, PasswordField, SubmitField
from wtforms.validators import InputRequired, Length, ValidationError
from flask_bcrypt import Bcrypt
import util
from util import OpenAIGPT
import re
import json


history = []
igpt = OpenAIGPT(keys_path="apikey.txt")

app = Flask(__name__)


db_path = 'database/test.db'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + db_path
app.config['SECRET_KEY'] = 'password'
db = SQLAlchemy(app)
bcrypt = Bcrypt(app)


# define admin and user
ADMIN_ID = 'admin'
ADMIN_PASSWORD = 'admin1234'
USER_ID = 'user'
USER_PASSWORD = 'user1234'


class LoginForm(FlaskForm):
    id = StringField('ID', validators=[InputRequired(), Length(min=4, max=15)])
    password = PasswordField('Password', validators=[InputRequired(), Length(min=8, max=80)])
    submit = SubmitField('Login')


@app.route('/')
def home():
    return render_template('home.html')


@app.route('/dashboard')
def dashboard():
    return render_template('dashboard.html')


@app.route('/wrong')
def wrong():
    return render_template('wrong.html')


@app.route('/admin_login', methods=['GET', 'POST'])
def admin_login():
    util.update(history)
    form = LoginForm()
    if form.validate_on_submit():
        if form.id.data == ADMIN_ID and form.password.data == ADMIN_PASSWORD:
            return redirect(url_for('admin_page'))
        else:
            return redirect(url_for('wrong'))
    return render_template('admin_login.html', form=form)


@app.route('/user_login', methods=['GET', 'POST'])
def user_login():
    util.update(history)
    form = LoginForm()
    if form.validate_on_submit():
        if form.id.data == USER_ID and form.password.data == USER_PASSWORD:
            return redirect(url_for('user_page'))
        else:
            return redirect(url_for('wrong'))
    return render_template('user_login.html', form=form)


@app.route('/user_page', methods=['GET', 'POST'])
def user_page():
    if request.method == 'POST':
        query_type = request.form.get('query_type')
        query = request.form.get('query')
        ai_query = request.form.get('ai_query')

        if query_type and query and ai_query:
            if query_type == "flight":
                info = util.get_flight_info(query)
            elif query_type == "airport":
                info = util.get_airport_info(query)

            ai_input = f'{info} {ai_query}'
            ai_response = igpt(ai_input)

            history.append({"role": "user", "content": ai_query})
            if info == '' or 'Error':
                history.append({"role": "error", "content": "No information found!"})
            history.append({"role": "system", "content": f"{info}"})
            history.append({"role": "assistant", "content": ai_response})

        elif query_type and query:
            if query_type == "flight":
                info = util.get_flight_info(query)
            elif query_type == "airport":
                info = util.get_airport_info(query)

            history.append({"role": "user", "content": f"Your query {query_type}: {query}"})
            if info == '' or 'Error':
                history.append({"role": "error", "content": "No information found!"})
            history.append({"role": "system", "content": f"{info}"})

        elif ai_query:
            if len(history) > 0:
                ai_input = f"这是你获得的信息{history[-1]['content']}，从这里面回答一下问题{ai_query}"
            else:
                ai_input = ai_query
            ai_response = igpt(ai_input)
            history.append({"role": "user", "content": ai_query})
            history.append({"role": "assistant", "content": ai_response})

        else:
            util.update(history)

        return render_template('user_page.html', history=history)
    return render_template('user_page.html', history=history)


@app.route('/admin_page', methods=['GET', 'POST'])
def admin_page():
    if request.method == 'POST':
        code = request.form.get('query')
        ai_query = request.form.get('ai_query')

        if code:
            status = util.operate_db(code)
            history.append({"role": "user", "content": code})
            if status == '' or 'Failed' in status:
                history.append({"role": "error", "content": {status}})
            else:
                history.append({"role": "system", "content": status})

        elif ai_query:
            if len(history) > 0:
                text = ''
                for line in range(len(history)):
                    print(history[line]['content'])
                    text += str(history[line]['content'])
                ai_input = f"{util.prompt},{text},{ai_query},You should tell me sql code with markdown format"
            else:
                ai_input = ai_query

            ai_response = igpt(ai_input)
            history.append({"role": "user", "content": ai_query})
            history.append({"role": "assistant", "content": ai_response})

        else:
            if len(history) > 0:
                text = history[-1]['content']
                try:
                    match_code = re.search(r"```sql\n(.*?)\n```", text, re.DOTALL)
                    if match_code:
                        code = match_code.group(1)
                        status = util.operate_db(code)
                        history.append({"role": "user", "content": code})
                        if status == '' or 'Failed' in status:
                            history.append({"role": "error", "content": {status}})
                        else:
                            history.append({"role": "system", "content": status})
                except:
                    history.append({"role": "error", "content": "Input your SQL code"})

        return render_template('admin_page.html', history=history)
    return render_template('admin_page.html', history=history)


@app.route('/clear_history', methods=['POST'])
def clear_history():
    util.update(history)
    return redirect(url_for('admin_page'))


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
