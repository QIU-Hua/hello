package com.qiuhua.kkk.Mapper;

import com.qiuhua.kkk.Model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_creat,gmt_modified,creat_id,tag) values (#{title},#{description},#{gmt_creat},#{gmt_modified},#{creat_id},#{description})")
    void create(Question question);


    @Select("select * from question limit ${offset},${size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);


    @Select("select count(1) from question")
    Integer count();
}
