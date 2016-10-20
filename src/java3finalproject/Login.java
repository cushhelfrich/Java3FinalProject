package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Wayne Riley
 * @Assignment Name: login
 * @Date: Sep 22, 2016
 * @Description: Java III final Project - Password Wizard App - this is the
 * login screen that allows for account setup and accepts Username/password to
 * access individual accounts within MySQL database.
 *
 * https://www.javacodegeeks.com/2012/06/in-this-tutorial-i-will-design-nice.html
 */
//Imports
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Begin Class Login
//*********************Wayne Riley**************************************
public class Login extends Application {

    //instantiate subclass
    Dashboard db = new Dashboard();
    CreateUser createUser = new CreateUser();
    
    //[Char, 10/19] deleted
    
    private final Encryptor encrypt = new Encryptor();
    private final Label lblMessage = new Label();
    private final TextField txtUserName = new TextField();
    private final PasswordField pf = new PasswordField();

    @Override
    public void start(Stage primaryStage) {

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 50, 50, 50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 20, 20, 30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        Label lblPassword = new Label("Password");
        Button btnLogin = new Button("Login");

        //CUSH
        Button btnCreateUser = new Button("Create User");
        Button btnResetPassword = new Button("Reset Password");
        btnCreateUser.setPrefWidth(120);
        btnResetPassword.setPrefWidth(120);
        //END CUSH

        //Adding Nodes to GridPane layout
        //gridPane.setGridLinesVisible(true); //CUSH
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(lblMessage, 1, 2);
        gridPane.add(btnCreateUser, 1, 3); //CUSH
        gridPane.add(btnResetPassword, 1, 4); //CUSH
        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("Password Wizard");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        btnCreateUser.setId("btnCreateUser"); //CUSH
        btnResetPassword.setId("btnResetPassword"); //CUSH
        text.setId("text");

        //Action for btnLogin
        btnLogin.setOnAction(
                (ActionEvent e) -> {
                    processLogin(txtUserName.getText(), pf.getText());
                });
        
        //[Cush]Action for btnCreateUser
       btnCreateUser.setOnAction(
                (ActionEvent e) -> {
                    
                    createUser.createUser();
                    
                });
    
        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp, 400, 300);
        primaryStage.setTitle("Java III Final Project");
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
        // Sanitize inputs!
    private void processLogin(String user, String pw)
    {    
        Connection connection;
        DBConnector db = new DBConnector();
        lblMessage.setText("");
        
        // Connect to DB
        try
        {
            connection = db.makeConnection();            

            // Query User table for user, pw, and salt where user = user
            ResultSet rs = db.retrieveSaltedPW(user);
            
            rs.last();
            int rsSize = rs.getRow();
            
            if(rsSize == 0)
            {
                lblMessage.setText("No account exists for this user.");
                lblMessage.setTextFill(Color.RED);
            }
            else // Record returned
            {
                // These parameters will have to be modified, in accordance with group decisions
                String salt = rs.getString("salt");
                String pw_hash = rs.getString("password");
                
                byte[] byteSalt = Base64.getDecoder().decode(salt);
                
                if(encrypt.isExpectedPassword(pw, pw_hash, byteSalt))
                {
                    System.out.println("The dash will display");
                }
                
                else
                {
                    lblMessage.setText("Incorrect user or password");
                    lblMessage.setTextFill(Color.RED);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Failed to connect to db");
                    
        }
        
          txtUserName.setText("");
          pf.setText("");
    }

} //End Class Login
