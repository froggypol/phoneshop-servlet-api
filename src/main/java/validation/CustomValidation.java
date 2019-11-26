package validation;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.utils.UtilParse;
import service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

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
        List<String> quantity = Arrays.asList(request.getParameterValues("quantity"));
        List<String> productsId = Arrays.asList(request.getParameterValues("productId"));
        quantity.stream().forEach(quantityItem -> {
            int id = quantity.indexOf(quantityItem);
            Product product = productService.getProductById(productsId.get(id));
            if (!quantityItem.matches("[0-9]+")) {
                errorMap.customException(product, "Incorrect Input");
                return;
            }
            try {
                UtilParse.parseIntByLocale(request.getLocale(), quantityItem);
            } catch (ParseException e) {
                errorMap.customException(product, "Incorrect Input");
            }
        });
    }
}
