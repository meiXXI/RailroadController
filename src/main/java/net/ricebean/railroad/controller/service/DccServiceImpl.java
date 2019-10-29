package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccCommand;
import net.ricebean.railroad.controller.model.DccStatus;
import net.ricebean.railroad.controller.model.types.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DccServiceImpl implements DccService {

    private static final Logger log = LoggerFactory.getLogger(DccServiceImpl.class);

    @Override
    public String executeCommand(DccCommand dccCommand) throws IOException {
        log.info("Process Command: " + dccCommand.getCommand());

        Path path = Paths.get("/dev/ttyACM0");
        Files.write(path, dccCommand.getCommand().getBytes());

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
}
