package com.app.note.dto;

import java.time.LocalDateTime;

public class NoteListDto {
    private String title;
    private LocalDateTime createdDate;

    public NoteListDto(String title, LocalDateTime createdDate) {
        this.title = title;
        this.createdDate = createdDate;
    }

    public String getTitle() { return title; }
    public LocalDateTime getCreatedDate() { return createdDate; }
}
