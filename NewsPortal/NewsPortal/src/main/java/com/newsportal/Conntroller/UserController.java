package com.newsportal.Conntroller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.newsportal.Mapper.UserMapper;
import com.newsportal.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @GetMapping//Postman中通过Params进行get，返回输入ID的对应User，输入list则返回所有User
    public List<User> GetUsers(@RequestParam List<String> ID){
        if (ID.get(0).equals("list")){
            List<User> users = userMapper.selectList(null);
            return users;
        }
        List<User> users = userMapper.selectBatchIds(ID);
        return users;
    }
    @PostMapping//Postman中通过Params进行create
    public String CreateUser(@RequestParam String EmailAddress,@RequestParam String Password,@RequestParam String UserName){
        User user = new User();
        user.setEmailAddress(EmailAddress);
        user.setPassword(Password);
        user.setUserName(UserName);
        userMapper.insert(user);
        return "Successfully Created!";
    }
    @PutMapping//Postman中通过Params进行update，对象为对应ID的User
    public String UpdateUser(@RequestParam String ID,@RequestParam(required = false) String EmailAddress,@RequestParam(required = false) String Password,@RequestParam(required = false) String UserName){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",ID);
        User user=new User();
        if (EmailAddress!=null)user.setEmailAddress(EmailAddress);
        if (Password!=null)user.setPassword(Password);
        if (UserName!=null)user.setUserName(UserName);
        userMapper.update(user,updateWrapper);
        return "Successfully Updated!";
    }
}