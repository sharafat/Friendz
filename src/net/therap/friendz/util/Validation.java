package net.therap.friendz.util;

import android.widget.EditText;

/**
 * @author The TherapJavaFest Team
 */
public class Validation {

    public static boolean validateRequired(EditText editText, String errorMsg) {
        if ("".equals(editText.getText().toString().trim())) {
            editText.setError(errorMsg);
            return false;
        } else {
            editText.setError(null);
            return true;
        }
    }

}
