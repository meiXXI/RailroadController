package net.ricebean.railroad.controller.model;

/**
 * Dcc Log model object.
 */
public class DccLogItem {

    private final String message;
    private final long timestamp;

    /**
     * Custom constructor.
     * @param dccCommand The command.
     */
    public DccLogItem(DccCommand dccCommand) {
        this.timestamp = System.currentTimeMillis();
        this.message = dccCommand.getCommand();
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
