package com.iStore.View;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iStore.Database.ConnectionDatabase;
import com.iStore.Users.UserController;
import com.iStore.Users.UserModel;
import com.iStore.Whitelist.WhitelistController;

public class AppView implements ActionListener {
    static JFrame frame_login;
    static JFrame frame_register;
    static JFrame frame_home;
    static JFrame frame_app;
    static JFrame frame_select_modif_user;
    static JFrame frame_modif_user;
    static JFrame frame_delete_user;
    private JPanel panel_home;
    private JPanel panel_list;
    private JPanel panel_main;
    private JButton button_login_home;
    private JButton button_register_home;
    private JButton button_register;
    private JButton button_login;
    private JButton button_mail_list;
    private JButton button_modif_user;
    private JButton button_select_modif_user;
    private JButton button_confirm_modif_user;
    private JButton button_delete_user;
    private JButton button_confirm_delete_user;
    private  JDialog windows_login;
    private JDialog windows_home;
    private JDialog windows_register;
    private JDialog windows_select_modif_user;
    private JDialog windows_modif_user;
    private JDialog windows_delete_user;
    private JTextField field_user_login;
    private JTextField field_password_login;
    private JTextField field_mail_register;
    private JTextField field_password_register;
    private JTextField field_confirm_password_register;
    private JTextField field_name_register;
    private JTextField field_mail_list;
    private JTextField field_select_modif_user;
    private JTextField field_name_modif_user;
    private JTextField field_password_modif_user;
    private JTextField field_mail_modif_user;
    private JTextField field_delete_user;
    private UserController user_controller = new UserController();
    private UserModel utilisateur = new UserModel();

    private final WhitelistController whitelist = new WhitelistController();
    private JMenuItem menuitem_1_home;
    private JMenuItem menuitem_2_home;
    private JTable table_user;
    private JTable table_whitelist;




    public JPanel getRootPanel() {
        if (this.panel_main == null) {

            this.panel_main = new JPanel();
            this.panel_home = new JPanel();

            this.panel_main.setLayout(new BorderLayout());
            this.panel_home.setLayout(new GridBagLayout());

            this.panel_main.add(this.panel_home,BorderLayout.CENTER);

            JMenuBar menubar_home = new JMenuBar();
            this.panel_main.add(menubar_home,BorderLayout.NORTH);

            JMenu menu_home = new JMenu("Menu");
            menubar_home.add(menu_home);

            this.menuitem_1_home = new JMenuItem("Liste des utilisateurs");
            this.menuitem_1_home.addActionListener(this);
            menu_home.add(this.menuitem_1_home);

            this.menuitem_2_home = new JMenuItem("Ajouter un utilisateur");
            this.menuitem_2_home.addActionListener(this);
            menu_home.add(this.menuitem_2_home);
        }
        dialog_Home();
        return this.panel_main;
    }

    public void app_View(){
        frame_app = new JFrame("Gestion de stock");

        AppView placeView = new AppView();
        frame_app.add(placeView.getRootPanel());

        frame_app.setSize(600, 600);
        frame_app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_app.setVisible(false);
        frame_app.setLocationRelativeTo(null);

    }
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        // PARTIE LOGIN
        if(source == this.button_login_home)
        {
            // Créer une boîte de dialogue
            this.windows_login = new JDialog(frame_login, "Login");
            this.windows_login.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 5, 0);

            JLabel label_user_login = new JLabel("Username :");
            this.windows_login.add(label_user_login,c);

            c.gridy = 1;
            c.insets = new Insets(0, 0, 50, 0);

            this.field_user_login = new JTextField();
            this.field_user_login.setPreferredSize(new Dimension(300,30));
            this.windows_login.add(field_user_login,c);

            c.gridy = 2;
            c.insets = new Insets(0, 0, 5, 0);

            JLabel label_password_login = new JLabel("Password :");
            this.windows_login.add(label_password_login,c);

            c.gridy = 3;
            c.insets = new Insets(0, 0, 50, 0);

            this.field_password_login = new JTextField();
            this.field_password_login.setPreferredSize(new Dimension(300,30));
            this.windows_login.add(field_password_login,c);

            c.gridy = 4;
            c.insets = new Insets(0, 0, 0, 0);

            this.button_login = new JButton("Login");
            this.button_login.setPreferredSize(new Dimension(125,25));
            this.button_login.addActionListener(this);
            this.windows_login.add(button_login,c);

            this.windows_login.setSize(400, 400);
            this.windows_login.setLocationRelativeTo(null);
            this.windows_login.setVisible(true);
        }

        // PARTIE REGISTER

        if(source == this.button_register_home)
        {
            // Créer une boîte de dialogue
            this.windows_register = new JDialog(frame_register, "Register");
            windows_register.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0, 0, 5, 0);

            JLabel label_username_register = new JLabel("Nom d'utilisateur :");
            this.windows_register.add(label_username_register,c);

            c.gridy = 1;
            c.insets = new Insets(0, 0, 30, 0);

            this.field_name_register = new JTextField();
            this.field_name_register.setPreferredSize(new Dimension(300,30));
            this.windows_register.add(this.field_name_register,c);

            c.gridy = 2;
            c.insets = new Insets(0, 0, 5, 0);

            JLabel label_mail_register = new JLabel("E-Mail :");
            this.windows_register.add(label_mail_register,c);

            c.gridy = 3;
            c.insets = new Insets(0, 0, 30, 0);

            this.field_mail_register = new JTextField();
            this.field_mail_register.setPreferredSize(new Dimension(300,30));
            this.windows_register.add(this.field_mail_register,c);

            c.gridy = 4;
            c.insets = new Insets(0, 0, 5, 0);

            JLabel label_password_register = new JLabel("Password :");
            this.windows_register.add(label_password_register,c);

            c.gridy = 5;
            c.insets = new Insets(0, 0, 30, 0);

            this.field_password_register = new JTextField();
            this.field_password_register.setPreferredSize(new Dimension(300,30));
            this.windows_register.add(this.field_password_register,c);

            c.gridy = 6;
            c.insets = new Insets(0, 0, 5, 0);

            JLabel label_confirm_password_register = new JLabel("Confirm Password :");
            this.windows_register.add(label_confirm_password_register,c);

            c.gridy = 7;
            c.insets = new Insets(0, 0, 30, 0);

            this.field_confirm_password_register = new JTextField();
            this.field_confirm_password_register.setPreferredSize(new Dimension(300,30));
            this.windows_register.add(this.field_confirm_password_register,c);

            c.gridy = 8;
            c.insets = new Insets(0, 0, 0, 0);

            this.button_register = new JButton("Register");
            this.button_register.setPreferredSize(new Dimension(125,25));
            this.button_register.addActionListener(this);
            this.windows_register.add(this.button_register,c);

            this.windows_register.setSize(400, 400);
            this.windows_register.setLocationRelativeTo(null);
            this.windows_register.setVisible(true);
        }
        if(source == this.button_login)
        {
            try {
                if (this.user_controller.login(this.field_user_login.getText(),this.field_password_login.getText())){
                frame_app.setVisible(true);
                ResultSet userData = this.user_controller.getUser(this.field_user_login.getText());
                userData.next();
                this.utilisateur = new UserModel(userData.getLong("id"), userData.getString("name"), userData.getString("email"), userData.getBoolean("is_admin"), userData.getString("password"));
                System.out.println(this.utilisateur.getName());
                this.windows_login.dispose();
                this.windows_home.dispose();
                 }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (source == this.button_register){
            try {
                if (this.user_controller.register(this.field_name_register.getText(), this.field_mail_register.getText(), this.field_password_register.getText(), this.field_confirm_password_register.getText())){
                    frame_app.setVisible(true);
                    ResultSet userData =  this.user_controller.getUser(this.field_mail_register.getText());
                    userData.next();
                    this.utilisateur = new UserModel(userData.getLong("id"), userData.getString("name"), userData.getString("email"), userData.getBoolean("is_admin"), userData.getString("password"));
                    System.out.println(this.utilisateur.getName());
                    this.windows_register.dispose();
                    this.windows_home.dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
        if(source == this.menuitem_1_home){
            clean_home();
            list_Home();
        }
        if(source == this.menuitem_2_home){
            clean_home();
            create_Home();
        }
        if(source == this.button_modif_user){
            this.windows_select_modif_user = new JDialog(frame_select_modif_user, "Modifier un utilisateur");
            this.windows_select_modif_user.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0,0,5,0);

            JLabel label_select_modif_user = new JLabel("Entrer l'utilisateur à modifier");
            this.windows_select_modif_user.add(label_select_modif_user,c);

            c.gridy = 1;
            c.insets = new Insets(0,0,20,0);

            this.field_select_modif_user = new JTextField();
            this.field_select_modif_user.setPreferredSize(new Dimension(300,30));
            this.windows_select_modif_user.add(this.field_select_modif_user,c);

            c.gridy = 2;
            c.insets = new Insets(0,0,0,0);

            this.button_select_modif_user = new JButton("Continuer");
            this.button_select_modif_user.setPreferredSize(new Dimension(125,25));
            this.button_select_modif_user.addActionListener(this);
            this.windows_select_modif_user.add(this.button_select_modif_user,c);

            this.windows_select_modif_user.setSize(400, 400);
            this.windows_select_modif_user.setLocationRelativeTo(null);
            this.windows_select_modif_user.setVisible(true);
        }
        if(source == this.button_select_modif_user){
            this.windows_select_modif_user.dispose();

            this.windows_modif_user = new JDialog(frame_modif_user, "Modifier un utilisateur");
            this.windows_modif_user.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0,0,5,10);

            JLabel label_name_modif_user = new JLabel("Nom :");
            this.windows_modif_user.add(label_name_modif_user,c);

            c.gridy = 1;
            c.insets = new Insets(0,0,20,10);
            JLabel label_name_user = new JLabel("Nom utilisateur");
            this.windows_modif_user.add(label_name_user,c);

            c.gridy = 2;
            c.insets = new Insets(0,0,20,10);

            this.field_name_modif_user = new JTextField();
            this.field_name_modif_user.setPreferredSize(new Dimension(200,30));
            this.windows_modif_user.add(this.field_name_modif_user,c);

            c.gridy = 3;
            c.insets = new Insets(0,0,5,10);

            JLabel label_mail_modif_user = new JLabel("Mail :");
            this.windows_modif_user.add(label_mail_modif_user,c);

            c.gridy = 4;
            c.insets = new Insets(0,0,20,10);
            JLabel label_mail_user = new JLabel("Adresse e-mail");
            this.windows_modif_user.add(label_mail_user,c);

            c.gridy = 5;
            c.insets = new Insets(0,0,20,10);

            this.field_mail_modif_user = new JTextField();
            this.field_mail_modif_user.setPreferredSize(new Dimension(200,30));
            this.windows_modif_user.add(this.field_mail_modif_user,c);

            c.gridx = 1;
            c.gridy = 0;
            c.insets = new Insets(0,10,5,0);

            JLabel label_password_modif_user = new JLabel("Mot de passe :");
            this.windows_modif_user.add(label_password_modif_user,c);

            c.gridy = 1;
            c.insets = new Insets(0,10,20,0);
            JLabel label_password_user = new JLabel("Mot de passe");
            this.windows_modif_user.add(label_password_user,c);

            c.gridy = 2;
            c.insets = new Insets(0,10,20,0);

            this.field_password_modif_user = new JTextField();
            this.field_password_modif_user.setPreferredSize(new Dimension(200,30));
            this.windows_modif_user.add(this.field_password_modif_user,c);

            c.gridy = 5;
            c.insets = new Insets(0,10,20,0);

            this.button_confirm_modif_user = new JButton("Confirmer");
            this.button_confirm_modif_user.setPreferredSize(new Dimension(125,25));
            this.button_confirm_modif_user.addActionListener(this);
            this.windows_modif_user.add(this.button_confirm_modif_user,c);

            this.windows_modif_user.setSize(600, 400);
            this.windows_modif_user.setLocationRelativeTo(null);
            this.windows_modif_user.setVisible(true);
        }
        if(source == this.button_delete_user){
            this.windows_delete_user = new JDialog(frame_delete_user, "Supprimer un utilisateur");
            this.windows_delete_user.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(0,0,5,0);

            JLabel label_delete_user = new JLabel("Entrer l'utilisateur à supprimer");
            this.windows_delete_user.add(label_delete_user,c);

            c.gridy = 1;
            c.insets = new Insets(0,0,20,0);

            this.field_delete_user = new JTextField();
            this.field_delete_user.setPreferredSize(new Dimension(300,30));
            this.windows_delete_user.add(this.field_delete_user,c);

            c.gridy = 2;
            c.insets = new Insets(0,0,0,0);

            this.button_confirm_delete_user = new JButton("Supprimer");
            this.button_confirm_delete_user.setPreferredSize(new Dimension(125,25));
            this.button_confirm_delete_user.addActionListener(this);
            this.windows_delete_user.add(this.button_confirm_delete_user,c);

            this.windows_delete_user.setSize(400, 400);
            this.windows_delete_user.setLocationRelativeTo(null);
            this.windows_delete_user.setVisible(true);
        }
    }
    public void clean_home(){
        this.panel_home.removeAll();
        this.panel_home.validate();
        this.panel_home.repaint();
    }
    public void dialog_Home(){
        this.windows_home = new JDialog(frame_home,"Login / Register");
        this.windows_home.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 50, 0);

        c.gridx = 0;
        c.gridy = 0;

        this.button_login_home = new JButton("Login");
        this.button_login_home.setPreferredSize(new Dimension(100,25));
        this.button_login_home.addActionListener(this);
        this.windows_home.add(this.button_login_home,c);

        c.gridy = 1;

        this.button_register_home = new JButton("Register");
        this.button_register_home.setPreferredSize(new Dimension(100,25));
        this.button_register_home.addActionListener(this);
        this.windows_home.add(this.button_register_home,c);

        this.windows_home.setSize(400, 400);
        this.windows_home.setLocationRelativeTo(null);
        this.windows_home.setVisible(true);

    }
    public void list_Home(){

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 20, 5);

        this.button_modif_user = new JButton("Modifier");
        this.button_modif_user.setPreferredSize(new Dimension(125,25));
        this.button_modif_user.addActionListener(this);
        this.panel_home.add(button_modif_user,c);

        c.gridx = 1;
        c.insets = new Insets(0, 5, 20, 0);

        this.button_delete_user = new JButton("Delete");
        this.button_delete_user.setPreferredSize(new Dimension(125,25));
        this.button_delete_user.addActionListener(this);
        this.panel_home.add(button_delete_user,c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(0, 0, 0, 0);
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;

        this.table_user = new JTable();
        this.user_controller.loadDataSet(this.table_user);
        TableColumnModel columnModel = this.table_user.getColumnModel();
        columnModel.getColumn(2).setPreferredWidth(250);
        this.panel_home.add(this.table_user,c);
    }

    public void create_Home(){

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 5, 0);

        JLabel label_mail_list = new JLabel("E-mail à ajouter à la Liste Blanche :");
        this.panel_home.add(label_mail_list,c);

        c.gridy = 1;
        c.insets = new Insets(0, 0, 50, 0);

        this.field_mail_list = new JTextField();
        this.field_mail_list.setPreferredSize(new Dimension(300,30));
        this.panel_home.add(this.field_mail_list,c);

        c.gridy = 2;
        c.insets = new Insets(0, 0, 20, 0);

        this.button_mail_list = new JButton("Create");
        this.button_mail_list.setPreferredSize(new Dimension(125,25));
        this.panel_home.add(this.button_mail_list,c);

        c.gridy = 3;

        this.table_whitelist = new JTable();
        this.whitelist.loadDataSet(this.table_whitelist);
        TableColumnModel columnModel = this.table_whitelist.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(300);
        this.panel_home.add(this.table_whitelist,c);


    }
}