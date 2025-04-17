package com.newsportal.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yaml.snakeyaml.events.Event;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int ID;
    private int UserID;
    private int NewsID;
    private String Content;
}
