package org.example.repository;

import org.example.domain.DataRepository;

public class FileRepository implements DataRepository {
    @Override
    public String getData() {
        return "Data from File";
    }
}
