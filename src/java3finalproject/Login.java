package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Contributors: Wayne Riley, Cush Helfrich, Charlotte Hirschberger
 * @Assignment Name: login
 * @Date: Sep 22, 2016
 * @Description: Java III final Project - Password Wizard App - this is the
 * login screen that allows for account setup and accepts Username/password to
 * access individual accounts within MySQL database.
 *
 * https://www.javacodegeeks.com/2012/06/in-this-tutorial-i-will-design-nice.html
 */
//Imports
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
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
    Dashboard dashboard = new Dashboard();

    public static Verify verify = new Verify();
    public static DBConnector db;
    private final Encryptor encrypt = new Encryptor();
    private final Label lblMessage = new Label();
    private final TextField txtUserName = new TextField();
    private final PasswordField pf = new PasswordField();
    public static User currUser;

    @Override
    public void start(Stage primaryStage) {
        db = new DBConnector();
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
        //Button btnResetPassword = new Button("Reset Password");
        btnCreateUser.setPrefWidth(100);
        //btnResetPassword.setPrefWidth(120);
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
        //gridPane.add(btnResetPassword, 1, 4); //CUSH
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
        btnLogin.setId("btn");
        btnCreateUser.setId("btnCreateUser"); //CUSH
        //btnResetPassword.setId("btnResetPassword"); //CUSH
        text.setId("text");

        //[Scott]Add Tooltip to User Name Field
        Tooltip userTip = new Tooltip();
        userTip.setText("Don't have a User Name?\n"
                + "Select \"Create User\" below.\n");
        txtUserName.setTooltip(userTip);

        //[Scott]Add Tooltip to Password Field
        Tooltip pfTip = new Tooltip();
        pfTip.setText("Forgot your password?\n"
                + "Select Reset Password below.\n");
        pf.setTooltip(pfTip);

        //[Scott]Enter Button Handler binding
        EventHandler<KeyEvent> enterHandler = new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (processLogin(txtUserName, pf)) {
                        primaryStage.close();
                    }
                }
            }
        };

        txtUserName.setOnKeyPressed(enterHandler);
        pf.setOnKeyPressed(enterHandler);

        //Action for btnLogin
        btnLogin.setOnAction(
                (ActionEvent e) -> {
                    if (processLogin(txtUserName, pf)) {
                        primaryStage.close();
                    }
                });

        //[Cush]Action for btnCreateUser
        btnCreateUser.setOnAction(
                (ActionEvent e) -> {

                    //Moved here so user can create multiple users before login
                    CreateUser createUser = new CreateUser();
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

        /**
         * Start Charlotte's code This snippet adapted from
         * https://www.tutorialspoint.com/java/lang/runtime_addshutdownhook.htm
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("The system is closing");
            }
        });
    }

    /**
     * Charlotte's code This function is responsible for validating textfield
     * entries and creating the SQL statement used to query the User table for
     * the provided username. If a record is found, this function computes the
     * hash for the provided password and compares it to the retrieved one. If
     * the user's entry and the hash match, Dashboard is loaded and a User
     * object is created.
     *
     * @param user textfield entry
     * @param pw textfield entry
     * @return boolean indicates whether login was successful
     */
    private boolean processLogin(TextField txtUserName, PasswordField pf) {
        boolean bool = false;
        lblMessage.setText("");
        String user = txtUserName.getText();
        String pw = pf.getText();

        /* If username and password match Regex patterns, proceed with DB retrieval and encryption.
         Otherwise, Verify displays a detailed alert
         */
        if (verify.areValidCreds(txtUserName, pf)) {
            try {
                String isUser = "SELECT * FROM user WHERE username = '" + user + "'";

                // Query User table for user, pw, and salt where user = user
                List<Map<String, Object>> results = db.retrieveRecords(isUser);

                if (results != null && results.isEmpty()) // Query returned 0 results
                {
                    lblMessage.setText("No account exists for this user.");
                    lblMessage.setTextFill(Color.RED);
                } else if (results != null) // Record returned
                {
                    Map<String, Object> aRow = results.get(0);
                    String salt = (String) aRow.get("salt");
                    String pw_hash = (String) aRow.get("password");

                    //Determine whether the hash of the provided password matches the stored hash
                    if (encrypt.isExpectedPassword(pw, pw_hash, salt)) {
                        // If the passwords match, gather the other record values for User creation
                        Integer user_id = (Integer) aRow.get("user_id");
                        String email = (String) aRow.get("email");
                        String first = (String) aRow.get("first_name");
                        String last = (String) aRow.get("last_name");
                        Timestamp created = (Timestamp) aRow.get("created");
                        Timestamp updated = (Timestamp) aRow.get("last_update");

                        // Store the db values in a User object's attributes
                        currUser = new User(user_id, email, user, pw_hash, salt, first, last, created, updated);

                        bool = true;                // signal to calling function that stage should be closed
                        dashboard.mainScreen();     // display the user's dashboard
                    } else // User's password entry did not match the value in database
                    {
                        lblMessage.setText("Incorrect user or password");
                        lblMessage.setTextFill(Color.RED);
                    }
                }
            } /* Catch Exceptions thrown by Encryptor and DBConnector classes and
             display an Alert describing the Exception*/ catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                verify.createAlert(Alert.AlertType.ERROR, "Failed login",
                        "An error occurred while processing your credentials. The system "
                        + "will now exit. Error message: " + ex.getMessage());
                System.exit(1);
            }
        } /* Display a red alert message in the Login screen if one or more entries
         fail the regex tests in Verify
         */ else {
            lblMessage.setText("Fix entries above");
            lblMessage.setTextFill(Color.RED);
        }

        return bool;
    } // End Charlotte's code

} //End Class Login
