package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccLogItem;

import java.util.List;

public interface DccLogService {

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


}
