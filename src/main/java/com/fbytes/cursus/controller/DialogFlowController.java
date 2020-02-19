package com.fbytes.cursus.controller;

import com.google.actions.api.DialogflowApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("gdialog")
public class DialogFlowController extends DialogflowApp {

    private static final Logger logger = LoggerFactory.getLogger(DialogFlowController.class);

    @Autowired
    DialogflowApp dialogflowApp;

    @RequestMapping(value = "/dflow", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String gAction(@RequestBody String body, @RequestHeader Map<String, String> headers) throws IOException {
        logger.info("Request body: " + body);
        try {
            return dialogflowApp.handleRequest(body, headers).get();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return handleControllerError(e);
        }
    }

    public String handleControllerError(Exception e) {
        logger.error("Error in App.handleRequest ", e);
        return "Error handling the intent - " + e.getMessage();
    }
}
