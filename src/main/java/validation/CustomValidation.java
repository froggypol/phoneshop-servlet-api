package validation;

import com.es.phoneshop.utils.UtilParse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

public class CustomValidation {

    private static CustomValidation customValidation;

    private CustomValidation() { }

    public static CustomValidation getInstance() {
        if (customValidation == null) {
            customValidation = new CustomValidation();
        }
        return customValidation;
    }

    public void validQuantity(ErrorMap errorMap, HttpServletRequest request, HttpServletResponse response) {
        String quantity = request.getParameter("quantity");
        if(!quantity.matches("[0-9]+")) {
            errorMap.customException("quantity", "Incorrect Input");
            return;
        }
        try {
            UtilParse.parseIntByLocale(request.getLocale(), quantity);
        }
        catch (ParseException e) {
            errorMap.customException("quantity", "Incorrect Input");
        }
    }
}
