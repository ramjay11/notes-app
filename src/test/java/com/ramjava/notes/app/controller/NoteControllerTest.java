package com.ramjava.notes.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramjava.notes.app.dto.NoteDTO;
import com.ramjava.notes.app.model.Note;
import com.ramjava.notes.app.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    private final AtomicLong idGenerator = new AtomicLong();

    @Test
    void shouldCreateNote() throws Exception {
        NoteDTO noteDTO = new NoteDTO("Test Title", "Test Body");
        Note note = new Note(idGenerator.incrementAndGet(), noteDTO.getTitle(), noteDTO.getBody());

        when(noteService.createNote(any(NoteDTO.class))).thenReturn(note);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.body").value("Test Body"));
    }

    @Test
    void shouldGetAllNotes() throws Exception {
        when(noteService.getAllNotes()).thenReturn(List.of(
                new Note(1L, "Note 1", "Body 1"),
                new Note(2L, "Note 2", "Body 2")
        ));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Note 1"))
                .andExpect(jsonPath("$[1].title").value("Note 2"));
    }

    @Test
    void shouldGetNoteById() throws Exception {
        Note note = new Note(1L, "Sample Note", "Sample Body");
        when(noteService.getNoteById(1L)).thenReturn(Optional.of(note));

        mockMvc.perform(get("/notes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Note"))
                .andExpect(jsonPath("$.body").value("Sample Body"));
    }

    @Test
    void shouldReturn404ForNoteNotFound() throws Exception {
        when(noteService.getNoteById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/notes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateNote() throws Exception {
        NoteDTO noteDTO = new NoteDTO("Updated Title", "Updated Body");
        Note updatedNote = new Note(1L, "Updated Title", "Updated Body");

        when(noteService.updateNote(1L, noteDTO)).thenReturn(Optional.of(updatedNote));

        mockMvc.perform(put("/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.body").value("Updated Body"));
    }

    @Test
    void shouldDeleteNote() throws Exception {
        when(noteService.deleteNoteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/notes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentNote() throws Exception {
        when(noteService.deleteNoteById(999L)).thenReturn(false);

        mockMvc.perform(delete("/notes/999"))
                .andExpect(status().isNotFound());
    }
}