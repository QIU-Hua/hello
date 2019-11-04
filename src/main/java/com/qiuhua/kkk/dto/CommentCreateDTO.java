package com.qiuhua.kkk.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Integer type;
    private Long  parentId;
    private  String content;
}
