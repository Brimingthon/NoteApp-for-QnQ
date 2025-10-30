package com.app.note.controller;

import com.app.note.dto.NoteListDto;
import com.app.note.model.Note;
import com.app.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository repository;

    // Create
    @PostMapping
    public Note create(@RequestBody Note note) {
        if (note.getTitle() == null || note.getTitle().isBlank() ||
                note.getText() == null || note.getText().isBlank()) {
            throw new IllegalArgumentException("Title and text cannot be empty");
        }
        return repository.save(note);
    }

    // List (with filter + pagination)
    @GetMapping
    public Map<String, Object> getAll(
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        List<Note> notes = repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Note::getCreatedDate).reversed())
                .collect(Collectors.toList());

        if (tag != null && !tag.isBlank()) {
            notes = notes.stream()
                    .filter(n -> n.getTags() != null && n.getTags().contains(tag))
                    .toList();
        }

        int start = Math.min(page * size, notes.size());
        int end = Math.min(start + size, notes.size());

        List<NoteListDto> content = notes.subList(start, end)
                .stream()
                .map(n -> new NoteListDto(n.getTitle(), n.getCreatedDate()))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("notes", content);
        response.put("page", page);
        response.put("totalPages", (int) Math.ceil((double) notes.size() / size));
        response.put("totalElements", notes.size());
        return response;
    }

    // Get one
    @GetMapping("/{id}")
    public Note getOne(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    // Update
    @PutMapping("/{id}")
    public Note update(@PathVariable String id, @RequestBody Note updated) {
        Note existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (updated.getTitle() != null && !updated.getTitle().isBlank())
            existing.setTitle(updated.getTitle());
        if (updated.getText() != null && !updated.getText().isBlank())
            existing.setText(updated.getText());
        if (updated.getTags() != null)
            existing.setTags(updated.getTags());

        return repository.save(existing);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repository.deleteById(id);
    }

    // Stats
    @GetMapping("/{id}/stats")
    public Map<String, Long> getWordStats(@PathVariable String id) {
        Note note = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        return Arrays.stream(note.getText().toLowerCase().split("\\W+"))
                .filter(w -> !w.isBlank())
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}
