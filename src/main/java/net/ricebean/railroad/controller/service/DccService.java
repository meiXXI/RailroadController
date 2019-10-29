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
     * Returns a list of log items newer than the timestamp..
     * @param timestamp The timestamp.
     * @return List of log items.
     */
    List<DccLogItem> getLogItems(long timestamp);

    /**
     * Returns all log items in cache.
     * @return List of all log items in cache.
     */
    default List<DccLogItem> getLogItems() { return getLogItems(0); }

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
