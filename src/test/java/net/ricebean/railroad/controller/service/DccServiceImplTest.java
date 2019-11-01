package net.ricebean.railroad.controller.service;

import net.ricebean.railroad.controller.service.listener.DccMessageListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.StringWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class DccServiceImplTest {

    @Mock
    private ApplicationContext applicationContextMock;

    @InjectMocks
    private DccServiceImpl dccService = new DccServiceImpl();

    @Test
    public void processInput_1() throws Exception {

        // arrange
        List<String> commands = new ArrayList<>(10);

        DccMessageListener dccCommandListener = new DccMessageListener() {
            @Override
            public void notifyInboundDccMessage(String message) {
                commands.add(message);
            }

            @Override
            public void notifyOutboundDccMessage(String message) {

            }
        };

        Map<String, DccMessageListener> map = new HashMap<>();
        map.put("key", dccCommandListener);

        Mockito.doReturn(map).when(applicationContextMock).getBeansOfType(DccMessageListener.class);

        // act
        ReflectionTestUtils.invokeMethod(dccService, "processIncomingMessagePart", "<da><st><a><as df><adsf");

        // assert
        assertEquals(4, commands.size(), "Number of extracted commands is wrong.");
        assertEquals("<da>", commands.get(0), "Command is wrong.");
        assertEquals("<st>", commands.get(1), "Command is wrong.");
        assertEquals("<a>", commands.get(2), "Command is wrong.");
        assertEquals("<as df>", commands.get(3), "Command is wrong.");

        StringWriter sw = (StringWriter) ReflectionTestUtils.getField(dccService, "serialInputCache");
        assertEquals("<adsf", sw.toString(), "Result is wrong.");
    }

    @Test
    public void processInput_2() throws Exception {

        // arrange
        List<String> commands = new ArrayList<>(10);

        DccMessageListener dccCommandListener = new DccMessageListener() {
            @Override
            public void notifyInboundDccMessage(String message) {
                commands.add(message);
            }

            @Override
            public void notifyOutboundDccMessage(String message) {

            }
        };
        Map<String, DccMessageListener> map = new HashMap<>();
        map.put("key", dccCommandListener);

        Mockito.doReturn(map).when(applicationContextMock).getBeansOfType(DccMessageListener.class);

        // act
        ReflectionTestUtils.invokeMethod(dccService, "processIncomingMessagePart", "<1");
        ReflectionTestUtils.invokeMethod(dccService, "processIncomingMessagePart", "2345><00><a");
        ReflectionTestUtils.invokeMethod(dccService, "processIncomingMessagePart", "bcd><xyz><zzz");

        // assert
        assertEquals(4, commands.size(), "Number of extracted commands is wrong.");
        assertEquals("<12345>", commands.get(0), "Command is wrong.");
        assertEquals("<00>", commands.get(1), "Command is wrong.");
        assertEquals("<abcd>", commands.get(2), "Command is wrong.");
        assertEquals("<xyz>", commands.get(3), "Command is wrong.");

        StringWriter sw = (StringWriter) ReflectionTestUtils.getField(dccService, "serialInputCache");
        assertEquals("<zzz", sw.toString(), "Result is wrong.");
    }


}