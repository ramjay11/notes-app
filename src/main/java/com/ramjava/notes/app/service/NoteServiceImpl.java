package com.ramjava.notes.app.service;

import com.ramjava.notes.app.dto.NoteDTO;
import com.ramjava.notes.app.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NoteServiceImpl implements NoteService {

    private final List<Note> notes = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public List<Note> getAllNotes() {
        return notes;
    }

    @Override
    public Optional<Note> getNoteById(Long id) {
        return notes.stream().filter(note -> note.getId().equals(id)).findFirst();
    }

    @Override
    public Note createNote(NoteDTO noteDTO) {
        Note note = new Note(idGenerator.incrementAndGet(), noteDTO.getTitle(), noteDTO.getBody());
        notes.add(note);
        return note;
    }

    @Override
    public Optional<Note> updateNote(Long id, NoteDTO noteDTO) {
        return getNoteById(id).map(note -> {
            note.setTitle(noteDTO.getTitle());
            note.setBody(noteDTO.getBody());
            return note;
        });
    }

    @Override
    public boolean deleteNoteById(Long id) {
        return notes.removeIf(note -> note.getId().equals(id));
    }
}