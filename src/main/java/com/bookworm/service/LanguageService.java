package com.bookworm.service;


import com.bookworm.entities.Language;
import java.util.List;

/**
 * Service interface for managing Language entities.
 * Defines the business logic operations for product languages.
 */
public interface LanguageService {

    /**
     * Saves a new language or updates an existing one.
     *
     * @param language The language object to be saved.
     * @return The saved language.
     */
    Language saveLanguage(Language language);

    /**
     * Retrieves a language by its unique ID.
     *
     * @param id The ID of the language to retrieve.
     * @return The found language.
     */
    Language getLanguageById(Integer id);

    /**
     * Retrieves all available languages.
     *
     * @return A list of all languages.
     */
    List<Language> getAllLanguages();

    /**
     * Deletes a language by its ID.
     *
     * @param id The ID of the language to delete.
     */
    void deleteLanguage(Integer id);
}

