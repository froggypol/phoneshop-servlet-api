package validation;

import com.es.phoneshop.custom.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomValidation {

    private String quantity;

    private ErrorMap errorMap;

    public CustomValidation() {
        errorMap = ErrorMap.getInstance();
    }

    public boolean validQuantityNumberFormat(HttpServletRequest request, HttpServletResponse response) {
        quantity = request.getParameter("quantity");
        if (quantity.toLowerCase().matches("[a-z]+")) {
            errorMap.customException(quantity, new NumberFormatException());
            return false;
        }
        return true;
    }

    public boolean validQuantityInStock(Product product, HttpServletRequest request, HttpServletResponse response) {
        quantity = request.getParameter("quantity");
        int quantityToAdd = Integer.valueOf(quantity);
        if (product.getOrdered() + quantityToAdd > product.getStock()) {
            errorMap.customException(quantity, new OutOfStockException());
            return false;
        }
        return true;
    }
}
