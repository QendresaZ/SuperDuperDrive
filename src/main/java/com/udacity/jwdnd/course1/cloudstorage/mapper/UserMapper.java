package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS where username = #{username}")
    User getUserByUsername(String username);

    @Insert("INSERT INTO Users (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}," +
                       " #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int saveUser(User user);

    @Delete("DELETE FROM Users WHERE userId = #{userId}")
    User deleteUserById(Integer userId);

    @Update("UPDATE User WHERE SET username = #{username}, salt = #{salt}, password = #{password}, firstname = #{firstName}," +
            "lastname = #{lastName} WHERE userId = #{userId}")
    void updateUser(Integer userId);
}
