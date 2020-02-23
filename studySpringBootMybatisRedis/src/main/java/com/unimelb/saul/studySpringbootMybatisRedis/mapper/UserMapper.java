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


    @Update("update user set username=#{username} where email=#{email}")
    void updateInformation(@Param("email") String email, @Param("username") String username);

    @Delete("delete from user where email = #{email}")
    int deleteUser(String email);

}
