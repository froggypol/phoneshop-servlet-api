package validation;

import javafx.util.Pair;

public class ReviewStringValidator implements Validator<String> {

    private static ReviewStringValidator reviewStringValidator;

    private ReviewStringValidator() {
    }

    public static ReviewStringValidator getInstance() {
        if (reviewStringValidator == null) {
            reviewStringValidator= new ReviewStringValidator();
        }
        return reviewStringValidator;
    }


    @Override
    public void validate(Pair<String, String> validQuantity, ErrorMap errorMap) {
        if(validQuantity.getKey().matches("[0-9]+")) {
            errorMap.customException(validQuantity.getValue(), "Incorrect Input For Customer");
        }
    }
}
