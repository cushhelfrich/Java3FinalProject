
package java3finalproject;

//Imports
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Contributors: Charlotte Hirschberger
 * @Assignment Name: Java III Final Project
 * @Date: Nov 28, 2016
 * @Description: Packages a field, a regex pattern, an alert label, and identifying
 * text together for ease of validation.
 */

//Start class FieldSet
public class FieldSet {
    private TextInputControl field;     // PasswordField or TextField
    private final String regex;
    private final Label alert;          // red message that appears under corresponding field
    private final String id;            // name of field
    private final String CSS_CLASS = "login-alert";
    
    /**
     * Construct a FieldSet with provided parameters and empty label.
     * Assign the alert to a CSS class.
     * @param field
     * @param regex
     * @param id 
     */
    public FieldSet(TextInputControl field, String regex, String id)
    {
        this.field = field;
        this.regex = regex;
        this.id = id;
        this.alert = new Label("");
        alert.getStyleClass().add(CSS_CLASS);
    }
    
    // Get the label that holds a message for this FieldSet's field
    public Label getAlert()
    {
        return alert;
    }
    
    // Set the message in this object's label
    public void setAlert(String msg)
    {
        alert.setText(msg);
    }
    
    public TextInputControl getField()
    {
        return field;
    }
    
    public String getID()
    {
        return id;
    }
    
    public String getRegex()
    {
        return regex;
    }
}
