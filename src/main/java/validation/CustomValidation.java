package validation;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.utils.UtilParse;
import service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

public class CustomValidation {

    private static CustomValidation customValidation;

    private ProductService productService;

    private CustomValidation() {
        productService = ProductService.getInstance();
    }

    public static CustomValidation getInstance() {
        if (customValidation == null) {
            customValidation = new CustomValidation();
        }
        return customValidation;
    }

    public void validQuantity(ErrorMap errorMap, HttpServletRequest request, HttpServletResponse response) {
        String[] quantity = request.getParameterValues("quantity");
        String[] productsId = request.getParameterValues("productId");
        for(int i = 0 ; i < quantity.length; i++) {
            Product product = productService.getProductById(productsId[i]);
            if (!quantity[i].matches("[0-9]+")) {
                errorMap.customException(product, "Incorrect Input");
                return;
            }
            try {
                UtilParse.parseIntByLocale(request.getLocale(), quantity[i]);
            } catch (ParseException e) {
                errorMap.customException(product, "Incorrect Input");
            }
        }
    }
}
