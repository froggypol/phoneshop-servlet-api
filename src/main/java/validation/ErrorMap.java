package validation;

import com.es.phoneshop.model.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMap  {

    private Map<Product, List<String>> mapForErrors;

    public ErrorMap() {
        mapForErrors = new HashMap<>();
    }

    public void customException(Product product, String message) {
        if (mapForErrors.get(product) == null) {
            mapForErrors.put(product, new ArrayList<>());
        }
        mapForErrors.get(product).add(message);
    }

    public Map<Product, List<String>> getExceptionList() {
        return mapForErrors;
    }
}
