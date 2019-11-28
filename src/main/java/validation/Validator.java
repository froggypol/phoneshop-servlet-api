package validation;

import javafx.util.Pair;

public interface Validator<T> {
    void validate(Pair<T, T> validQuantity, ErrorMap errorMap);
}
