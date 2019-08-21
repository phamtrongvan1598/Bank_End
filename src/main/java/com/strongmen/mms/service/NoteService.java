package com.strongmen.mms.service;

import com.strongmen.mms.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> findAll();

    Note findById(Long id);

    void save(Note note);
}
