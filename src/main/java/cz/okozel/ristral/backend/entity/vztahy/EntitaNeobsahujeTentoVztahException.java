package cz.okozel.ristral.backend.entity.vztahy;

public class EntitaNeobsahujeTentoVztahException extends RuntimeException {

    public EntitaNeobsahujeTentoVztahException() {
    }

    public EntitaNeobsahujeTentoVztahException(String message) {
        super(message);
    }

    public EntitaNeobsahujeTentoVztahException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntitaNeobsahujeTentoVztahException(Throwable cause) {
        super(cause);
    }

    protected EntitaNeobsahujeTentoVztahException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
