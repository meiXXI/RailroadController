package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccCommand;

/**
 * This Service Interfaces abstracts the DCC communication logic.
 */
public interface DccService {

    /**
     * Execute a given DCC Command.
     * @param dccCommand The DCC Command.
     * @return String value
     */
    String executeCommand(DccCommand dccCommand);
}
