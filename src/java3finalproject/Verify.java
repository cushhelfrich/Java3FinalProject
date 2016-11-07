package java3finalproject;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Author Name: Cush Helfrich
 * @Assignment Name: java3finalproject
 * @Date: Oct 26, 2016
 * @Subclass Verify Description: Subclass used for verification of data entry
 */
//Imports
//*******************Start Cush Code****************************
//Begin Subclass Verify
public class Verify {

    /**
     * checkEmail method: used to check syntax of email entered
     *
     * @param n
     * @return flag
     */
    public static boolean checkEmail(String n) {
        boolean flag = true;
        if (!n.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+"
                + "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Email Format Error");
            alert.setContentText("Email should be in this format\n"
                    + "text@text.com\nPlease try again.");
            alert.showAndWait();
            flag = false;

        }
        return flag;

    } //End checkEmail method

    /**
     * isData Method: Checks if field is empty
     *
     * @param t
     * @param l
     * @return boolChecks
     */
    public boolean isData(TextField t, Label l) {

        boolean boolChecks = true;
        if (t.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("No Data Error");
            alert.setContentText("You must enter a value in the " + l.getText()
                    + " field.  Please try again");

            alert.showAndWait();

            boolChecks = false;
        }

        return boolChecks;

    } //End isData method

    //****************End Cush Code**********************************
    
    //****************Start Wayne Code**********************************
    /**
     * Alert if Account, username and password fields are blank
     */
    public void empty() {

        Alert blank = new Alert(Alert.AlertType.WARNING);
        blank.setTitle("Warning");
        blank.setHeaderText("Missing Information");
        blank.setContentText("You must populate Account, Username and Password fields");
        blank.showAndWait();
    }

    /**
     * Alert if account only is blank
     */
    public void deleteEmpty() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Missing Information");
        missing.setContentText("You must populate Account field");
        missing.showAndWait();
    }

    /**
     * Alert if duplicate account exist
     */
    public void duplicate() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Duplicate Account");
        missing.setContentText("You must enter a unique Account Name");
        missing.showAndWait();
    }

    /**
     * Alert if duplicate account exist
     */
    public void noAct() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Account Does Not Exist");
        missing.setContentText("You must enter a valid account name\nAccount Name must be entered exactly as it "
                + "was created");
        missing.showAndWait();
    }

    //****************End Wayne Code**********************************
    
    public boolean areValidCreds(TextField txtUserName, PasswordField pf)
    {
        boolean areValid = true;
        String user = txtUserName.getText();
        String invalidMsg = "";
        
        if(!user.matches("[a-zA-Z0-9]+([.-_]{1}[a-zA-Z0-9]+)*"))
        {
            areValid = false;
            invalidMsg += "The username must be 1-64 characters in length, must "
                    + "start with a number or letter, and must contain only numbers, "
                    + "letters, dashes, underscores, and periods. ";
        }
        
        if(!isValidPwEntry(pf))
        {
            areValid = false;
            invalidMsg += "The password must be 8-255 characters in length and contain";
        }
        
        createAlert(Alert.AlertType.ERROR, "", "Invalid entries", invalidMsg);
        
        return areValid;
    }
    
    private boolean isValidPwEntry(PasswordField pf)
    {
        boolean isValid = true;
        String pw = pf.getText();
        
        // if doesn't match regex, set to false
        
        return isValid;
    }
    
    private void createAlert(Alert.AlertType type, String title, String header, String content)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
} //End Subclass Verify
