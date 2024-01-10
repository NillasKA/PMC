package utility;

public class PMCException extends Exception{

    public PMCException()
    {}

    public PMCException(String message)
    {
        super(message);
    }

    public PMCException(String message, Exception cause)
    {
        super(message,cause);
    }

    public PMCException(Throwable cause)
    {
        super(cause);
    }

    public PMCException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace)
    {
        super(message, cause, enableSupression, writableStackTrace);
    }
}
