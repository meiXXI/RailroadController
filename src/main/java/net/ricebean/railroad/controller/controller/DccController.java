package net.ricebean.railroad.controller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DCC commands.
 */
@RestController
@RequestMapping("/dcc")
public class DccController {

    private static final Logger log = LoggerFactory.getLogger(DccController.class);

    @PostMapping
    public String execute() {
        return "";
    }
}
