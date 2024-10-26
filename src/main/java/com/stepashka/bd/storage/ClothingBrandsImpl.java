//package com.stepashka.bd.storage;
//
//import com.stepashka.bd.error.StorageException;
//import com.stepashka.bd.model.Clients;
//import com.stepashka.bd.model.ClothingBrands;
//import com.stepashka.bd.model.ClothingSize;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.LinkedList;
//import java.util.List;
//
//public class ClothingBrandsImpl extends DatabaseConnector implements ObjectStorage<Clients> {
//    private String SAVE_SQL = "INSERT INTO clothing_brands (brand_code, brand_note) VALUES(?, ?)";
//    private String UPDATE_SQL = "UPDATE clothing_brands SET brand_code=?, brand_note=? WHERE brand_id=?";
//    private String DELETE_SQL = "DELETE FROM clothing_brands WHERE brand_id=?";
//    private String SELECT_ALL_SQL = "SELECT * FROM clothing_brands";
//
//    @Override
//    public List<Clients> getAll() throws StorageException {
////        List<ClothingBrands> list = new LinkedList<>();
////
////        try(Connection connection = setConnection()){
////            Statement statement = connection.createStatement();
////            ResultSet result = statement.executeQuery(SELECT_ALL_SQL);
////            while(result.next()) {
////                var item = ClothingBrands.builder()
////                        .id(result.getLong("size_id"))
////                        .code(result.getString("size_code"))
////                        .note(result.getString("size_note"))
////                        .build();
////                list.add(item);
////            }
////        } catch (SQLException ex){
////            var message = "Ошибка извлечения всех полей таблицы clothing_sizes";
////            handleException(ex.getMessage(), message);
////        }
////        return list;
//    }
//
//    @Override
//    public void save(Clients entity) throws StorageException {
//
//    }
//
//    @Override
//    public void update(Clients entity) throws StorageException {
//
//    }
//
//    @Override
//    public void delete(Long id) throws StorageException {
//
//    }
//}
