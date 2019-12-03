package validation;

import javafx.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator implements Validator<String> {

    private final static String DATE_FORMAT = "dd-MM-yyyy";

    private static DateValidator dateValidation;

    private DateValidator() {
    }

    public static DateValidator getInstance() {
        if (dateValidation == null) {
            dateValidation = new DateValidator();
        }
        return dateValidation;
    }

    @Override
    public void validate(Pair<String, String> validDate, ErrorMap errorMap) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(validDate.getValue());
        } catch (ParseException e) {
            errorMap.customException("date", "Wrong date");
        }
    }
}
