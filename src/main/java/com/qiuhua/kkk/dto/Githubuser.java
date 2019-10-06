package com.qiuhua.kkk.dto;

import lombok.Data;

@Data
public class Githubuser {
    private String name ;
    private Long id;
    private String bio;
    private  String avatar_url;

    @Override
    public String toString() {
        return "Githubuser{" +
                "name='" + name + '\'' +
                ", id=" + id +

                ", bio='" + bio + '\'' +
                "avatar_url='" + avatar_url + '\''+
                '}';
    }
}
