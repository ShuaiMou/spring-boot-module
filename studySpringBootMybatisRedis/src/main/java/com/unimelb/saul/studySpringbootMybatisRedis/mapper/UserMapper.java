package com.unimelb.saul.studySpringbootMybatisRedis.mapper;

import com.unimelb.saul.studySpringbootMybatisRedis.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(email, password, username) values " +
            "(#{email}, #{password} ,#{username})")
    void addUser(User user);

    @Select("select * from user where email = #{email}")
    User findByEmail(@Param("email") String email);


    @Update("update user set password=#{password} where email=#{email}")
    int updateInformation(@Param("email") String email, @Param("password") String password);

}
