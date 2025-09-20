package org.example.repository;

public class FileRepository implements DataRepository {
    @Override
    public String getData() {
        return "Data from File";
    }
}
