package com.ramjava.notes.app.service;

import com.ramjava.notes.app.dto.NoteDTO;
import com.ramjava.notes.app.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    List<Note> getAllNotes();
    Optional<Note> getNoteById(Long id);
    Note createNote(NoteDTO noteDTO);
    Optional<Note> updateNote(Long id, NoteDTO noteDTO);
    boolean deleteNoteById(Long id);
}