package com.stepashka.bd.storage;

import com.stepashka.bd.entity.Clothes;
import com.stepashka.bd.entity.ClothingBrand;
import com.stepashka.bd.entity.ClothingSize;
import com.stepashka.bd.entity.ClothingType;
import com.stepashka.bd.error.StorageException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ClothesStorageImpl  extends DatabaseConnector implements ObjectStorage<Clothes> {
    private String SAVE_SQL = "INSERT INTO clothes(clothe_item_cost, clothe_item_note, clothe_item_size_id, clothe_item_type_id, clothe_item_brand_id) values(?, ?, ?, ?, ?)";
    private String UPDATE_SQL = "UPDATE clothes SET clothe_item_cost=?, clothe_item_note=?, clothe_item_size_id=?, clothe_item_type_id=?, clothe_item_brand_id=? WHERE clothe_item_id=?";
    private String DELETE_SQL = "DELETE FROM clothes WHERE clothe_item_id=?";
    private String SELECT_ALL_SQL = "SELECT " +
        "cl.clothe_item_id, cl.clothe_item_cost, cl.clothe_item_note, " +
        "cls.size_id, cls.size_code, cls.size_note, " +
        "clt.type_id, clt.type_code, clt.type_note, " +
        "clb.brand_id, clb.brand_code, clb.brand_note " +
        "FROM clothes cl " +
        "LEFT JOIN clothing_sizes cls ON cl.clothe_item_size_id = cls.size_id " +
        "LEFT JOIN clothing_types clt ON cl.clothe_item_type_id = clt.type_id " +
        "LEFT JOIN clothing_brands clb ON cl.clothe_item_brand_id = clb.brand_id";

    @Override
    public List<Clothes> getAll() throws StorageException {
        List<Clothes> list = new LinkedList<>();

        try(Connection connection = setConnection()){
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(SELECT_ALL_SQL);
            while(result.next()) {
                var type = ClothingType.builder()
                        .id(result.getLong("type_id"))
                        .code(result.getString("type_code"))
                        .note(result.getString("type_note"))
                        .build();
                var size = ClothingSize.builder()
                        .id(result.getLong("size_id"))
                        .code(result.getString("size_code"))
                        .note(result.getString("size_note"))
                        .build();
                var brand = ClothingBrand.builder()
                        .id(result.getLong("brand_id"))
                        .code(result.getString("brand_code"))
                        .note(result.getString("brand_note"))
                        .build();
                var clothe = Clothes.builder()
                        .id(result.getLong("clothe_item_id"))
                        .cost(result.getFloat("clothe_item_cost"))
                        .note(result.getString("clothe_item_note"))
                        .brand(brand)
                        .type(type)
                        .size(size)
                        .build();
                list.add(clothe);
            }
        } catch (SQLException ex){
            var message = "Ошибка извлечения всех полей таблицы clothes.";
            handleException(ex.getMessage(), message);
        }
        return list;
    }

    @Override
    public Long save(Clothes entity) throws StorageException {
        Long id = 0L;

        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
            statement.setFloat(1, entity.getCost());
            statement.setString(2, entity.getNote());
            statement.setLong(3, entity.getSize().getId());
            statement.setLong(4, entity.getType().getId());
            statement.setLong(5, entity.getBrand().getId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()){
                    id = resultSet.getLong(1);
                }
            }
        } catch (SQLException ex){
            var message = "Ошибка добавления объекта clothes (id=%s).".formatted(entity.getId());
            handleException(ex.getMessage(), message);
        }

        return id;
    }

    @Override
    public void update(Clothes entity) throws StorageException {
        try(Connection connection = setConnection()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setFloat(1, entity.getCost());
            statement.setString(2, entity.getNote());
            statement.setLong(3, entity.getSize().getId());
            statement.setLong(4, entity.getType().getId());
            statement.setLong(5, entity.getBrand().getId());
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException ex){
            var message = "Ошибка обновления объекта clothes (id=%s).".formatted(entity.getId());
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
            var message = "Ошибка удаления объекта clothe (id=%s).".formatted(id);
            handleException(ex.getMessage(), message);
        }
    }

    private void handleException(String errorMessage, String message) throws StorageException {
        log.error(errorMessage);
        throw new StorageException(message);
    }
}
