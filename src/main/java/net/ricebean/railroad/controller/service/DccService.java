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
     * Return a list of log items newer than the timestamp..
     * @param timestamp The timestamp.
     * @return List of log items.
     */
    List<DccLogItem> getLogItems(long timestamp);

    /**
     * Returns all log times in cache.
     * @return List of all log items in cache.
     */
    default List<DccLogItem> getLogItems() { return getLogItems(0); }

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
