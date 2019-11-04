package com.qiuhua.kkk.dto;

import com.qiuhua.kkk.model.User;
import lombok.Data;
@Data
public class questionDTO {
    private Long id;
    private String title;
    private String description;
    private Long creatId;
    private Long gmtCreat;
    private Long gmtModified;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private  String tag;

    private  User user;

}








