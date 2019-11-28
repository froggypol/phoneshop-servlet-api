package validation;

public class CartServiceQuantityValidator implements Validator<String> {

    private static CartServiceQuantityValidator cartServiceQuantityValidator;

    private CartServiceQuantityValidator() { }

    public static CartServiceQuantityValidator getInstance() {
        if (cartServiceQuantityValidator == null) {
            cartServiceQuantityValidator = new CartServiceQuantityValidator();
        }
        return cartServiceQuantityValidator;
    }

    @Override
    public void validate(String quantity, String id, ErrorMap errorMap) {
        if(!quantity.matches("[0-9]+")) {
            errorMap.customException("quantity&" + id, "Incorrect Input");
        }
    }
}
