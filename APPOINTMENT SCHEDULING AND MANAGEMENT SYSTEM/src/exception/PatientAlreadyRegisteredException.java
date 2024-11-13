package exception;

public class PatientAlreadyRegisteredException extends Exception {
    public PatientAlreadyRegisteredException(String message) {
        super(message);
    }
}
