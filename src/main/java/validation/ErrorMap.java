package validation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMap {

    private Map<String, List<String>> mapForErrors;

    public ErrorMap() {
        mapForErrors = new HashMap<>();
    }

    public void customException(String field, String message) {
        if (mapForErrors.get(field) == null) {
            mapForErrors.put(field, new ArrayList<>());
        }
        mapForErrors.get(field).add(message);
    }

    public Integer getExceptionListSize(String field) {
        if (mapForErrors.containsKey(field)){
            return mapForErrors.get(field).size();
        }
        return null;
    }

    public List<String> getExceptionList(String fieldWithException) {
        return mapForErrors.get(fieldWithException);
    }
}
