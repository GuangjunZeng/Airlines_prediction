package com.newsportal.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    //创建用户属性
    private int ID;
    private String EmailAddress;
    private String Password;
    private String UserName;
}
