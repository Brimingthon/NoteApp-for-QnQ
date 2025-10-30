package com.app.note;

import com.app.note.model.Note;
import com.app.note.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void testCreateNote() throws Exception {
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {"title": "Hello", "text": "note is just a note", "tags": ["BUSINESS"]}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Hello"));
    }

    @Test
    void testListNotes() throws Exception {
        repository.saveAll(List.of(
                new Note("First", "Some text", List.of("BUSINESS")),
                new Note("Second", "Another text", List.of("PERSONAL"))
        ));

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notes").isArray());
    }

    @Test
    void testWordStats() throws Exception {
        Note note = repository.save(new Note("Test", "note is just a note", List.of("BUSINESS")));

        mockMvc.perform(get("/api/notes/" + note.getId() + "/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value(2))
                .andExpect(jsonPath("$.is").value(1));
    }
}
