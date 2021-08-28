package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("notes")
public class NotesController {

    private final NoteService noteService;

    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public RedirectView saveOrEditNote(@ModelAttribute Note note, Authentication authentication, RedirectAttributes redirectAttributes) {
        String saveSuccess = null;
        String editSuccess = null;

        User user = (User) authentication.getPrincipal();
        if (note.getNoteId() == null) {
            note.setUserId(user.getUserId());
            noteService.createNote(note);
            if (note.getNoteId() != null) {
                saveSuccess = "Added note " + note.getNoteTitle() + " successfully";
                redirectAttributes.addFlashAttribute("saveSuccess", saveSuccess);
            } else {
                redirectAttributes.addFlashAttribute("saveError", "An error has occurred and note couldn't be saved.");
            }
        } else if (noteService.checkIfUserIsEligibleForUpdate(user, note)) {
            note.setUserId(user.getUserId());
            int updateNote = noteService.updateNote(note);
            if (updateNote > 0) {
                editSuccess = "Edited note " + note.getNoteTitle() + " successfully";
                redirectAttributes.addFlashAttribute("editSuccess", editSuccess);
            } else {
                redirectAttributes.addFlashAttribute("editError", "An error has occurred and changes couldn't be edited.");
            }
        }
        return new RedirectView("/home");
    }

    @GetMapping("{id}/delete")
    public String deleteNote(Authentication authentication, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        User user = (User) authentication.getPrincipal();
        int deleteNote = noteService.deleteNote(id);
        String deleteNoteSuccess = null;
        if (deleteNote > 0) {
            deleteNoteSuccess = "Deleted note successfully";
            redirectAttributes.addFlashAttribute("deleteNoteSuccess", deleteNoteSuccess);
        } else {
            redirectAttributes.addFlashAttribute("deleteError", "An error has occurred and note couldn't be deleted.");
        }
        return "redirect:/home";
    }
}
