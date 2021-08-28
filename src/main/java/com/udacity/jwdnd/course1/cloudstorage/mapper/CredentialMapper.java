package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECt * FROM Credentials WHERE credentialid = #{credentialId}")
    Credential getCredentialById(Integer credentialId);

    @Select("SELECT * FROM Credentials WHERE userid = #{userId}")
    List<Credential> getCredentialByUserId(Integer userId);

    @Delete("DELETE FROM Credentials where credentialid = #{credentialId}")
    int deleteCredential(Integer credentialId);

    @Insert("INSERT INTO Credentials (url, username, key, password, userid) " +
            "VALUES(#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int saveCredential(Credential credential);

    @Select("SELECT * FROM Credentials")
    List<Credential> getAllCredentials();

    @Update("UPDATE Credentials SET url = #{url}, username = #{userName}, key = #{key}, password = #{password}, userid = #{userId}")
    int updateCredential(Credential credential);
}
