package validation;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMap  {

    private Map<String, List<String>> mapForErrors;

    public ErrorMap() {
        mapForErrors = new HashMap<>();
    }

    public void customException(String fieldAndId, String message) {
        if (mapForErrors.get(fieldAndId) == null) {
            mapForErrors.put(fieldAndId, new ArrayList<>());
        }
        mapForErrors.get(fieldAndId).add(message);
    }

    public Map<String, List<String>> getExceptionList() {
        return mapForErrors;
    }

    public Map<String, List<String>> getErrorMap() {
        return mapForErrors;
    }
}
