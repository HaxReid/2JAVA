package com.iStore.Stocks;

import com.iStore.Database.ConnectionDatabase;
import com.iStore.Magasin.MagasinModel;
import com.iStore.Produits.ProduitsModel;
import com.iStore.Users.UserController;
import com.iStore.Users.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 * Description -> StockController class
 *
 * @Author benjamin, Pierre et Léonard
 */
public class StockController {
    private Connection conn = null;
    private PreparedStatement prepStatement = null;
    private PreparedStatement prepStatement2 = null;
    private Statement statement = null;
    private Statement statement2 = null;
    private ResultSet resultSet = null;

    /**
     * Description -> construcotr with databse connection
     *
     * @Author benjamin, Pierre et Léonard
     */
    public StockController() {
        try {
            conn = new ConnectionDatabase().getConn();
            statement = conn.createStatement();
            statement2 = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> get all database table stocks
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @return
     */
    public ResultSet all() {
        try {
            String query = "SELECT produits.name, quantite, magasin.name FROM stocks " +
                    "LEFT JOIN magasin ON stocks.magasin_id = magasin.id " +
                    "LEFT JOIN produits ON stocks.produits_id = produits.id";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Description -> edit quantite in stocks
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param stockModel
     */
    public void editStockQuantite(StockModel stockModel) {

        try {
            String query = "UPDATE stocks SET quantite=? WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, stockModel.getQuantite());
            prepStatement.setLong(2, stockModel.getId());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Stock Quantite Updated.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> add in stocks
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param produitsModel
     * @param magasinModel
     * @param stockModel
     */
    public void addStock(ProduitsModel produitsModel, MagasinModel magasinModel, StockModel stockModel) {

        try {
            String query = "SELECT produits.name, quantite, magasin.name FROM stocks " +
                    "LEFT JOIN produits ON stocks.produit_id = produits.id " +
                    "LEFT JOIN magasin ON stocks.magasin_id = magasin.id " +
                    "WHERE produits.name='"
                    +produitsModel.getName()
                    +"' AND quantite='"
                    +stockModel.getQuantite()
                    +"' AND magasin.name='"
                    +magasinModel.getName() +"'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                JOptionPane.showMessageDialog(null, "Stock already exists");
            else
                addFunction(produitsModel, magasinModel, stockModel);
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
     * @param magasinModel
     * @param stockModel
     */
    public void addFunction(ProduitsModel produitsModel, MagasinModel magasinModel, StockModel stockModel) {
        try {
            String query = "INSERT INTO stocks (produit_id,quantite,magasin_id) " +
                    "VALUES(?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, produitsModel.getId());
            prepStatement.setLong(2, stockModel.getQuantite());
            prepStatement.setLong(3, magasinModel.getId());

            prepStatement.executeUpdate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Description -> delete in stocks
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param stockModel
     */
    public void deleteStock(StockModel stockModel) {
        try {
            String query = "DELETE FROM stocks WHERE id=?";
            prepStatement = (PreparedStatement) conn.prepareStatement(query);
            prepStatement.setLong(1, stockModel.getId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Stock Deleted.");
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
            StockController stock = new StockController();
            table.setModel(stock.buildTableModel(stock.all()));
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
    // Method to display product-related data set in tabular form
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
