package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Wayne Riley
 * @Assignment Name: java3finalproject
 * @Date: Oct 17, 2016
 * @Subclass Add Description: Content (Account, Username, password) passed from
 * dashboard textfields and inserted into database.
 */
//Imports
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//Begin Subclass Add
//***************Start Wayne Code***************************
public class Add {

    //declarations
    Stage addScene = new Stage();
    Button btnConfirm = new Button("Confirm");
    Button btnEdit = new Button("Edit");

    /**
     * Account name, user name, and password received from dashboard textfields
     * to this scene
     *
     * @param actName
     * @param usrName
     * @param pw
     * @param ws
     */
    public void insert(String actName, String usrName, String pw) {

        //declarations for Confirmation dialog
        Pane pane = new Pane();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));
        GridPane gridPane = new GridPane();

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        Text header = new Text(15, 15, "Confirm New Account");
        header.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        pane.getChildren().addAll(header); //add children to top pane
        header.setEffect(dropShadow);

        //labels        
        Label lblaccountName = new Label("Account Name:");
        lblaccountName.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lblaccountGetText = new Label(actName);
        Label lblLUserName = new Label("User Name:");
        lblLUserName.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lbluserNameGetText = new Label(usrName);
        Label lblpassword = new Label("Password:");
        lblpassword.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lblpasswordGetText = new Label(pw);

        /* Add panes to appropriate region */
        bp.setTop(pane);
        bp.setCenter(gridPane);

        //Add ID's to Nodes for CSS
        bp.setId("bp");
        gridPane.setId("root");
        btnConfirm.setId("btn");
        btnEdit.setId("btn");
        header.setId("text");

        /*Set GridPane attributes */
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //add Account Name to grid pane
        gridPane.add(lblaccountName, 0, 0);
        GridPane.setConstraints(lblaccountName, 0, 0, 1, 1);
        gridPane.add(lblaccountGetText, 1, 0);
        GridPane.setConstraints(lblaccountGetText, 1, 0, 1, 1);
        //Add User Name to grid pane
        gridPane.add(lblLUserName, 0, 1);
        GridPane.setConstraints(lblLUserName, 0, 1, 1, 1);
        gridPane.add(lbluserNameGetText, 1, 1);
        GridPane.setConstraints(lbluserNameGetText, 1, 1, 1, 1);
        //add password to grid pane
        gridPane.add(lblpassword, 0, 2);
        GridPane.setConstraints(lblpassword, 0, 2, 1, 1);
        gridPane.add(lblpasswordGetText, 1, 2);
        GridPane.setConstraints(lblpasswordGetText, 1, 2, 1, 1);

        //add Buttons
        gridPane.add(btnConfirm, 0, 4);
        GridPane.setConstraints(btnConfirm, 0, 4, 1, 1);
        gridPane.add(btnEdit, 1, 4);
        GridPane.setConstraints(btnEdit, 1, 4, 1, 1);

        //lambda expression confirm and Edit
        btnConfirm.setOnAction((ActionEvent e) -> {
            addScene.close();
            String errorMsg = "An error occurred, and the account could not be added to records. "
                    + "Contact an administrator if the problem persists. Error message: ";

            //****************Start Bill Code*********************
            /* Try creating an Account object with the encrypted password and then try inserting
            the credentials in the database
            */
            try
            {
                // try encrypting the password during Account creation
                Account newAct = new Account(actName, usrName, pw);
                
                // try inserting the new credentials in database table Account
                boolean insertSuccess = newAct.insert(actName, usrName, newAct.getPassword());
                
                // if database insert is successful
                if(insertSuccess == true)            
                {
                    System.out.println("Insert " + actName + " " + usrName + " " + pw + " " + " into database");
                    Dashboard.updateAccountSet(newAct); //calls method in Dashboard to update TextArea and Account list
                    Login.verify.createAlert(Alert.AlertType.INFORMATION, "Success", "Account was successfully created.");
                }
            }
            // catch error generated during exception or key storage
            catch(NoSuchAlgorithmException | KeyStoreException | IOException | NoSuchPaddingException | 
                    InvalidKeyException | CertificateException | InvalidAlgorithmParameterException | 
                    IllegalBlockSizeException | BadPaddingException ex)
            {
                Login.verify.createAlert(Alert.AlertType.WARNING, "Encryption error",
                        errorMsg + ex.getMessage());
            }
            // catch error generated while trying to add a database record
            catch (SQLException ex) 
            {
                // try to undo the addition of the account key to the KeyStore
                try 
                {
                    Dashboard.getAES().deleteKey(actName);
                    Login.verify.createAlert(Alert.AlertType.WARNING, "Database error",
                        errorMsg + ex.getMessage());
                } 
                // catch error generated during key deletion
                catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex1) {
                    Login.verify.createAlert(Alert.AlertType.WARNING, "Error adding acount",
                        "A series of errors occurred, which may result in data corruption. "
                                + "The system will now exit. Error message:  " + ex.getMessage());
                    System.exit(1);
                }
            }

            //****************End Bill Code*********************
            
            Dashboard.clearHandler();//clears all textfields
        });//end confirm event handler

        //lambda expression Exit Program        
        btnEdit.setOnAction(
                (ActionEvent e) -> {
                    addScene.close();
                }
        );//end edit event handler

        //New stage
        Scene scene = new Scene(bp, 275, 180);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        addScene.setTitle("Add Information");
        addScene.setScene(scene);
        addScene.show();
    }
    //*********End Wayne's Code******************
}//End Subclass Add
