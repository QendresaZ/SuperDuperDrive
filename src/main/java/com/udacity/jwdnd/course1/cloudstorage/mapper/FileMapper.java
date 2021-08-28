package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

     @Select("SELECT * FROM FILES")
     List<File> getAllFiles();

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFileByFileName(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES" +
            " (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File file);

    @Delete("Delete FROM FILES WHERE fileid = #{fileId}")
    int deleteFile(Integer fileId);

    @Update("UPDATE FILES SET filename = #(fileName), contenttype = #(contentType), filesize = #(fileSize), " +
            "userid = #(userId), filedata = #(fileData) WHERE fileid = #{fileId}")
    int updateFile(Integer fileId);

}
