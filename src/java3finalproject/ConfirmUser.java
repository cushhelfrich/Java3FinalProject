package java3finalproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Author Name: Cush
 * @Assignment Name: java3finalproject
 * @Date: Oct 27, 2016
 * @Subclass ConfirmUser Description:
 */
//Imports
//Begin Subclass ConfirmUser
public class ConfirmUser {

    Stage confirmUserStage = new Stage();
    //The main BorderPane
    BorderPane bpConfirmUser = new BorderPane();

    //Labels to show user
    Label lblUserName = new Label("Username");
    Label lblEmail = new Label("Email");
    Label lblFirstName = new Label("First Name");
    Label lblLastName = new Label("Last Name");
    Label lblPassword = new Label("Password");

    Text txtUserName = new Text();
    Text txtEmail = new Text();
    Text txtFirstName = new Text();
    Text txtLastName = new Text();
    Text pfPassword = new Text();

    Button btnConfirm = new Button("Confirm");
    Button btnEdit = new Button("Edit");

    public ConfirmUser(TextField tfUserName, TextField tfEmail,
            TextField tfFirstName, TextField tfLastName, PasswordField pfPassword) {

        this.txtUserName.setText(String.valueOf(tfUserName.getText()));
        this.txtEmail.setText(String.valueOf(tfEmail.getText()));
        this.txtFirstName.setText(String.valueOf(tfFirstName.getText()));
        this.txtLastName.setText(String.valueOf(tfLastName.getText()));
        this.pfPassword.setText(String.valueOf(pfPassword.getText()));
    }

    public void confirmUser() {

        bpConfirmUser.setTop(vbText());//Add vbTop to bpMain top by calling method
        bpConfirmUser.setCenter(gpCenter());//Add GridPane to center
        bpConfirmUser.setBottom(hbButtons());

        bpConfirmUser.setPadding(new Insets(10, 50, 50, 50));

        bpConfirmUser.setId("bp");
        //Create Scene and add mainBorder to it
        Scene myScene = new Scene(bpConfirmUser, 400, 300);

        //Setup Stage
        confirmUserStage.setTitle("Confirm Information"); //Set the title of the Stage
        myScene.getStylesheets().add(getClass().getClassLoader().getResource("createuser.css").toExternalForm());
        confirmUserStage.setScene(myScene);  //Add Scene to Stage

        confirmUserStage.show(); //Show Stage

    }

    public VBox vbText() {

        VBox vbTop = new VBox(); //VBox for text at the top

        vbTop.setAlignment(Pos.CENTER); //Align to center

        vbTop.setPadding(new Insets(1, 1, 15, 1));

        //Create the text for the BorderPane top
        Text mainText1 = new Text("User Confirmation");
        Text mainText2 = new Text("Is this information correct?");

        //Set attributes for the text
        mainText1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
        mainText2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 15));

        //SetId's
        mainText1.setId("text");
        mainText2.setId("text");

        vbTop.getChildren().addAll(mainText1, mainText2); //Add text to VBox

        return vbTop;

    } //End vbText method

    public GridPane gpCenter() {

        //Used to see lines for GridPane rows/colums for troubleshooting
        //gpSorts.setGridLinesVisible(true);
        GridPane gpEntries = new GridPane(); //Create GridPane instance

        //SetId
        gpEntries.setId("gp");

        //Set the gaps and padding
        gpEntries.setHgap(5);
        gpEntries.setVgap(5);

        /**
         * Note the following when adding to GridPane
         * GridPane.setConstraints(node, columnIndex, rowIndex, columnspan,
         * rowspan); gpSorts.add(node, columnIndex, rowIndex);
         *
         * Add Label and TextField for name and age to top of
         * bpCenterLeft(BorderPane)
         */
        gpEntries.add(lblUserName, 0, 0);
        GridPane.setConstraints(lblUserName, 0, 0, 1, 1);

        gpEntries.add(txtUserName, 1, 0);
        GridPane.setConstraints(txtUserName, 1, 0, 1, 1);

        gpEntries.add(lblEmail, 0, 1);
        GridPane.setConstraints(lblEmail, 0, 1, 1, 1);

        gpEntries.add(txtEmail, 1, 1);
        GridPane.setConstraints(txtEmail, 1, 1, 1, 1);

        gpEntries.add(lblFirstName, 0, 2);
        GridPane.setConstraints(lblFirstName, 0, 2, 1, 1);

        gpEntries.add(txtFirstName, 1, 2);
        GridPane.setConstraints(txtFirstName, 1, 2, 1, 1);

        gpEntries.add(lblLastName, 0, 3);
        GridPane.setConstraints(lblLastName, 0, 3, 1, 1);

        gpEntries.add(txtLastName, 1, 3);
        GridPane.setConstraints(txtLastName, 1, 3, 1, 1);

        gpEntries.add(lblPassword, 0, 4);
        GridPane.setConstraints(lblPassword, 0, 4, 1, 1);

        gpEntries.add(pfPassword, 1, 4);
        GridPane.setConstraints(pfPassword, 1, 4, 1, 1);

        //Set last label to good width for all labels in GridPane (0, #)
        lblPassword.setPrefWidth(75);

        return gpEntries;

    }

    /**
     * hbButtons Method: this will create a HBox to hold the buttons at the
     * bottom of the bpMain
     *
     * @return hbBottom
     */
    public HBox hbButtons() {

        HBox hbBottom = new HBox(10);  //HBox for buttons at the bottom

        hbBottom.setAlignment(Pos.CENTER); //Set Buttoms to center
        hbBottom.setPadding(new Insets(10, 1, 1, 1)); //Padd it

        //SetId
        btnConfirm.setId("btn");
        btnEdit.setId("btn");

        //Add buttons to the bottom HBox
        hbBottom.getChildren().addAll(btnConfirm, btnEdit);

        //Handlers
        btnConfirm.setOnAction(new ConfirmHandler()); //Handler for confirm
        btnEdit.setOnAction(new EditHandler()); //Handler for confirm

        return hbBottom;

    } //End hbButtons

    class ConfirmHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            
        }
    }
    
     class EditHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            
            confirmUserStage.close();
            
        }
    }

} //End Subclass ConfirmUser
