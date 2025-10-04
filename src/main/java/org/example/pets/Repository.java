package org.example.pets;

import java.util.List;

public interface Repository {
  Pets add(Pets pet);
  List<Pets> getAll();
  Pets getById(String id);
  Pets remove(String id);
  Pets feed(String id, String amount);
  Pets play(String id, String amount);
  List<Pets> findByName(String name);
}
