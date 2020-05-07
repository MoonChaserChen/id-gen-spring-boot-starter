package ink.akira.idgen.snowflake;

/**
 * invalid timestamp
 */
public class InvalidSystemClockException extends RuntimeException {
    public InvalidSystemClockException(String message){
        super(message);
    }
}
