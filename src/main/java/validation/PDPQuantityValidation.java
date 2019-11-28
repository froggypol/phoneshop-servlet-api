package validation;

public class PDPQuantityValidation implements Validator<String>{

    private static PDPQuantityValidation customValidation;

    private PDPQuantityValidation() { }

    public static PDPQuantityValidation getInstance() {
        if (customValidation == null) {
            customValidation = new PDPQuantityValidation();
        }
        return customValidation;
    }

    @Override
    public void validate(String validQuantity, String id, ErrorMap errorMap) {
        if(!validQuantity.matches("[0-9]+")) {
            errorMap.customException("quantity&" + id, "Incorrect Input");
        }
    }
}
