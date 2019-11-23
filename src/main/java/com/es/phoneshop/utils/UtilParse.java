package com.es.phoneshop.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class UtilParse {

    public static Integer parseIntByLocale(Locale locale, String value) throws ParseException {
        return NumberFormat.getInstance(locale).parse(value).intValue();
    }
}
