package net.ricebean.railroad.controller.service;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import net.ricebean.railroad.controller.model.DccCommand;
import net.ricebean.railroad.controller.model.DccStatus;
import net.ricebean.railroad.controller.service.listener.DccMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for handling all the DCC communication.
 */
@Component
public class DccServiceImpl implements DccService, SerialPortDataListener {

    private static final Logger log = LoggerFactory.getLogger(DccServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;

    private SerialPort serialPort;
    private final StringWriter serialInputCache = new StringWriter();
    private Pattern commandFilterPattern = Pattern.compile("(<[^>]+>)");

    /**
     * Default constructor.
     */
    public DccServiceImpl() {
    }

    @PostConstruct
    public void init() {
        this.serialPort = initSerialPort();
    }

    @Override
    public void executeCommand(DccCommand dccCommand) {

        // extract command
        byte[] cmd = dccCommand.getCommand().getBytes();

        // notify listeners
        Collection<DccMessageListener> dccMessageListeners = applicationContext.getBeansOfType(DccMessageListener.class).values();

        for(DccMessageListener dccMessageListener: dccMessageListeners) {
            dccMessageListener.notifyOutboundDccMessage(new String(cmd));
        }

        // send
        this.serialPort.writeBytes(cmd, cmd.length);
    }

    @Override
    public DccStatus getStatus() {
        return null;
    }

    private SerialPort initSerialPort() {
        SerialPort serialPort;

        SerialPort[] serialPorts = SerialPort.getCommPorts();

        if(serialPorts.length == 1) {
            serialPort = serialPorts[0];
            serialPort.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
            serialPort.addDataListener(this);
            serialPort.openPort();
        } else {
            serialPort = null;
        }

        return serialPort;
    }

    /**
     * Method of SerialPortDataListener interface.
     */
    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    /**
     * Method of SerialPortDataListener interface. Method will be called if a new message part is available.
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
            SerialPort serialPort = event.getSerialPort();

            byte[] data = new byte[serialPort.bytesAvailable()];
            event.getSerialPort().readBytes(data, data.length);

            processIncomingMessagePart(new String(data));
        }
    }

    /**
     * Process an incoming message part.
     * @param messagePart The incoming message part.
     */
    private synchronized void processIncomingMessagePart(String messagePart) {

        // append message part
        serialInputCache.append(messagePart);

        // analyze input
        List<String> commands = new ArrayList<>(10);
        Matcher matcher = commandFilterPattern.matcher(serialInputCache.toString());

        while (matcher.find()) {
            commands.add(matcher.group());
        }

        // notify listeners and cleanup
        Collection<DccMessageListener> listeners = applicationContext.getBeansOfType(DccMessageListener.class).values();

        for(String command: commands) {
            serialInputCache.getBuffer().delete(0, command.length());

            for(DccMessageListener listener: listeners) {
                listener.notifyInboundDccMessage(command);
            }
        }
    }

}
