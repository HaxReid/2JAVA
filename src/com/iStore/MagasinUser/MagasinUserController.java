package com.iStore.MagasinUser;
import com.iStore.Database.ConnectionDatabase;
import com.iStore.Magasin.MagasinModel;
import com.iStore.Produits.ProduitsModel;
import com.iStore.Stocks.StockModel;
import com.iStore.Users.UserController;
import com.iStore.Users.UserModel;
import com.iStore.Whitelist.WhitelistModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 * Description -> MagasinUserController class
 *
 * @Author benjamin, Pierre et Léonard
 */
public class MagasinUserController {
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
    public MagasinUserController() {
        try {
            conn = new ConnectionDatabase().getConn();
            statement = conn.createStatement();
            statement2 = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> get all database table magasin_user
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @return
     */
    public ResultSet all() {
        try {
            String query = "SELECT users.name, users.email, quantite, magasin.name FROM magasin_user " +
                    "LEFT JOIN magasin ON magasin_user.magasin_id = magasin.id " +
                    "LEFT JOIN users ON magasin_user.user_id = users.id";
            resultSet = statement.executeQuery(query);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;

    }

    /**
     * Description -> search in magasin_user
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param name
     * @return
     */
    public boolean isInMagasin(String name) {
        try {
            String query = "SELECT users.name FROM magasin_user " +
                    "LEFT JOIN users ON magasin_user.user_id = users.id " +
                    "WHERE users.name=?";
            prepStatement.setString(1, name);
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Description -> add in magasin_user
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param userModel
     * @param magasinModel
     * @param magasinUserModel
     */
    public void addMagasinUser(UserModel userModel, MagasinModel magasinModel, MagasinUserModel magasinUserModel) {

        try {
            String query = "SELECT users.name, users.email, magasin.name, role FROM magasin_user " +
                    "LEFT JOIN users ON magasin_user.user_id = users.id " +
                    "LEFT JOIN magasin ON magasin_user.magasin_id = magasin.id " +
                    "WHERE users.name='"
                    +userModel.getName()
                    +"' AND users.email='"
                    +userModel.getEmail()
                    +"' AND magasin.name='"
                    +magasinModel.getName()
                    +"' AND magasin.name='"
                    +magasinUserModel.getRole() +"'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                JOptionPane.showMessageDialog(null, "User already exists in magasin");
            else
                addFunction(userModel, magasinModel, magasinUserModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> add function
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param userModel
     * @param magasinModel
     * @param magasinUserModel
     */
    public void addFunction(UserModel userModel, MagasinModel magasinModel, MagasinUserModel magasinUserModel) {
        try {
            String query = "INSERT INTO stocks (produit_id,magasin_id,role) " +
                    "VALUES(?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, userModel.getId());
            prepStatement.setLong(2, magasinModel.getId());
            prepStatement.setString(3, magasinUserModel.getRole());

            prepStatement.executeUpdate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Description -> delete in magasin_user by user
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param userModel
     */
    public void deleteMagasinUserByUser(UserModel userModel) {
        try {
            String query = "DELETE FROM magasin_user WHERE user_id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, userModel.getId());
            prepStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> delete in magasin_user by magasin
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param magasinUserModel
     */
    public void deleteMagasinUserByUser(MagasinUserModel magasinUserModel) {
        try {
            String query = "DELETE FROM magasin_user WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, magasinUserModel.getId());
            prepStatement.executeUpdate();
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
            MagasinUserController magasinUserController = new MagasinUserController();
            table.setModel(magasinUserController.buildTableModel(magasinUserController.all()));
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
