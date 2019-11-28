package validation;

public interface Validator<T> {
    void validate(T validationObject, String id, ErrorMap errorMap);
}
