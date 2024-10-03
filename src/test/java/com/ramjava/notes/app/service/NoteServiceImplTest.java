package com.ramjava.notes.app.service;

import com.ramjava.notes.app.dto.NoteDTO;
import com.ramjava.notes.app.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NoteServiceImplTest {

    private NoteServiceImpl noteService;

    @BeforeEach
    void setUp() {
        noteService = new NoteServiceImpl();
    }

    @Test
    void shouldCreateNote() {
        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setTitle("Sample Note");
        noteDTO.setBody("This is a sample note");

        Note note = noteService.createNote(noteDTO);

        assertNotNull(note.getId(), "Note ID should not be null");
        assertEquals("Sample Note", note.getTitle());
        assertEquals("This is a sample note", note.getBody());
    }

    @Test
    void shouldReturnAllNotes() {
        noteService.createNote(new NoteDTO("Title 1", "Body 1"));
        noteService.createNote(new NoteDTO("Title 2", "Body 2"));

        List<Note> notes = noteService.getAllNotes();

        assertEquals(2, notes.size());
    }

    @Test
    void shouldReturnNoteById() {
        NoteDTO noteDTO = new NoteDTO("Test Note", "Test Body");
        Note note = noteService.createNote(noteDTO);

        Optional<Note> retrievedNote = noteService.getNoteById(note.getId());

        assertTrue(retrievedNote.isPresent());
        assertEquals("Test Note", retrievedNote.get().getTitle());
    }

    @Test
    void shouldUpdateNote() {
        NoteDTO noteDTO = new NoteDTO("Initial Title", "Initial Body");
        Note createdNote = noteService.createNote(noteDTO);

        NoteDTO updateDTO = new NoteDTO("Updated Title", "Updated Body");
        Optional<Note> updatedNote = noteService.updateNote(createdNote.getId(), updateDTO);

        assertTrue(updatedNote.isPresent());
        assertEquals("Updated Title", updatedNote.get().getTitle());
    }

    @Test
    void shouldDeleteNoteById() {
        NoteDTO noteDTO = new NoteDTO("To be deleted", "Delete this note");
        Note note = noteService.createNote(noteDTO);

        boolean isDeleted = noteService.deleteNoteById(note.getId());

        assertTrue(isDeleted);
        assertTrue(noteService.getNoteById(note.getId()).isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenNoteNotFound() {
        Optional<Note> note = noteService.getNoteById(999L);
        assertTrue(note.isEmpty());
    }
}