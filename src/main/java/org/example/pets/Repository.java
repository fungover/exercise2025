package org.example.pets;

import java.util.List;

public interface Repository {
  void add(Pets pet);
  List<Pets> getAll();
  Pets getById(String id);
  void remove(String id);
  void feed(String id, String amount);
  void play(String id, String amount);
}
