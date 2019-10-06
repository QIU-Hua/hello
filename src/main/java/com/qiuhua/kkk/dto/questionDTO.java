package com.qiuhua.kkk.dto;

import com.qiuhua.kkk.Model.User;
import lombok.Data;
@Data
public class questionDTO {
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
    private  User user;

}








