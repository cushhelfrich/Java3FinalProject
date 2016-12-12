package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Riley Laptop
 * @Assignment Name: java3finalproject
 * @Date: Oct 29, 2016
 * @Subclass Modify Description: ALlows user to modify account username and
 * password.
 */
//Imports
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//Begin Subclass Modify
class Modify {

//declarations
    Stage modifyScene = new Stage();
    int ModifyId;
    String decPW;

    /**
     * Account name, user name, and password received from dashboard textfields
     * to this scene
     *
     * @param actName
     * @param usrName
     * @param pw
     * @param ws
     */
    public void change(String actName, String usrName, String pw) throws SQLException {

        //declarations for Confirmation dialog
        Pane pane = new Pane();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));
        GridPane gridPane = new GridPane();

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Header text
        Text header = new Text(170, 15, "Modify Account\n Are you sure?");
        header.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        pane.getChildren().addAll(header); //add children to top pane
        header.setEffect(dropShadow);

        //GridPane labels           
        Label lblLUserName = new Label("Old Username =");
        lblLUserName.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lbluserNameGetText = new Label(getUser(actName));
        Label lblNewUserName = new Label("New Username =");
        lblNewUserName.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lblNewUserNameGetText = new Label(usrName);
        Label lblpassword = new Label("Old Password = ");
        lblpassword.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lblpasswordGetText = new Label(getPassword(actName));
        Label lblNewPassword = new Label("New Password = ");
        lblNewPassword.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lblNewPasswordGetText = new Label(pw);

        //Add panes to appropriate region
        bp.setTop(pane);
        bp.setCenter(gridPane);

        //Add ID's to Nodes for CSS
        bp.setId("bp");
        gridPane.setId("root");
        header.setId("text");

        //Set GridPane attributes
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(0, 0, 5, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Add hbox for account name 
        gridPane.add(getAccountHBox(actName), 0, 0, 4, 1);
        //Add User Name to grid pane
        if (!"".matches(usrName)) {
            gridPane.add(lblLUserName, 0, 1);
            GridPane.setConstraints(lblLUserName, 0, 1, 1, 1);
            gridPane.add(lbluserNameGetText, 1, 1);
            GridPane.setConstraints(lbluserNameGetText, 1, 1, 1, 1);
            //new user name
            gridPane.add(lblNewUserName, 2, 1);
            GridPane.setConstraints(lblNewUserName, 2, 1, 1, 1);
            gridPane.add(lblNewUserNameGetText, 3, 1);
            GridPane.setConstraints(lblNewUserNameGetText, 3, 1, 1, 1);
        }
        //Display old password to grid pane
        if (!"".matches(pw)) {
            gridPane.add(lblpassword, 0, 2);
            GridPane.setConstraints(lblpassword, 0, 2, 1, 1);
            gridPane.add(lblpasswordGetText, 1, 2);
            GridPane.setConstraints(lblpasswordGetText, 1, 2, 1, 1);
            //add new password to grid pane
            gridPane.add(lblNewPassword, 2, 2);
            GridPane.setConstraints(lblNewPassword, 2, 2, 1, 1);
            gridPane.add(lblNewPasswordGetText, 3, 2);
            GridPane.setConstraints(lblNewPasswordGetText, 3, 2, 1, 1);
        }
        //add Buttons
        gridPane.add(getBtnBox(usrName, pw, actName), 0, 4, 4, 1);

        //New stage
        Scene scene = new Scene(bp, 520, 220);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        modifyScene.setTitle("Modify Information");
        modifyScene.setScene(scene);
        modifyScene.show();

    }

    /**
     * Get hBox and buttons Business Logic for retrieving and modifying user
     * name.
     *
     * @return
     */
    private HBox getBtnBox(String un, String pw, String an) {

        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));

        Button btnConfirm = new Button("Confirm");
        Button btnEdit = new Button("Edit");

        //button styling
        btnConfirm.setId("btn");
        btnEdit.setId("btn");

        //lambda expression confirm and Edit
        btnConfirm.setOnAction((ActionEvent e) -> {
            modifyScene.close();
            if (!"".matches(un)) {
                modifyUser(un);
            }
            if (!"".matches(pw)) {
                try {
                    modifyPassword(pw, an);
                } catch (SQLException ex) {
                    Logger.getLogger(Modify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Dashboard.getAct();
            } catch (SQLException ex) {
                Logger.getLogger(Modify.class.getName()).log(Level.SEVERE, null, ex);
            }
            Dashboard.initAccountSet();
            Dashboard.clearHandler();//calls Static method in main          
        });//end confirm event handler

        //lambda expression Exit Program        
        btnEdit.setOnAction((ActionEvent e) -> {
            modifyScene.close();
        });//end edit event handler

        hBox.getChildren().addAll(btnConfirm, btnEdit);

        return hBox;
    }

    /**
     * hBox that holds innerGridPane account name
     *
     * @return
     */
    private HBox getAccountHBox(String an) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        //hBox.setPadding(new Insets(5, 5, 5, 5));

        //lables
        Label lblaccountName = new Label("Account Name:");
        lblaccountName.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lblaccountGetText = new Label(an);

        /*Set innerGridPane attributes */
        GridPane innerGridPane = new GridPane();
        innerGridPane.setAlignment(Pos.CENTER);
        innerGridPane.setPadding(new Insets(0, 0, 5, 10));
        innerGridPane.setHgap(5);
        innerGridPane.setVgap(5);

        //add Account Name to innergridpane
        innerGridPane.add(lblaccountName, 0, 0);
        GridPane.setConstraints(lblaccountName, 0, 0, 1, 1);
        innerGridPane.add(lblaccountGetText, 1, 0);
        GridPane.setConstraints(lblaccountGetText, 1, 0, 1, 1);

        hBox.getChildren().addAll(innerGridPane);

        return hBox;
    }

    /**
     * Class get existing user from database to display on u/i
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public String getUser(String name) throws SQLException {

        String userName = null;

        //retrieve old account user and PW        
        String findAct = "SELECT * FROM account WHERE account_name = '" + name + "' AND user_id = " + Login.currUser.getUserId();

        // Query User table for account_id to that matches account name.
        List<Map<String, Object>> results = Login.db.retrieveRecords(findAct);

        if (results != null && results.isEmpty()) // Query returned 0 results
        {
            Login.verify.noAct();
        } else if (results != null) {
            ModifyId = (Integer) results.get(0).get("account_id");//gets account_id
            userName = (String) results.get(0).get("username");//gets username
        }
        return userName;
    }

    /**
     * Passes Username and password to be modified by calling DBConnector class
     *
     * @param un
     */
    private void modifyUser(String un) {

        String modify = "UPDATE account SET username = '" + un + "' WHERE account_id= '" + ModifyId + "'";

        try {
            Login.db.modifyRecords(modify);
        } catch (SQLException ex) {
            System.out.println("Error while attempting to modify records: " + ex.toString());
        }
    }

    /**
     * Get Decrypted password
     *
     * @param an
     * @return
     */
    public String getPassword(String an) {

        Account dispAccount = new Account(an);//call account class and load account name

        try
        {        
            decPW = Dashboard.getAES().decrypt(dispAccount.getPassword(), an);//decrypt password
        } 
        catch (Exception ex) {
            Logger.getLogger(Modify.class.getName()).log(Level.SEVERE, null, ex);
        }


        return decPW;//return to gridpane

    }

    /**
     * Encrypt and modify password
     *
     * @param an
     */
    private void modifyPassword(String pw, String name) throws SQLException
    {

        String findAct = "SELECT * FROM account WHERE account_name = '" + name + "' AND user_id = " + Login.currUser.getUserId();

        // Query User table for account_id to that matches account name.
        List<Map<String, Object>> results = Login.db.retrieveRecords(findAct);

        if (results != null && results.isEmpty()) // Query returned 0 results
        {
            Login.verify.noAct();

        } else if (results != null){
            ModifyId = (Integer) results.get(0).get("account_id");//gets account_id

            try
            {
                String encpw = Dashboard.getAES().encrypt(pw, name);
                String modify = "UPDATE account SET password = '" + encpw + "' WHERE account_id= '" + ModifyId + "'";
                Login.db.modifyRecords(modify);
            } catch (SQLException ex) {
                System.out.println("Error while attempting to modify records: " + ex.toString());
            }
            catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | 
                    InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | 
                    KeyStoreException | CertificateException ex) {
                Login.verify.createAlert(Alert.AlertType.ERROR, "Error modifying account", 
                        "There was a problem processing your request, and the password wasn't modified. "
                            + "If this problem persists, contact the administrator. Error message: " + ex.getMessage());
            }
        }        
    }
} //End Subclass Modify
