package com.stepashka.bd.storage;

import com.stepashka.bd.entity.Stock;
import com.stepashka.bd.error.StorageException;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class StockStorageImpl extends DatabaseConnector implements ObjectStorage<Stock>{
    private String SAVE_SQL = "INSERT INTO stocks (stock_town, stock_address) VALUES(?, ?)";
    private String UPDATE_SQL = "UPDATE stocks SET stock_town=?, stock_address=? WHERE stock_id=?";
    private String DELETE_SQL = "DELETE FROM stocks WHERE stock_id=?";
    private String SELECT_ALL_SQL = "SELECT * FROM stocks";

    @Override
    public List<Stock> getAll() throws StorageException {
        List<Stock> list = new LinkedList<>();

        try(Connection connection = setConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_SQL);
            while(result.next()) {
                var item = Stock.builder()
                        .id(result.getLong("stock_id"))
                        .town(result.getString("stock_town"))
                        .address(result.getString("stock_address"))
                        .build();
                list.add(item);
            }
        } catch (SQLException ex){
            var message = "Ошибка извлечения всех полей таблицы stocks.";
            handleException(ex.getMessage(), message);
        }
        return list;
    }

    @Override
    public Long save(Stock entity) throws StorageException {
        Long id = 0L;

        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
            statement.setString(1, entity.getTown());
            statement.setString(2, entity.getAddress());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()){
                    id = resultSet.getLong(1);
                }
            }
        } catch (SQLException ex){
            var message = "Ошибка добавления объекта stocks (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }

        return id;
    }

    @Override
    public void update(Stock entity) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, entity.getTown());
            statement.setString(2, entity.getAddress());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка обновления объекта stocks (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }
    }

    @Override
    public void delete(Long id) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка удаления объекта stocks (id=%s).".formatted(id);
            handleException(ex.getMessage(), message);
        }
    }

    private void handleException(String errorMessage, String message) throws StorageException {
        log.error(errorMessage);
        throw new StorageException(message);
    }
}
