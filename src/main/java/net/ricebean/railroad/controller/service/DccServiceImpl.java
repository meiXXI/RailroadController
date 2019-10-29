package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccCommand;
import net.ricebean.railroad.controller.model.DccLogItem;
import net.ricebean.railroad.controller.model.DccStatus;
import net.ricebean.railroad.controller.model.types.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DccServiceImpl implements DccService {

    private static final Logger log = LoggerFactory.getLogger(DccServiceImpl.class);

    private static int LOG_CAPACTIY_MAX = 1000;
    private static int LOG_CAPACTIY = 750;

    private final static List<DccLogItem> LOG_ITEMS = Collections.synchronizedList(new ArrayList<>(LOG_CAPACTIY_MAX + 100));

    @Override
    public String executeCommand(DccCommand dccCommand) throws IOException {
        log.info("Process Command: " + dccCommand.getCommand());

        Path path = Paths.get("/dev/ttyACM0");
        // Files.write(path, dccCommand.getCommand().getBytes());
        append(dccCommand);

        return null;
    }

    @Override
    public DccStatus getStatus() {
        DccStatus dccStatus;

        Path path = Paths.get("/dev/ttyACM0");

        if(Files.exists(path)) {
            dccStatus = new DccStatus(Status.Online);
        } else {
            dccStatus = new DccStatus(Status.Offline);
        }
        return dccStatus;
    }

    /**
     * Returns list logs since timestamp.
     * @return List of LogItems.
     */
    @Override
    public List<DccLogItem> getLogItems(long timestamp) {
        return getLogs(timestamp);
    }

    /**
     * Cache log event in the static list.
     * @param dccCommand The command to be cached.
     */
    private void append(DccCommand dccCommand) {

        // create log item object
        DccLogItem logItem = new DccLogItem(dccCommand);

        // cache
        synchronized (LOG_ITEMS) {
            LOG_ITEMS.add(logItem);
        }

        // if list is too long, remove the first items
        synchronized (LOG_ITEMS) {
            if (LOG_ITEMS.size() > LOG_CAPACTIY_MAX) {
                for (int i = 0; i < LOG_CAPACTIY_MAX - LOG_CAPACTIY; i++) {
                    LOG_ITEMS.remove(0);
                }
            }
        }
    }

    /**
     * Returns logs since the defined
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
}
