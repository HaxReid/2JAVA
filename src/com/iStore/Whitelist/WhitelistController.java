package com.iStore.Whitelist;

import com.iStore.Database.ConnectionDatabase;
import com.iStore.Users.UserController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 * Description -> WhitelisteController class
 *
 * @Author benjamin, Pierre et Léonard
 */
public class WhitelistController {
    private Connection conn = null;
    private PreparedStatement prepStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    /**
     * Description -> constructor with database connection
     *
     * @Author benjamin, Pierre et Léonard
     */
    public WhitelistController() {
        try {
            conn = new ConnectionDatabase().getConn();
            statement = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> get all database table whitelist
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @return
     */
    public ResultSet all() {
        try {
            String query = "SELECT * FROM whitelist";
            resultSet = statement.executeQuery(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Description -> Search email in whitelist
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param email
     * @return
     */
    public boolean isInWhitelist(String email) {
        try {
            String query = "SELECT email FROM whitelist WHERE email='"+email+"'";
            //prepStatement.setString(1, ());
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Description -> edit in whitelist
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param whitelistModel
     */
    public void editUserInWhitelist(WhitelistModel whitelistModel) {

        try {
            String query = "UPDATE whitelist SET email=? WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, whitelistModel.getEmail());
            prepStatement.setLong(2, whitelistModel.getId());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Updated.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> add in whitelist
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param whitelistModel
     */
    public void addUserInWhitelist(WhitelistModel whitelistModel) {

        try {
            String query = "SELECT * FROM whitelist WHERE email='"+whitelistModel.getEmail()+"'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                JOptionPane.showMessageDialog(null, "User already exists in whitelist");
            else
                addFunction(whitelistModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> add function
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param whitelistModel
     */
    public void addFunction(WhitelistModel whitelistModel) {
        try {
            String query = "INSERT INTO whitelist (email) " +
                    "VALUES(?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, whitelistModel.getEmail());
            prepStatement.executeUpdate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Description -> delete in whitelist
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param whitelistModel
     */
    public void deleteUserInWhitelist(WhitelistModel whitelistModel) {
        try {
            String query = "DELETE FROM whitelist WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, whitelistModel.getId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User in Whitelist Deleted.");
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
            WhitelistController whitelistController = new WhitelistController();
            table.setModel(whitelistController.buildTableModel(whitelistController.all()));
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
