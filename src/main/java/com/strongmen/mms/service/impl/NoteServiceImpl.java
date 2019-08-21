package com.strongmen.mms.service.impl;

import com.strongmen.mms.model.Note;
import com.strongmen.mms.repository.NoteRepository;
import com.strongmen.mms.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();

    }

    @Override
    public Note findById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public void save(Note note) {
        noteRepository.save(note);
    }
}
