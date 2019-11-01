package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccLogItem;
import net.ricebean.railroad.controller.service.listener.DccMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DccLogServiceImpl implements DccLogService, DccMessageListener {

    private static final Logger log = LoggerFactory.getLogger(DccLogServiceImpl.class);

    private static int LOG_CAPACTIY_MAX = 1000;
    private static int LOG_CAPACTIY = 750;

    private final static List<DccLogItem> LOG_ITEMS = Collections.synchronizedList(new ArrayList<>(LOG_CAPACTIY_MAX + 100));

    /**
     * Default constructor.
     */
    public DccLogServiceImpl() {
    }

    /**
     * Returns list logs since timestamp.
     *
     * @return List of LogItems.
     */
    @Override
    public List<DccLogItem> getLogItems(long timestamp) {
        return getLogs(timestamp);
    }

    /**
     * Returns logs since the defined
     *
     * @param timestamp The time in nano since when items are requested.
     */
    private List<DccLogItem> getLogs(long timestamp) {
        List<DccLogItem> result;

        // create and filter result
        synchronized (LOG_ITEMS) {
            result = new ArrayList<>(LOG_ITEMS.size());

            for (int i = LOG_ITEMS.size() - 1; i > -1; i--) {
                if (LOG_ITEMS.get(i).getTimestamp() > timestamp) {
                    result.add(LOG_ITEMS.get(i));
                }
            }
        }

        // return result
        return result;
    }

    /**
     * Process incoming message.
     * @param message The inbound message.
     */
    @Override
    public void notifyInboundDccMessage(String message) {
        DccLogItem dccLogItem = new DccLogItem(message, DccLogItem.Direction.Inbound);
        append(dccLogItem);
    }

    @Override
    public void notifyOutboundDccMessage(String message) {
        DccLogItem dccLogItem = new DccLogItem(message, DccLogItem.Direction.Outbound);
        append(dccLogItem);
    }

    /**
     * Cache log event in the static list.
     * @param logItem The dcc log item to be appended.
     */
    private void append(DccLogItem logItem) {

        // log
        log.info(logItem.toString());

        // cache
        synchronized (LOG_ITEMS) {
            LOG_ITEMS.add(logItem);
        }

        // if list is too long, remove the first items
        synchronized (LOG_ITEMS) {
            if (LOG_ITEMS.size() > LOG_CAPACTIY_MAX) {
                if (LOG_CAPACTIY_MAX - LOG_CAPACTIY > 0) {
                    LOG_ITEMS.subList(0, LOG_CAPACTIY_MAX - LOG_CAPACTIY).clear();
                }
            }
        }
    }
}
