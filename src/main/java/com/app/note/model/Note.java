package com.app.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "notes")
public class Note {
    @Id
    private String id;
    private String title;
    private String text;
    private List<String> tags;
    private LocalDateTime createdDate = LocalDateTime.now();

    public Note() {}

    public Note(String title, String text, List<String> tags) {
        this.title = title;
        this.text = text;
        this.tags = tags;
        this.createdDate = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
