package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccCommand;
import net.ricebean.railroad.controller.model.DccStatus;

import java.io.IOException;

/**
 * This Service Interfaces abstracts the DCC communication logic.
 */
public interface DccService {

    /**
     * Execute a given DCC Command.
     * @param dccCommand The DCC Command.
     * @return String value
     */
    String executeCommand(DccCommand dccCommand) throws IOException;


    /**
     * Returns status details of the DCC Interface.
     * @return Status of the DCC interface.
     */
    DccStatus getStatus();
}
