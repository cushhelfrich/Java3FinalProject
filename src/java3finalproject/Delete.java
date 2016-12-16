package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Riley Laptop
 * @Assignment Name: java3finalproject
 * @Date: Oct 26, 2016
 * @Subclass Delete Description: Variable passed from Dashbooard account
 * textfiled to delete account
 */
//Imports
import java.io.IOException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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

//Begin Subclass Delete
//**********Start Wayne Code ***************************
class Delete {

    //declarations
    Stage deleteScene = new Stage();
    Button btnConfirm = new Button("Yes");
    Button btnEdit = new Button("No");
    Verify verify = new Verify();

    /**
     * Account name, user name, password and website received from dashboard
     * textfields to this scene
     *
     * @param actName
     * @param usrName
     * @param pw
     * @param ws
     */
    public void delete(String actName) {

        //Borderpane and Gridpane
        Pane pane = new Pane();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));
        GridPane gridPane = new GridPane();

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Header information
        Text header = new Text(35, 15, "Delete Account\n Are You Sure?");
        header.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        pane.getChildren().addAll(header); //add children to top pane
        header.setEffect(dropShadow);

        //labels        
        Label lblaccountName = new Label("Account Name:");
        lblaccountName.setFont(Font.font("", FontWeight.BOLD, 12));
        Label lblaccountGetText = new Label(actName);

        /* Add panes to appropriate region */
        bp.setTop(pane);
        bp.setCenter(gridPane);

        //Add ID's to Nodes
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

        //add Buttons
        gridPane.add(btnConfirm, 0, 2);
        GridPane.setConstraints(btnConfirm, 0, 2, 1, 1);
        gridPane.add(btnEdit, 1, 2);
        GridPane.setConstraints(btnEdit, 1, 2, 1, 1);

        //lambda expression confirm and Edit
        btnConfirm.setOnAction((ActionEvent e) -> {
            String errorMsg = "There was a problem processing your request, and account details "
                    + "were not deleted from the system. Error message: ";
            
            if (!Dashboard.isDuplicate(actName)) {
                verify.noAct();
            } 
            else {
                Key key = null;
            try 
            {
                // store the existing key in case record erasure fails later
                 key = Dashboard.getAES().loadKey(actName);
                 
                 // if a non-null Key was successfully retrieved
                 if(key != null)
                 {
                    // try to delete the key
                    Dashboard.getAES().deleteKey(actName);
                 }
                 
                // try removing the account from the database, even if it wasn't in the KeyStore
                removeAct(actName);
                Dashboard.deleteAccount(actName);   // update the display
                Login.verify.createAlert(Alert.AlertType.INFORMATION, "Success", "The account was deleted successfully.");
            }
            // fetching or deleting the Key failed
            catch(KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException ex)
            {
                Login.verify.createAlert(Alert.AlertType.WARNING, "Error deleting account", errorMsg + ex.getMessage()); 
            }
            // deleting the record failed
            catch (SQLException ex) 
            {
                Login.verify.createAlert(Alert.AlertType.WARNING, "Error deleting account", errorMsg + ex.getMessage());
                
                // try to reverse the deletion
                try
                {
                    Dashboard.getAES().storeKey(actName, key);
                } 
                catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException exDelete) 
                {
                    Login.verify.createAlert(Alert.AlertType.WARNING, "Error deleting acount",
                        "A series of errors occurred, which may result in data corruption. "
                                + "The system will now exit. Error message:  " + ex.getMessage());
                    System.exit(1);
                }
            }
            Dashboard.clearHandler();//calls Static method in main
            deleteScene.close();//closes scene
            }
        });//end confirm event handler

        //lambda expression Exit Program        
        btnEdit.setOnAction((ActionEvent e) -> {
            deleteScene.close();
        });//end edit event handler

        //New stage
        Scene scene = new Scene(bp, 255, 180);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        deleteScene.setTitle("Delete Information");
        deleteScene.setScene(scene);
        deleteScene.show();
    }

    /**
     * Calls retrieveRecords method in DBConnector class to determine account_id
     * and then calls modifyRecords to delete account information in SQL.
     *
     * @throws SQLException
     */
    private void removeAct(String actName) throws SQLException {

        String rmvAct = "SELECT * FROM account WHERE account_name = '" + actName + "' AND user_id = " + Login.currUser.getUserId();

        // Query User table for account_id to that matches account name.
        List<Map<String, Object>> results = Login.db.retrieveRecords(rmvAct);

        if (results != null)
        {
            int deleteId = (Integer) results.get(0).get("account_id");

            //SQL string to delete account account row
            String dltAct = "DELETE FROM account WHERE account_id = '" + deleteId + "'";

            //Calls modifyRecords in DBConnector to execute SQL string and delete account
            Login.db.modifyRecords(dltAct);
        }
        
        // else an exception occured and results are null
    }
} //End Subclass Delete
//****************************End Wayne Code***************************
