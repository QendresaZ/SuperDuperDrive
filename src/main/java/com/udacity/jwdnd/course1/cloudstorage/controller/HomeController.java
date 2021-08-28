package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private FileService fileService;
    private CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(NoteService noteService, FileService fileService, CredentialService credentialService,
                          EncryptionService encryptionService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getUserData(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("notesList", this.noteService.getNotesByUserId(user.getUserId()));
        model.addAttribute("note", new Note());


        model.addAttribute("credentialsList", this.credentialService.getCredentialsbyUserId(user.getUserId()));
        model.addAttribute("credential", new Credential());
        model.addAttribute("encryptionService", encryptionService);

        model.addAttribute("filesList", this.fileService.getFilesByUserId(user.getUserId()));
        model.addAttribute("file", new File());
        return "/home";
    }
}
