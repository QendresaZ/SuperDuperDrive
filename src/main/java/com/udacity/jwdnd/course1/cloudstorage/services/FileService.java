package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(File file) {
        return fileMapper.saveFile(file);
    }

    public void store(MultipartFile multipartFile, Integer userId) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!isFileNameAvailable(fileName)) {
            throw new IOException("File with this name already exists");
        }
        String fileSizeInKilobyes = (multipartFile.getSize() / 1024) + " KB";
        File file = new File(null, fileName, multipartFile.getContentType(), fileSizeInKilobyes, userId, multipartFile.getBytes());
        saveFile(file);
    }

    private boolean isFileNameAvailable(String fileName) {
        return fileMapper.getFileByFileName(fileName) == null;
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public List<File> getFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public int deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

    public File getFileByFileName(String fileName) {
        return fileMapper.getFileByFileName(fileName);
    }
}
