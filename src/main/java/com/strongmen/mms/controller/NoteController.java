package com.strongmen.mms.controller;

import com.strongmen.mms.model.Note;
import com.strongmen.mms.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = {"*"})
@RestController
public class NoteController {
    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Note>> listAllNotes() {
        List<Note> notes = noteService.findAll();
        if (notes.isEmpty()) {
            return new ResponseEntity<List<Note>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Note> getNote(@PathVariable("id") long id) {
        System.out.println("Fetching Note with id " + id);
        Note note = noteService.findById(id);
        if (note == null) {
            System.out.println("Note with id " + id + " not found");
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Note>(note, HttpStatus.OK);
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public ResponseEntity<Void> createNote(@RequestBody Note note, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Note " + note.getTitle());
        noteService.save(note);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/note/{id}").buildAndExpand(note.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Note> updateCustomer(@PathVariable("id") long id, @RequestBody Note note) {
        System.out.println("Updating Note " + id);

        Note currentNote = noteService.findById(id);

        if (currentNote == null) {
            System.out.println("Note with id " + id + " not found");
            return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
        }

        currentNote.setTitle(note.getTitle());
        currentNote.setContent(note.getContent());
        currentNote.setId(note.getId());

        noteService.save(currentNote);
        return new ResponseEntity<Note>(currentNote, HttpStatus.OK);
    }
}
