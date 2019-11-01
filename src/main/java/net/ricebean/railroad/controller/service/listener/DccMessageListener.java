package net.ricebean.railroad.controller.service.listener;

/**
 * Listener Interface for DCC Commands.
 */
public interface DccMessageListener {

    /**
     * Notification about an incoming DCC Message.
     * @param message The inbound DCC Message.
     */
    void notifyInboundDccMessage(String message);

    /**
     * Notification about an outgoing DCC Message.
     * @param message The outbound DCC Message.
     */
    void notifyOutboundDccMessage(String message);
}
