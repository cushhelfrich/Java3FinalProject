package java3finalproject;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Contributors: Cush Helfrich, Wayne Riley, Charlotte Hirschberger
 * @Assignment Name: java3finalproject
 * @Date: Oct 26, 2016
 * @Subclass Verify Description: Subclass used for verification of data entry
 */

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
    
    /***********Start Charlotte's Code******************/
    
    /**
     * Extracts entries from username and password fields and sends them to
     * isValidUName and isValidPWEntry methods for regex tests.
     * Concatenates an alert message that displays the requirements for the entry or entries
     * determined to be invalid.
     * @param txtUserName
     * @param pf
     * @return 
     */
    public boolean areValidCreds(TextField txtUserName, PasswordField pf)
    {
        boolean areValid = true;
        String user = txtUserName.getText();
        String pw = pf.getText();
        String invalidMsg = "";
        pf.requestFocus();
        
        if(!isValidUName(user)) // String in txtUserName didn't match regex pattern
        {
            areValid = false;
            invalidMsg += "The username must be 6-64 characters in length, must "
                    + "start with a number or letter, and must contain only numbers, "
                    + "letters, dashes, underscores, and periods. ";
            txtUserName.setText("");
            txtUserName.requestFocus();
        }
        
        /* If user string was invalid, text will be added to invalidMsg. Otherwise,
        invalidMsg will only display the text inside this block, i.e. "The password..."
        */
        if(!isValidPwEntry(pw)) // String in pf didn't match regex pattern
        {
            areValid = false;
            invalidMsg += "The password must be 8-255 characters in length and "
                    + "contain at least 1 digit, 1 symbol, and 1 uppercase letter.";
            pf.setText("");
        }
        
        /* If either entry was invalid, show an alert*/
        if(areValid == false)
        {
            createAlert(Alert.AlertType.ERROR, "Invalid entries", invalidMsg);
        }
        
        return areValid;
    }
    
    /**
     * Returns false if value in user argument doesn't match regex pattern
     * Password must be 6-64 characters, start and end with an alphanumeric char,
     * contain only letters, numbers, and . - _ with hyphens, periods, and underscores
     * occurring one at a time
     * @param user value from username TextField
     * @return 
     */
    private boolean isValidUName(String user)
    {
        boolean isValid = true;
        
        if(!user.matches("(?=[A-Za-z0-9-_.]{6,64}$)"            //String has between 6-64 characters
                + "^[A-Za-z0-9]([-_.]{0,1}[A-Za-z0-9]+)+$"))    //String starts and ends with alphanumeric data
        {
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Returns false if value in pw argument doesn't match regex pattern
     * Password must be 8-255 characters in length, contain at least 1 uppercase letter,
     * 1 digit, and 1 non-word character
     * @param pw value from PasswordField
     * @return 
     */
    private boolean isValidPwEntry(String pw)
    {
        boolean isValid = true;
        
        if(!pw.matches("^.*(?=.{8,255})"                // Password must be 8-255 characters
                + "(?=.*\\d)(?=.*[A-Z])(?=.*\\W).*$"))  // Password must contain 1 digit, 1 capital, 1 symbol
        {
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Accepts an AlertType and multiple strings, in order to efficiently create
     * an alert dialog
     * @param type      AlertType
     * @param header
     * @param content 
     */
    public void createAlert(Alert.AlertType type, String header, String content)
    {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
} //End Subclass Verify
