package com.iStore.Magasin;

import com.iStore.Database.ConnectionDatabase;
import com.iStore.Produits.ProduitsModel;
import com.iStore.Users.UserController;
import com.iStore.Users.UserModel;
import com.iStore.Whitelist.WhitelistModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 * Description -> MagasinController class
 *
 * @Author benjamin, Pierre et Léonard
 */
public class MagasinController {
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
    public MagasinController() {
        try {
            conn = new ConnectionDatabase().getConn();
            statement = conn.createStatement();
            statement2 = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> get all database table magasin
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @return
     */
    public ResultSet all() {
        try {
            String query = "SELECT * FROM magasin";
            resultSet = statement.executeQuery(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Description -> add in magasin
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param magasinModel
     */
    public void addMagasin(MagasinModel magasinModel) {

        try {
            String query = "SELECT * FROM magasin WHERE name='"+ magasinModel.getName()+"'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                JOptionPane.showMessageDialog(null, "Products already exists");
            else
                addFunction(magasinModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> add function
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param magasinModel
     */
    public void addFunction(MagasinModel magasinModel) {
        try {
            String query = "INSERT INTO magasin (name) " +
                    "VALUES (?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, magasinModel.getName());

            prepStatement.executeUpdate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Description -> delete in magasin
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param magasinModel
     */
    public void deleteMagasin(MagasinModel magasinModel) {
        try {
            String query = "DELETE FROM magasin WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, magasinModel.getId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Magasin Deleted.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> edit in magasin
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param magasinModel
     */
    public void editMagasin(MagasinModel magasinModel) {

        try {
            String query = "UPDATE magasin SET name=? WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, magasinModel.getName());
            prepStatement.setLong(2, magasinModel.getId());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Magasin Updated.");

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
            MagasinController magasinController = new MagasinController();
            table.setModel(magasinController.buildTableModel(magasinController.all()));
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