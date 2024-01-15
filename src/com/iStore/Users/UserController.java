package com.iStore.Users;

import com.iStore.Database.ConnectionDatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.iStore.Whitelist.WhitelistController;


/**
 * Description -> UserController class
 *
 * @Author benjamin, Pierre et Léonard
 */
public class UserController {
    private Connection conn = null;
    private PreparedStatement prepStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private WhitelistController whitelist = new WhitelistController();


    /**
     * Description -> constructor with database connection
     * 
     * @Author benjamin, Pierre et Léonard
     */
    public UserController() {
        try {
            conn = new ConnectionDatabase().getConn();
            statement = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> get all user in users
     * 
     * @Author benjamin, Pierre et Léonard
     * 
     * @return 
     */
    public ResultSet allUser() {
        try {
            String query = "SELECT * FROM users";
            resultSet = statement.executeQuery(query);

       } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Description -> get one user in users
     * 
     * @Author benjamin, Pierre et Léonard
     * 
     * @param email 
     * @return
     */
    public ResultSet getUser(String email) {
        try {
            String query = "SELECT * FROM users WHERE email='"+email+"'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Description -> Verify if an email is an email
     * 
     * @Author benjamin, Pierre et Léonard
     * 
     * @param email 
     * @return
     */
    public boolean verifyEmail(String email) {
        Pattern p = Pattern
                .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = p.matcher(email.toUpperCase());
        return m.matches();
    }


    /**
     * Description -> edit in users
     * 
     * @Author benjamin, Pierre et Léonard
     * 
     * @param userModel 
     */
    public void editUser(UserModel userModel) {

        try {
            String query = "UPDATE users SET name=?,email=?, password=? WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userModel.getName());
            prepStatement.setString(2, userModel.getEmail());
            prepStatement.setString(3, userModel.getPassword());
            prepStatement.setLong(4, userModel.getId());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Updated.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> edit into an admin in users
     *
     * @Author benjamin, Pierre et Léonard
     * 
     * @param userModel 
     * @param is_admin
     */
    public void editUserToAdmin(UserModel userModel, Boolean is_admin) {

        try {
            String query = "UPDATE users SET is_admin=? WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setBoolean(1, is_admin);
            prepStatement.setLong(2, userModel.getId());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Updated.");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> add in users
     *
     * @Author benjamin, Pierre et Léonard
     * 
     * @param userModel 
     * @param is_admin
     */
    public void addUser(UserModel userModel, Boolean is_admin) {

        try {
            String query = "SELECT * FROM users WHERE name='"
                    +userModel.getName()
                    +"' AND email='"
                    +userModel.getEmail()
                    +"' AND is_admin='"
                    +is_admin
                    +"' AND password='"
                    +HashUserPassword(userModel.getPassword())
                    +"'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                JOptionPane.showMessageDialog(null, "User already exists");
            else
                addFunction(userModel, is_admin);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> Add function
     *
     * @Author benjamin, Pierre et Léonard
     * 
     * @param userModel 
     * @param is_admin
     */
    public void addFunction(UserModel userModel, Boolean is_admin) {
        try {
            String query = "INSERT INTO users (name,email,is_admin,password) " +
                    "VALUES(?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userModel.getName());
            prepStatement.setString(2, userModel.getEmail());
            prepStatement.setBoolean(3, is_admin);
            prepStatement.setString(4, HashUserPassword(userModel.getPassword()));
            prepStatement.executeUpdate();

            if(is_admin)
                JOptionPane.showMessageDialog(null, "New administrator added.");
            else JOptionPane.showMessageDialog(null, "New employee added.");

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Description -> delete in users
     *
     * @Author benjamin, Pierre et Léonard
     * 
     * @param userModel 
     */
    public void deleteUser(UserModel userModel) {
        try {
            String query = "DELETE FROM users WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setLong(1, userModel.getId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "User Deleted.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Description -> hash password
     *
     * @Author benjamin, Pierre et Léonard
     * 
     * @param base 
     * @return
     */
    public static String HashUserPassword(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * Description -> checkpassword in users
     *
     * @author benjamin, leonard, pierre
     *
     * @param password 
     * @param email
     * @return
     */
    public boolean CheckPassword(String password, String email){
        String query = "SELECT password, email FROM users WHERE password='"+password+"' AND  email = '"+email+"'";
        try {
        resultSet = statement.executeQuery(query);
        if(resultSet.next()) return true;
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        return false;

    }

    /**
     * Description -> register in users
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param name
     * @param email
     * @param password
     * @param passwordConf
     * @return
     */
    public boolean register (String name, String email, String password, String passwordConf){
        UserModel user = new UserModel();
        if (verifyEmail(email)){
            if (this.whitelist.isInWhitelist(email)){
                if (password.equals(passwordConf)){
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(HashUserPassword(password));
                    Boolean admin = false;
                    addUser(user,admin);
                    JOptionPane.showMessageDialog(null, "Utilisateur Enregistré.");
                    return true;

                }else{
                    JOptionPane.showMessageDialog(null, "Les mots de passe de sont pas similaires.");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Votre compte n'est pas whitelist.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "L'email n'est pas une addrese mail.");
        }
        return false;
    }

    /**
     * Description -> login in users
     *
     * @Author benjamin, Pierre et Léonard
     *
     * @param email
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean login(String email, String password) throws SQLException {
        if (verifyEmail(email)){
            if (this.whitelist.isInWhitelist(email)){
                String passwordHash = HashUserPassword(password);
                if (CheckPassword(passwordHash, email)){
                    return true;

                }else{
                    JOptionPane.showMessageDialog(null, "Identifiant inconnu.");

                }
            }else{
                JOptionPane.showMessageDialog(null, "Votre compte n'est plus whitelist.");


            }
        }else{
            JOptionPane.showMessageDialog(null, "l'email n'est pas une addrese mail.");

        }
        return false;
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
            UserController user = new UserController();
            table.setModel(user.buildTableModel(user.allUser()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Description -> build JTable model
     *
     * @author benjamin, leonard, pierre
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    // Method to display data set in tabular form
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
