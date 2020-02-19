package com.fbytes.cursus.controller;

import com.fbytes.cursus.services.router.IRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("tst")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(GoogleActionsController.class);
    @Autowired
    private IRouter router;

    @RequestMapping(value = "/loopback", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String sendRabbitMessage(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        logger.debug("Request body: " + body);
        return body;
    }

    @RequestMapping(value = "/routeconfig", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String routeconfig(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        return router.showConfig();
    }
}
