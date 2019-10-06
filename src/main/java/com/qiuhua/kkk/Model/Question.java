package com.qiuhua.kkk.Model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Integer creatId;
    private Long gmtCreat;
    private Long gmtModified;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private  String tag;

}
