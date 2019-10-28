package net.ricebean.railroad.controller.controller;

import net.ricebean.railroad.controller.model.DccCommand;
import net.ricebean.railroad.controller.service.DccService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * REST endpoints for DCC commands.
 */
@RestController
@RequestMapping("/dcc")
public class DccController {

    private static final Logger log = LoggerFactory.getLogger(DccController.class);

    @Autowired
    private DccService dccService;

    @PostMapping
    public String executeCommand(@RequestBody DccCommand dccCommand) throws Exception {
        log.info("New DCC Command has received: '" + dccCommand.getCommand() + "'");
        return dccService.executeCommand(dccCommand);
    }

    @PostMapping(value = "/on")
    public String switchOn() throws Exception {
        DccCommand dccCommand = new DccCommand();
        dccCommand.setCommand("<1>");
        return dccService.executeCommand(dccCommand);
    }

    @PostMapping(value = "/off")
    public String switchOff() throws Exception {
        DccCommand dccCommand = new DccCommand();
        dccCommand.setCommand("<0>");
        return dccService.executeCommand(dccCommand);
    }
}
