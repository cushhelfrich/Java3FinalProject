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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
//***********************Wayne***************************
class Delete {

    //declarations
    Stage deleteScene = new Stage();
    Button btnConfirm = new Button("Yes");
    Button btnEdit = new Button("No");

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

        //declarations for Confirmation dialog
        Pane pane = new Pane();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));
        GridPane gridPane = new GridPane();

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        Text header = new Text(35, 15, "Delete Account\n Are You Sure?");
        header.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        pane.getChildren().addAll(header); //add children to top pane
        header.setEffect(dropShadow);

        //labels        
        Label lblaccountName = new Label("Account Name:");
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
            deleteScene.close();
            //!!!!!!!!!!!!!!!!!insert business logic or call class!!!!!!!!!!!!!!!

            System.out.println("Delete " + actName + " from database");
            Dashboard.clearHandler();//calls Static method in main
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

} //End Subclass Delete
