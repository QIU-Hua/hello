package com.qiuhua.kkk.Model;

import lombok.Data;


@Data
public class User {
    private Integer id;
    private  String name ;
    private  String accountID;
    private  String token;
    private  long gmtCreate;
    private  long gmtModified;
    private  String avatarUrl;

}
