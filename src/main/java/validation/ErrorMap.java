package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMap {

    private Map<String, List<Exception>> mapForErrors;

    private static ErrorMap errorMap;

    private ErrorMap() {
        mapForErrors = new HashMap<>();
    }

    public static ErrorMap getInstance() {
        if (errorMap == null) {
            errorMap = new ErrorMap();
        }
        return errorMap;
    }

    public void customException(String field, Exception e) {
        if (mapForErrors.get(field) == null) {
            mapForErrors.put(field, new ArrayList<>());
            mapForErrors.get(field).add(e);
        }
    }

    public List<Exception> getExceptionList(String fieldWithException) {
        return mapForErrors.get(fieldWithException);
    }
}
