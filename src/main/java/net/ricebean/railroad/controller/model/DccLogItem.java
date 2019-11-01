package net.ricebean.railroad.controller.model;

/**
 * Dcc Log Item model object.
 */
public class DccLogItem {

    public enum Direction {
        Inbound,
        Outbound
    }

    private final long timestamp;
    private final String message;
    private final Direction direction;


    public DccLogItem(String message, Direction direction) {
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.direction = direction;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Message: (" + direction + ") " + message;
    }
}
