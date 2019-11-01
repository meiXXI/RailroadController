package net.ricebean.railroad.controller.controller;

import net.ricebean.railroad.controller.model.DccCommand;
import net.ricebean.railroad.controller.model.DccLogItem;
import net.ricebean.railroad.controller.model.DccStatus;
import net.ricebean.railroad.controller.service.DccLogService;
import net.ricebean.railroad.controller.service.DccService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST endpoints for DCC commands.
 */
@RestController
@RequestMapping("/dcc")
public class DccController {

    private static final Logger log = LoggerFactory.getLogger(DccController.class);


    @Autowired
    private DccService dccService;

    @Autowired
    private DccLogService dccLogService;

    /**
     * Returns the current status of the DCC Controller.
     * @return
     */
    @GetMapping(value="/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public DccStatus getStatus() {
        return dccService.getStatus();
    }

    /**
     * Execute a DCC Command on the DCC Controller.
     * @param dccCommand The DCC Command to be executed.
     */
    @PostMapping
    public void executeCommand(@RequestBody DccCommand dccCommand) throws Exception {
        dccService.executeCommand(dccCommand);
    }

    /**
     * Returns new dcc logs emerged since the last timestamp.
     * @param timestamp The timestamp.
     * @return List of newly emerged log items.
     */
    @GetMapping(value = "/logs/{timestamp}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DccLogItem> getLogs(@PathVariable long timestamp) {
        return dccLogService.getLogItems(timestamp);
    }

    /**
     * Return all log items in the cache.
     * @return List of all log items in cache.
     */
    @GetMapping(value = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DccLogItem> getLogs() {
        return dccLogService.getLogItems();
    }

}
