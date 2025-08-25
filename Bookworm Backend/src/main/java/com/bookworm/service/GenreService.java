package com.bookworm.service;

import com.bookworm.entities.Genre;
import java.util.List;

/**
 * Service interface for managing Genre entities.
 * Defines the business logic operations for product genres.
 */
public interface GenreService {

    /**
     * Saves a new genre or updates an existing one.
     *
     * @param genre The genre object to be saved.
     * @return The saved genre.
     */
    Genre saveGenre(Genre genre);

    /**
     * Retrieves a genre by its unique ID.
     *
     * @param id The ID of the genre to retrieve.
     * @return The found genre.
     */
    Genre getGenreById(Integer id);

    /**
     * Retrieves all genres.
     *
     * @return A list of all genres.
     */
    List<Genre> getAllGenres();

    /**
     * Deletes a genre by its ID.
     *
     * @param id The ID of the genre to delete.
     */
    void deleteGenre(Integer id);
}
