package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("credentials")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    public String saveOrEditCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = (User) authentication.getPrincipal();
        if (credential.getCredentialId() == null) {
            credential.setUserId(user.getUserId());
            int saveCredential = credentialService.saveCredential(credential);
            if (saveCredential > 0) {
                String saveCredentialSuccess = "Added credential successfully";
                redirectAttributes.addFlashAttribute("saveCredentialSuccess", saveCredentialSuccess);
            } else {
                String saveCredentialError = "An error has occurred and note couldn't be saved.";
                redirectAttributes.addFlashAttribute("saveCredentialError", saveCredentialError);
            }
        } else {
            credential.setUserId(user.getUserId());
            int editedCredential = credentialService.updateCredential(credential);
            if (editedCredential > 0) {
                redirectAttributes.addFlashAttribute("editedCredentialSuccess", "Edited credential successfully");
            } else {
                redirectAttributes.addFlashAttribute("editedCredentialError", "An error has occurred and note couldn't be edited.");
            }
        }
        return "redirect:/home";
    }

    @GetMapping("{id}/delete")
    public String deleteCredential(Authentication authentication, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        User user = (User) authentication.getPrincipal();
        int deletedCredential = credentialService.deleteCredential(id);
        if (deletedCredential > 0) {
            redirectAttributes.addFlashAttribute("deletedCredentialSuccess", "Deleted credential successfully");
        } else {
            redirectAttributes.addFlashAttribute("deletedCredentialError", "An error has occurred and credential couldn't be deleted.");
        }

        return "redirect:/home";
    }
}