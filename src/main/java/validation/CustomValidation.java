package validation;

import service.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CustomValidation {

    private String quantity;

    private static CustomValidation customValidation;

    private SessionService sessionService;

    private CustomValidation(){
        sessionService = SessionService.getInstance();
    }

    public static CustomValidation getInstance() {
        if (customValidation == null) {
            customValidation = new CustomValidation();
        }
        return customValidation;
    }

    public int validQuantity(ErrorMap errorMap, Locale locale, HttpServletRequest request, HttpServletResponse response) {
        quantity = request.getParameter("quantity");
        int qnt = 0;
        try {
            qnt = NumberFormat.getInstance(locale).parse(quantity).intValue();
        }
        catch (ParseException e) {
            errorMap.customException("quantity", "Parse exception");
            sessionService.setErrorMapInSessionAttribute(request, response);
        }
        return qnt;
    }
}
