package com.iStore.Produits;

import com.iStore.Database.ConnectionDatabase;
import com.iStore.Users.UserController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.Locale;
import java.util.Vector;
/**
 * Description -> ProduitsController class
 *
 * @Author benjamin, Pierre et Léonard
 */
public class ProduitsController {
    private Connection conn = null;
    private PreparedStatement prepStatement = null;
    private PreparedStatement prepStatement2 = null;
    private Statement statement = null;
    private Statement statement2 = null;
    private ResultSet resultSet = null;

    /**
     * Description -> constructor with database connection
     *
     * @Author benjamin, Pierre et Léonard
     */
    public ProduitsController() {
        try {
            conn = new ConnectionDatabase().getConn();
            statement = conn.createStatement();
            statement2 = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> get all database table produits
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @return
     */
    public ResultSet all() {
        try {
            String query = "SELECT * FROM produits";
            resultSet = statement.executeQuery(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Description -> get a produit in produits
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param produitsModel
     * @param name
     * @return
     */
    public Long getProduitId(ProduitsModel produitsModel, String name) {
        Long id = null;
        try {
            String query = "SELECT * FROM produits WHERE name='" + name + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) id = resultSet.getLong(1);
            produitsModel.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Description -> search in produits
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param name
     * @return
     */
    public boolean isInProduits(String name) {
        try {
            String query = "SELECT name FROM produits WHERE name='"+name+"'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Description -> edit in produits
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param produitsModel
     */
    public void editProduits(ProduitsModel produitsModel) {

        try {
            String query = "UPDATE produits SET name=?, price=?, description=? WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, produitsModel.getName());
            prepStatement.setLong(2, produitsModel.getPrice());
            prepStatement.setString(3, produitsModel.getDescription());
            prepStatement.setLong(4, produitsModel.getId());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Products Updated.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> add in produits
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param produitsModel
     */
    public void addProduits(ProduitsModel produitsModel) {

        try {
            String query = "SELECT * FROM produits WHERE name='"
                    +produitsModel.getName()
                    +"' AND price='"
                    +produitsModel.getPrice()
                    +"' AND description='"
                    +produitsModel.getDescription() +"'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                JOptionPane.showMessageDialog(null, "Products already exists");
            else
                addFunction(produitsModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> add function
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param produitsModel
     */
    public void addFunction(ProduitsModel produitsModel) {
        try {
            String query = "INSERT INTO produits (name,price,description) " +
                    "VALUES(?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, produitsModel.getName());
            prepStatement.setLong(2, produitsModel.getPrice());
            prepStatement.setString(3, produitsModel.getDescription());

            prepStatement.executeUpdate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Description -> delete in produits
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param produitsModel
     */
    public void deleteProduits(ProduitsModel produitsModel) {
        try {
            String query = "DELETE FROM produits WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, produitsModel.getId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Products Deleted.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> load data in JTable
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param table
     */
    public void loadDataSet(JTable table) {
        try {
            ProduitsController produitsController = new ProduitsController();
            table.setModel(produitsController.buildTableModel(produitsController.all()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> build JTable model
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

}
