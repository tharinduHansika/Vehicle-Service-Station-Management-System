package util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(LinkedHashMap<JFXTextField, Pattern> map, JFXButton btn) {
        for (JFXTextField key : map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                //if the input is not matching
                addError(key,btn);
                return key;
            }
            removeError(key,btn);
        }
        return true;
    }

    private static void removeError(JFXTextField txtField,JFXButton btn) {
        txtField.setStyle("-jfx-focus-color: green");
        btn.setDisable(false);
    }

    private static void addError(JFXTextField txtField,JFXButton btn) {
        if (txtField.getText().length() > 0) {
            txtField.setStyle("-jfx-focus-color: red");
        }
        btn.setDisable(true);
    }

}
