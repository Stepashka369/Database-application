package com.stepashka.bd.storage;

import com.stepashka.bd.error.StorageException;


import java.util.List;

public interface ObjectStorage<T> {
    List<T> getAll() throws StorageException;
    Long save(T entity) throws StorageException;
    void update(T entity) throws StorageException;
    void delete(Long id) throws StorageException;
}
