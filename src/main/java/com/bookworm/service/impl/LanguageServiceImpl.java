package com.bookworm.service.impl;


import com.bookworm.entities.Language;
import com.bookworm.repository.LanguageRepository;
import com.bookworm.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of the LanguageService interface.
 * This class contains the business logic for language management.
 */
@Service
public class LanguageServiceImpl implements LanguageService {

//    private final LanguageRepository languageRepository;
//
//    @Autowired
//    public LanguageServiceImpl(LanguageRepository languageRepository) {
//        this.languageRepository = languageRepository;
//    }
//
//    @Override
//    @Transactional
//    public Language saveLanguage(Language language) {
//        return languageRepository.save(language);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Language getLanguageById(Integer id) {
//        return languageRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Language not found with id: " + id));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<Language> getAllLanguages() {
//        return languageRepository.findAll();
//    }
//
//    @Override
//    @Transactional
//    public void deleteLanguage(Integer id) {
//        if (!languageRepository.existsById(id)) {
//            throw new NoSuchElementException("Language not found with id: " + id);
//        }
//        languageRepository.deleteById(id);
//    }
    
   
}

