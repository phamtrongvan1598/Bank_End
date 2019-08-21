package com.strongmen.mms.repository.impl;

import com.strongmen.mms.model.Note;
import com.strongmen.mms.repository.NoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class NoteRepositoryImpl implements NoteRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Note> findAll() {
        TypedQuery<Note> query = em.createQuery("select n from Note n", Note.class);
        return query.getResultList();
    }

    @Override
    public Note findById(Long id) {
        TypedQuery<Note> query = em.createQuery("select n from Note n where n.id =:id", Note.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Note model) {
        if (model.getId() != null) {
            em.merge(model);

        } else {
            em.persist(model);
        }
    }
}