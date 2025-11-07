package org.example.repository;

import org.example.entities.Language;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends ListCrudRepository<Language,Integer> {
}
