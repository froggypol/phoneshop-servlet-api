package validation;

import javafx.util.Pair;

public class NameSurnameValidator implements Validator<String> {

    private static NameSurnameValidator nameSurnameValidation;

    private NameSurnameValidator() {
    }

    public static NameSurnameValidator getInstance() {
        if (nameSurnameValidation == null) {
            nameSurnameValidation = new NameSurnameValidator();
        }
        return nameSurnameValidation;
    }

    @Override
    public void validate(Pair<String, String> validStr, ErrorMap errorMap) {
        if (validStr.getValue().matches("[0-9]+")) {
            errorMap.customException("name", "Wrong personal data");
        }
    }
}
