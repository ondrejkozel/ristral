package cz.okozel.ristral.backend.entity.lines;

public class EmptyRouteException extends Exception {

    public EmptyRouteException() {
    }

    public EmptyRouteException(String message) {
        super(message);
    }

    public EmptyRouteException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRouteException(Throwable cause) {
        super(cause);
    }

    public EmptyRouteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
