package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.model.DccCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DccServiceImplTest {

    @InjectMocks
    private DccService dccService = new DccServiceImpl();

    @Test
    public void executeCommand() throws Exception {

        // arrange
        DccCommand dccCommand = new DccCommand();
        dccCommand.setCommand("myCommand");

        // act
        dccService.executeCommand(dccCommand);

        // assert

    }
}