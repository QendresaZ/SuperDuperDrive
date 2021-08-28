package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import java.io.IOException;

@Controller
@RequestMapping("files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public RedirectView uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile,
                                   Authentication authentication, RedirectAttributes attributes) {
        String fileUploadError = null;

        User user = (User) authentication.getPrincipal();
        try {
            fileService.store(multipartFile, user.getUserId());
            attributes.addFlashAttribute("fileUploadSuccess", "File uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            fileUploadError = "This file name already exists!";
            attributes.addFlashAttribute("fileUploadError", fileUploadError);
        }
        return new RedirectView("home");
    }

    @GetMapping("{id}/delete")
    public String deleteFile(Authentication authentication, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        User user = (User) authentication.getPrincipal();
        int deletedFile = fileService.deleteFile(id);
        if (deletedFile > 0) {
            redirectAttributes.addFlashAttribute("deletedFileSuccess", "Deleted file successfully");
        } else {
            redirectAttributes.addFlashAttribute("deletedFileError", "An error has occurred and file couldn't be deleted.");
        }

        return "redirect:/home";
    }
    
    @GetMapping("download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
       File file = fileService.getFileByFileName(fileName);
       return ResponseEntity.ok().
               header(HttpHeaders.CONTENT_DISPOSITION,
                       "attachment;filename=\"" + file.getFileName() + "\"").
               body(new ByteArrayResource(file.getFileData()));
    }
}
