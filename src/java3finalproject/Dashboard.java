package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Contributors: Wayne Riley, Charlotte Hirschberger
 * @Assignment Name: java3finalproject
 * @Date: Oct 16, 2016
 * @Subclass Dashboard Description: Dashboard called from Login screen once
 * proper UID/PID is accepted
 */
//Imports
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//************Start Wayne Code*********************
//Begin Subclass Dashboard
public class Dashboard {

    //declarations
    Stage dbScene = new Stage();
    Button viewAccount = new Button("View Account");
    Button btnAdd;
    Button btnModify;
    Button btnDelete;
    Button btnClear;
    Button btnExit;
    static TextArea accountView;
    private static List<Map<String, Object>> account;
    private static List<Account> accountArr = new ArrayList<>();
    private static ComboBox sortCombo;
    private static AEScrypt aes;
    static TextField accountName = new TextField();
    static TextField userName = new TextField();
    static TextField passWord = new TextField();

    //instantiate subclass
    Add add = new Add();
    Delete delete = new Delete();
    Modify modify = new Modify();
    Verify verify = new Verify();

    /**
     * Main dashboard - User can view accounts or add, modify and delete
     * accounts
     *
     */
    public void mainScreen() {
        aes = new AEScrypt();

        //Borderpane to hold Gridpane, HBOX (holds buttons)
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));

        //Gridpane to hold Account, UserName and Password textfield as well as View Button
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        ImageView copyImg = new ImageView(new Image("images/clipboard.png"));
        // Assign the clipboard image as each button background
        Button copyUN = new Button();
        Button copyPW = new Button();
        copyUN.setGraphic(copyImg);
        copyPW.setGraphic(copyImg);

        gridPane.add(accountName, 0, 0, 2, 1);
        accountName.setPromptText("Enter Account Name");
        gridPane.add(viewAccount, 0, 1, 2, 1);
        viewAccount.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(userName, 0, 2);
        GridPane.setConstraints(userName, 0, 2, 1, 1);
        userName.setPromptText("Enter/View Username");
        gridPane.add(passWord, 0, 3);
        GridPane.setConstraints(passWord, 0, 3, 1, 1);
        passWord.setPromptText("Enter/View Password");
        gridPane.add(copyUN, 1, 2);
        gridPane.add(copyPW, 1, 3);
        //gridPane.add(webSite, 0, 4);
        //GridPane.setConstraints(webSite, 0, 4, 1, 1);
        //webSite.setPromptText("Enter/View Website Here");
        
        /* Add panes to appropriate region */
        bp.setRight(gridPane);
        bp.setBottom(gethBox());
        bp.setLeft(getvBox());

        //Add ID's to Nodes for CSS
        bp.setId("bp");
        gridPane.setId("root");
        btnAdd.setId("btn");
        btnModify.setId("btn");
        btnDelete.setId("btn");
        btnClear.setId("btn");
        btnExit.setId("btn");

        //Dashboard stage
        Scene scene = new Scene(bp, 370, 200);
        dbScene.setTitle("Dashboard - " + Login.currUser.getUsername()); //CUSH added username
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        dbScene.setScene(scene);
        dbScene.show();

        try
        {
            getAct();           //calls method to load List<Map<>> with account details from database        
            initAccountSet();
        }
        catch(SQLException ex)
        {
            System.out.println("Error message: " + ex.getMessage());
            verify.createAlert(Alert.AlertType.ERROR, "Error loading accounts", 
                    "There was a problem loading your account details, and the system will now exit. "
                    + "Error message: " + ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * VBox for textarea
     *
     * @return
     */
    private VBox getvBox() {
        /*Set vBox attributes */
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setPadding(new Insets(5, 5, 5, 5));
                
            /****Charlotte's code****/
            // HBox holds Text and ComboBox, side by side
            HBox holdCombo = new HBox();
                holdCombo.setAlignment(Pos.BASELINE_LEFT);
            
                Text sortBy = new Text("Sort by: ");
                sortBy.setId("sortBy");
        
                // Create and populate the ComboBox
                sortCombo = new ComboBox();
                sortCombo.getItems().addAll("Name", "Newest", "Last Updated");
                sortCombo.setValue("Name");
        
            // Determine when a ComboBox cell has been clicked and change the sort order in response
            sortCombo.valueProperty().addListener(new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue ov, String s1, String s2)
                {
                    switch (s2) {
                        case "Name":
                            Collections.sort(accountArr);
                            break;
                        case "Newest":
                            Collections.sort(accountArr, Account.CreatedComp);
                            break;
                        // Last Updated
                        default:
                            Collections.sort(accountArr, Account.UpdatedComp);
                            break;
                    }
                updateTextArea();
                }
            });
        
            holdCombo.getChildren().addAll(sortBy, sortCombo);
            /*****End Charlotte's code*****/
        accountView = new TextArea();
        accountView.setPrefColumnCount(13);
        accountView.setPrefRowCount(8);
        accountView.setEditable(false);
        accountView.setWrapText(true);

        vBox.getChildren().addAll(holdCombo, accountView);

        return vBox;
    }

    ;

    /**
     * get hBox and buttons (Add, modify, delete, clear and Exit)
     *
     * @return
     */
    private HBox gethBox() {
        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        btnAdd = new Button("Add");
        btnModify = new Button("Modify");
        btnDelete = new Button("Delete");
        btnClear = new Button("Clear");
        btnExit = new Button("Exit");

        //Launches add confirmation scene
        btnAdd.setOnAction((ActionEvent e) -> {

            boolean blank = accountName.getText().matches("") || userName.getText().matches("")
                    || passWord.getText().matches("");
            if (isDuplicate(accountName.getText())) {
                verify.duplicate();
            } else if (blank == true) {
                verify.empty();
            } else {
                add.insert(accountName.getText(), userName.getText(), passWord.getText());
            }
        }
        );//end Add event handler

        viewAccount.setOnAction(
                (ActionEvent e) -> {
                    // if field is empty, call Verify method
                    if (accountName.getText().matches("")) {
                        verify.deleteEmpty();
                    } 
                    else {
                        boolean isFound = false; // Account found?
                        int i = 0;
                        
                        // Keep iterating through array as long as account hasn't been found
                        while(isFound == false && i < accountArr.size())
                        {
                            // if match is found
                            if(accountArr.get(i).getName().equalsIgnoreCase(accountName.getText()))
                            {
                                isFound = true;
                                
                                try
                                {
                                    // Retrieve, decrypt, and display the password
                                    passWord.setText(aes.decrypt(accountArr.get(i).getPassword(), accountArr.get(i).getName()));
                                                                    
                                    // Retrieve and display the account username
                                    userName.setText(accountArr.get(i).getUserName());
                                    
                                } 
                                catch (Exception ex) {
                                    Login.verify.createAlert(Alert.AlertType.ERROR, "Error retrieving credentials", 
                                            "There was an error processing your request, and account credentials "
                                             + "could not be displayed. If the problem persists, contact the administrator. "
                                             + "Error message: " + ex.getMessage());
                                }
                            }
                            i++;
                        }
                        if(isFound == false)    // Account not found
                        {
                            Login.verify.createAlert(Alert.AlertType.INFORMATION, "Not found", "The account you searched for was not found.");
                        }
                    }
                }
        );

        //Launches modify confirmation scene
        btnModify.setOnAction(
                (ActionEvent e) -> {
                    if (accountName.getText().matches("")) {
                        verify.deleteEmpty();
                    } else if (userName.getText().matches("") && passWord.getText().matches("")) {
                        verify.modifyEmpty();
                    } else if (!isDuplicate(accountName.getText())) {
                        verify.noAct();
                    } else {
                        try {
                            if (userName.getText().equals(modify.getUser(accountName.getText()))) {
                                verify.sameUserNameEntry();
                            } else if (passWord.getText().equals(modify.getPassword(accountName.getText()))) {
                                verify.samePasswordEntry();
                            } else {
                                modify.change(accountName.getText(), userName.getText(), passWord.getText());
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        );//end modify event handler

        //Launches delete confirmation scene
        btnDelete.setOnAction(
                (ActionEvent e) -> {
                    if (accountName.getText().matches("")) {
                        verify.deleteEmpty();
                    } else if (!isDuplicate(accountName.getText())) {
                        verify.noAct();
                    } else {
                        delete.delete(accountName.getText());
                    }
                }
        );//end delete event handler

        //Clears TextFields
        btnClear.setOnAction(
                (ActionEvent e) -> {
                    clearHandler();
                }
        );//end clear event handler

        //Exit Program
        btnExit.setOnAction(
                (ActionEvent e) -> {
                    System.exit(0);
                }
        );//end exit event handler

        hBox.getChildren()
                .addAll(btnAdd, btnModify, btnDelete, btnClear, btnExit);

        return hBox;
    }

    /**
     * Calls dbConnector for connection and query returns account name which
     * loads to ArrayList
     *
     * @throws SQLException
     */
    public static void getAct() throws SQLException {
        if (account != null && !account.isEmpty()) {
            account.clear(); // clears arrayList
        }
        String rtrvAct = "SELECT * FROM account WHERE user_id = " + Login.currUser.getUserId();

        //calls account to load accounts in arraylist
        account = Login.db.retrieveRecords(rtrvAct);
    }

    /**
     * Read ArrayList and display in text area.
     */
    public static void updateTextArea() {
        accountView.setText("ACCOUNT NAME\n-----------------------------\n");
        for (int i = 0; i < accountArr.size(); i++) {
            accountView.appendText(accountArr.get(i).getName() + "\n");
        }
    }

    /**
     * Method called to clear text fields
     */
    public static void clearHandler() {
        accountName.clear();
        userName.clear();
        passWord.clear();
        //webSite.clear();
    }
/*********************End Wayne's Code******************************/

/*********************Start Charlotte's Code************************/
    /** 
     * Uses contents of List<Map<>> to create a series of Accounts and load them
     * into an ArrayList for sorting
     */
    public static void initAccountSet() {
        accountArr.clear();
        Map<String, Object> row;
        if(!account.isEmpty()) // if user has added some accounts
        {
            for (int i = 0; i < account.size(); i++) 
            {
                // Get the column names and data in the current Map/row
                row = account.get(i);
                
                // Construct an Account with the current Map's data
                Account acct = new Account(
                    (String)row.get("account_name"), 
                    (String)row.get("username"), 
                    (String)row.get("password"),
                    (Timestamp)row.get("created"),
                    (Timestamp)row.get("last_update")
                );
                
                // Add the newly created Account to an ArrayList
                accountArr.add(acct);
            }        
        Collections.sort(accountArr);   // Initially sort by name
        }        
        updateTextArea();               // Fill the TextArea with sorted names
    }
    
    /**
     * Adds a new Account to the end of the ArrayList, sorts by Newest,
     *  and updates the TextArea and ComboBox value
     * @param newAcct   Account that was just added with Add.java
     */
    public static void updateAccountSet(Account newAcct) {
        accountArr.add(newAcct);
        Collections.sort(accountArr, Account.CreatedComp); // Sort by Newest
        updateTextArea();
        sortCombo.setValue("Newest");
    }
    
    /**
     * Uses string provided by user to delete Account from List of Accounts and then
     *  update TextArea
     * @param acctName
     */
    public static void deleteAccount(String acctName)
    {
        boolean contSearch = true;
        int i = 0;
        
        /* Keep searching the List until an Account is removed*/
        while(contSearch == true && i < accountArr.size())
        {
            if(accountArr.get(i).getName().equalsIgnoreCase(acctName))
            {
                accountArr.remove(i);
                contSearch = false;
            }
            i++;
        }
        Collections.sort(accountArr); // Sort by Name
        updateTextArea();
    }


    /**
     * Used to verify that an Account doesn't exist when using Add and
     *  used to verify that an Account DOES exist when using Delete
     * @param acctName
     * @return 
     */
    public static boolean isDuplicate(String acctName)
    {
        boolean b = false;
        int i = 0;
        
        /* Keep searching as long as acctName isn't found in the List*/
        while(b == false && i < accountArr.size())
        {
            if(accountArr.get(i).getName().equalsIgnoreCase(acctName))
            {
                b = true;
            }
            i++;
        }
        return b;
    }
    
    public static AEScrypt getAES()
    {
        return aes;
    }
} //End Subclass Dashboard
//*********************End Wayne Code******************************
