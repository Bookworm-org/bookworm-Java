package com.bookworm.service.impl;

import com.bookworm.entities.Genre;
import com.bookworm.repository.GenreRepository;
import com.bookworm.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of the GenreService interface.
 * This class contains the business logic for genre management.
 */
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(Integer id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Genre not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteGenre(Integer id) {
        if (!genreRepository.existsById(id)) {
            throw new NoSuchElementException("Genre not found with id: " + id);
        }
        genreRepository.deleteById(id);
    }
}
