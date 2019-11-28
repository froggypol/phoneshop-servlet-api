package validation;

import javafx.util.Pair;

public class QuantityValidator implements Validator<String> {

    private static QuantityValidator customValidation;

    private QuantityValidator() {
    }

    public static QuantityValidator getInstance() {
        if (customValidation == null) {
            customValidation = new QuantityValidator();
        }
        return customValidation;
    }

    @Override
    public void validate(Pair<String, String> validQuantity, ErrorMap errorMap) {
        if (!validQuantity.getKey().matches("[0-9]+")) {
            errorMap.customException("quantity&" + validQuantity.getValue(), "Incorrect Input");
        }
    }
}
