package com.newsportal.Conntroller;

import com.newsportal.Entity.Comment;
import com.newsportal.Mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentMapper commentMapper;
    @GetMapping
    public List<Comment> GetComments(@RequestParam List<String> ID){
        if (ID.get(0).equals("all")){
            List<Comment> comments = commentMapper.selectList(null);commentMapper.selectList(null);
            return comments;
        }
        List<Comment> comments = commentMapper.selectBatchIds(ID);
        return comments;
    }
    @PostMapping
    public String CreateComment(@RequestParam int UserID,@RequestParam int NewsID,@RequestParam String Content){
        Comment comment = new Comment();
        comment.setUserID(UserID);
        comment.setNewsID(NewsID);
        comment.setContent(Content);
        commentMapper.insert(comment);
        return "Successfully Created!";
    }
    @PutMapping
    public String UpdateComment(@RequestParam String ID,@RequestParam String Content){
        Comment comment=commentMapper.selectById(ID);
        comment.setContent(Content);
        commentMapper.updateById(comment);
        return "Successfully Updated!";
    }
    @DeleteMapping
    public String DeleteComment(@RequestParam String ID){
        commentMapper.deleteById(ID);
        return "Successfully Deleted!";
    }
}
