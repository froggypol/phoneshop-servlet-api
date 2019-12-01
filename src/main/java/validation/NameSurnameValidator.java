package validation;

import javafx.util.Pair;

public class NameSurnameValidator implements Validator<String> {

    @Override
    public void validate(Pair<String, String> validStr, ErrorMap errorMap) {
        if (validStr.getValue().matches("[0-9]+")) {
            errorMap.customException("name", "Wrong personal data");
        }
    }
}
