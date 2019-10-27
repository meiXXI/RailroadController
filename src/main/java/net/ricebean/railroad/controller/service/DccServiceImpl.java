package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.controller.SystemController;
import net.ricebean.railroad.controller.model.DccCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DccServiceImpl implements DccService {

    private static final Logger log = LoggerFactory.getLogger(DccServiceImpl.class);

    @Override
    public String executeCommand(DccCommand dccCommand) {

        log.info("Process Command: " + dccCommand.getCommand());
        return null;
    }
}
