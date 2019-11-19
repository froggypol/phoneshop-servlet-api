package com.es.phoneshop.parsing;

import validation.CustomValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ParseQuantityService {

    CustomValidation customValidation;

    public ParseQuantityService() {
        customValidation = CustomValidation.getInstance();
    }

    public Integer parsingQuantity(Locale locale, HttpServletRequest request, HttpServletResponse response) {
        String quantity = request.getParameter("quantity");
        int integerQuantity = -1;
        try {
            integerQuantity = NumberFormat.getInstance(locale).parse(quantity).intValue();
        }
        catch (ParseException e) {
            new ParseException(quantity, e.getErrorOffset());
            return null;
        }
        return integerQuantity;
    }
}
