package org.example;

import org.example.entities.Language;
import org.springframework.data.repository.ListCrudRepository;

public interface LanguageRepository extends ListCrudRepository<Language,Integer> {
}
