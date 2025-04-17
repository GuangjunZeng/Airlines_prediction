package com.newsportal.Conntroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.newsportal.Entity.Comment;
import com.newsportal.Entity.News;
import com.newsportal.Mapper.CommentMapper;
import com.newsportal.Mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
    @Autowired
    NewsMapper newsMapper;
    @GetMapping//通过ID或Type的Params进行索引，输入list返回所有对象
    public List<News> GetNews(@RequestParam(required = false) List<String> ID, @RequestParam(required = false) String Type){
        if (ID==null){
            QueryWrapper<News> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Type", Type);
            List<News> news = newsMapper.selectList(queryWrapper);
            return news;
        }
        if (ID.get(0).equals("list")){
            List<News> news = newsMapper.selectList(null);
            return news;
        }
        List<News> news = newsMapper.selectBatchIds(ID);
        return news;
    }
    @PostMapping
    public String CreateNews(@RequestParam String Title, @RequestParam String CoverUrl,@RequestParam String Type, @RequestParam String Content){
        News news = new News();
        news.setTitle(Title);
        news.setContent(Content);
        news.setCoverUrl(CoverUrl);
        news.setType(Type);
        newsMapper.insert(news);
        return "Successfully Created!";
    }
    @PutMapping
    public String UpdateUser(@RequestParam String ID,@RequestParam(required = false) String Title,@RequestParam(required = false) String CoverUrl, @RequestParam(required = false) String Type,@RequestParam(required = false) String Content){
        UpdateWrapper<News> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ID",ID);
        News news=new News();
        if (Title!=null)news.setTitle(Title);
        if (Content!=null)news.setContent(Content);
        if (CoverUrl!=null)news.setCoverUrl(CoverUrl);
        if (Type!=null)news.setType(Type);
        newsMapper.update(news,updateWrapper);
        return "Successfully Updated!";
    }
    @Autowired
    CommentMapper commentMapper;
    @GetMapping(value = "comment")//返回Params输入ID对应News的所有Comment
    public List<Comment> GetComment(@RequestParam String NewsID){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NewsID",NewsID);
        List<Comment> comment = commentMapper.selectList(queryWrapper);
        return comment;
    }
}
