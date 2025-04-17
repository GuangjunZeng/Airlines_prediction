package com.newsportal.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    //创建广告属性ID, Title, Content, Type
    private int ID;
    private String Title;
    private String Content;
    private String Type;
    private int ViewCount;
}
