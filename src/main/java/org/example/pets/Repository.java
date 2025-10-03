package org.example.pets;

import java.util.List;

public interface Repository {
  void add(Pets pet);
  List<Pets> getAll();
  Pets getById(Long id);
  Pets remove(Long id);
  Pets update(Long id);
}
