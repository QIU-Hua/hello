package com.qiuhua.kkk.Mapper;

import com.qiuhua.kkk.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Usermapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountID},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findbyToken(@Param("token") String token);
}