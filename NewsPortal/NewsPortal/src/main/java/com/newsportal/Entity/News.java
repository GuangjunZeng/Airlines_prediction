package com.newsportal.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class News {
    //创建新闻属性ID, Title, Content, CoverUrl, CommentList
    private int ID;
    private String Title;
    private String CoverUrl;
    private String Type;
    private String Content;
}
