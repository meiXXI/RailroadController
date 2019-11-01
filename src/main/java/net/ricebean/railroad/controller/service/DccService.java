package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccCommand;
import net.ricebean.railroad.controller.model.DccLogItem;
import net.ricebean.railroad.controller.model.DccStatus;

import java.io.IOException;
import java.util.List;

/**
 * This Service Interfaces abstracts the DCC communication logic.
 */
public interface DccService {

    /**
     * Execute a given DCC Command on the DCC Controller.
     * @param dccCommand The DCC Command.
     */
    void executeCommand(DccCommand dccCommand) throws IOException;


    /**
     * Returns status details of the DCC Controller.
     * @return Status of the DCC Controller.
     */
    DccStatus getStatus();
}
