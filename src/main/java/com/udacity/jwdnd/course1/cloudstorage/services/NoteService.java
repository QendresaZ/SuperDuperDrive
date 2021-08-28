package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public int createNote(Note note) {
        return noteMapper.saveNoteMapper(note);
    }

    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public Note getNoteById(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }

    public List<Note> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }

    public boolean checkIfUserIsEligibleForUpdate(User user, Note note) {
        Note result = getNoteById(note.getNoteId());
        if (result == null) return false;
        return result.getUserId().equals(user.getUserId());
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }
}
