package java3finalproject;

import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Author Name: Cush Helfrich
 * @Assignment Name: java3finalproject
 * @Date: Oct 18, 2016
 * @Subclass CreateUser Description:
 */
//Imports
//Begin Subclass CreateUser
public class CreateUser {

    Verify verify = new Verify();
    DBConnector dbConnection = new DBConnector();//Instance of connection to database

    //ConfirmUser confUser = new ConfirmUser();  //Create instance
    Stage createUserStage = new Stage();
    //The main BorderPane
    BorderPane bpCreateUser = new BorderPane();

    //Labels to show user
    Label lblUserName = new Label("Username");
    Label lblEmail = new Label("Email");
    Label lblFirstName = new Label("First Name");
    Label lblLastName = new Label("Last Name");
    Label lblPassword = new Label("Password");
    Label lblMessage = new Label("");

    //TextArea
    TextField tfUserName = new TextField();
    TextField tfEmail = new TextField();
    TextField tfFirstName = new TextField();
    TextField tfLastName = new TextField();

    PasswordField pfPassword = new PasswordField();//Password

    Button btnEnterUser = new Button("Enter");

    //Default constructor
    public CreateUser() {

    }

    //Constructor with arguments
    public CreateUser(Text txtUserName, Text txtEmail, Text txtFirstName,
            Text txtLastName, Text txtPassword) {

        this.tfUserName.setText(String.valueOf(txtUserName.getText()));
        this.tfEmail.setText(String.valueOf(txtEmail.getText()));
        this.tfFirstName.setText(String.valueOf(txtFirstName.getText()));
        this.tfLastName.setText(String.valueOf(txtLastName.getText()));
        this.pfPassword.setText(String.valueOf(txtPassword.getText()));
    }

    public void createUser() {

        bpCreateUser.setTop(vbText());//Add vbTop to bpMain top by calling method
        bpCreateUser.setCenter(gpCenter());//Add GridPane to center
        bpCreateUser.setBottom(hbButtons());

        tfUserName.setOnKeyPressed(new PressEnter());
        tfEmail.setOnKeyPressed(new PressEnter());
        tfFirstName.setOnKeyPressed(new PressEnter());
        tfLastName.setOnKeyPressed(new PressEnter());
        pfPassword.setOnKeyPressed(new PressEnter());

        bpCreateUser.setPadding(new Insets(10, 50, 50, 50));

        bpCreateUser.setId("bp");
        //Create Scene and add mainBorder to it
        Scene myScene = new Scene(bpCreateUser, 400, 300);

        //Setup Stage
        createUserStage.setTitle("Create User"); //Set the title of the Stage
        myScene.getStylesheets().add(getClass().getClassLoader().getResource("createuser.css").toExternalForm());
        createUserStage.setScene(myScene);  //Add Scene to Stage

        createUserStage.show(); //Show Stage

    }

    public VBox vbText() {

        VBox vbTop = new VBox(); //VBox for text at the top

        vbTop.setAlignment(Pos.CENTER); //Align to center

        vbTop.setPadding(new Insets(1, 1, 15, 1));

        //Create the text for the BorderPane top
        Text mainText1 = new Text("User creation");
        Text mainText2 = new Text("Please fill out the following fields");

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

        gpEntries.add(tfUserName, 1, 0);
        GridPane.setConstraints(tfUserName, 1, 0, 1, 1);

        gpEntries.add(lblEmail, 0, 1);
        GridPane.setConstraints(lblEmail, 0, 1, 1, 1);

        gpEntries.add(tfEmail, 1, 1);
        GridPane.setConstraints(tfEmail, 1, 1, 1, 1);

        gpEntries.add(lblFirstName, 0, 2);
        GridPane.setConstraints(lblFirstName, 0, 2, 1, 1);

        gpEntries.add(tfFirstName, 1, 2);
        GridPane.setConstraints(tfFirstName, 1, 2, 1, 1);

        gpEntries.add(lblLastName, 0, 3);
        GridPane.setConstraints(lblLastName, 0, 3, 1, 1);

        gpEntries.add(tfLastName, 1, 3);
        GridPane.setConstraints(tfLastName, 1, 3, 1, 1);

        gpEntries.add(lblPassword, 0, 4);
        GridPane.setConstraints(lblPassword, 0, 4, 1, 1);

        gpEntries.add(pfPassword, 1, 4);
        GridPane.setConstraints(pfPassword, 1, 4, 1, 1);

        gpEntries.add(lblMessage, 1, 5);
        GridPane.setConstraints(lblMessage, 1, 5, 1, 1);

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
        btnEnterUser.setId("btn");

        //Add buttons to the bottom HBox
        hbBottom.getChildren().addAll(btnEnterUser);

        //Handlers
        btnEnterUser.setOnAction(new EnterHandler()); //Handler for edit

        return hbBottom;

    } //End hbButtons

    class EnterHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            boolean checks = true;

            //Check if username is populated
            if (!verify.isData(tfUserName, lblUserName)) {

                tfUserName.clear();
                tfUserName.requestFocus();
                checks = false;

            }
            //Check if email is populated
            if (checks != false) {
                if (!verify.isData(tfEmail, lblEmail)) {

                    tfEmail.clear();
                    tfEmail.requestFocus();
                    checks = false;

                }
            }
            //Check if first name is populated
            if (checks != false) {
                if (!verify.isData(tfFirstName, lblFirstName)) {

                    tfFirstName.clear();
                    tfFirstName.requestFocus();
                    checks = false;

                }
            }
            //Check if last name is populated
            if (checks != false) {
                if (!verify.isData(tfLastName, lblLastName)) {

                    tfLastName.clear();
                    tfLastName.requestFocus();
                    checks = false;

                }
            }
            //Check if password name is populated
            if (checks != false) {
                if (!verify.isData(pfPassword, lblPassword)) {

                    pfPassword.clear();
                    pfPassword.requestFocus();
                    checks = false;

                }
            }

            //Check email syntax
            if (checks != false) {
                if (!Verify.checkEmail(tfEmail.getText())) {
                    tfEmail.clear();
                    tfEmail.requestFocus();
                    checks = false;

                }

            }
                //Check if username is already used
                if (checks != false) {
                    String query = "SELECT * FROM user WHERE username = '"
                            + tfUserName.getText() + "'";
                    
                    List<Map<String, Object>> results = dbConnection.retrieveRecords(query);
                    
                    if (!results.isEmpty()) 
                    {

                            lblMessage.setText("Username already used");
                            lblMessage.setTextFill(Color.RED);
                            tfUserName.clear();
                            tfUserName.requestFocus();
                            checks = false;

                    }

                }

            if (checks) {
                ConfirmUser confirm = new ConfirmUser(tfUserName, tfEmail,
                        tfFirstName, tfLastName, pfPassword);

                confirm.confirmUser(); //Call method to show confirm user stage
                createUserStage.close(); //Close the stage

            }
        }
    }

    class PressEnter implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent pressenter) {

            if (pressenter.getCode() == KeyCode.ENTER) {

                boolean checks = true;

                //Check if username is populated
                if (!verify.isData(tfUserName, lblUserName)) {

                    tfUserName.clear();
                    tfUserName.requestFocus();
                    checks = false;

                }
                //Check if email is populated
                if (checks != false) {
                    if (!verify.isData(tfEmail, lblEmail)) {

                        tfEmail.clear();
                        tfEmail.requestFocus();
                        checks = false;

                    }
                }
                //Check if first name is populated
                if (checks != false) {
                    if (!verify.isData(tfFirstName, lblFirstName)) {

                        tfFirstName.clear();
                        tfFirstName.requestFocus();
                        checks = false;

                    }
                }
                //Check if last name is populated
                if (checks != false) {
                    if (!verify.isData(tfLastName, lblLastName)) {

                        tfLastName.clear();
                        tfLastName.requestFocus();
                        checks = false;

                    }
                }
                //Check if password name is populated
                if (checks != false) {
                    if (!verify.isData(pfPassword, lblPassword)) {

                        pfPassword.clear();
                        pfPassword.requestFocus();
                        checks = false;

                    }
                }

                //Check email syntax
                if (checks != false) {
                    if (!Verify.checkEmail(tfEmail.getText())) {
                        tfEmail.clear();
                        tfEmail.requestFocus();
                        checks = false;

                    }

                }

                //Check if username is already used
                if (checks != false) {
                    String query = "SELECT * FROM user WHERE username = '"
                            + tfUserName.getText() + "'";
                    
                    List<Map<String, Object>> results = dbConnection.retrieveRecords(query);
                    
                    if (!results.isEmpty()) 
                    {

                            lblMessage.setText("Username already used");
                            lblMessage.setTextFill(Color.RED);
                            tfUserName.clear();
                            tfUserName.requestFocus();
                            checks = false;

                    }

                }

                if (checks) {
                    ConfirmUser confirm = new ConfirmUser(tfUserName, tfEmail,
                            tfFirstName, tfLastName, pfPassword);

                    confirm.confirmUser(); //Call method to show confirm user stage
                    createUserStage.close(); //Close the stage

                }

            }

        }
    }

} //End Subclass CreateUser
