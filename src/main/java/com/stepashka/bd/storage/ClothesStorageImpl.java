package com.stepashka.bd.storage;

import com.stepashka.bd.error.StorageException;
import com.stepashka.bd.model.Clients;

import java.util.List;

public class ClothesStorageImpl  extends DatabaseConnector implements ObjectStorage<Clients> {
    private String SAVE_SQL = "INSERT INTO clients (client_name, client_surname, client_email) VALUES(?, ?, ?)";
    private String UPDATE_SQL = "UPDATE clients SET client_name=?, client_surname=?, client_email=? WHERE client_id=?";
    private String DELETE_SQL = "DELETE FROM clients WHERE client_id=?";
    private String SELECT_ALL_SQL = "SELECT * FROM clients";

    @Override
    public List<Clients> getAll() throws StorageException {
        return List.of();
    }

    @Override
    public void save(Clients entity) throws StorageException {

    }

    @Override
    public void update(Clients entity) throws StorageException {

    }

    @Override
    public void delete(Long id) throws StorageException {

    }
}
