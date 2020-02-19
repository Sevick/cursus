package com.fbytes.cursus.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbytes.cursus.model.Producer;
import com.fbytes.cursus.services.router.IRouter;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DialogFlowService extends DialogflowApp {

    private static final Producer producerName = Producer.GOOGLE_DIALOGFLOW;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(DialogFlowService.class);

    @Autowired
    private IRouter router;

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        try {
            router.routeMessage(producerName, objectMapper.writeValueAsString(request.getWebhookRequest()));
        } catch (Exception e) {
            logger.error("Unable to route message: " + e.getMessage());
        }
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Goblins at your command");
        return responseBuilder.build();
    }

    @ForIntent("actions.intent.MAIN")
    public ActionResponse main(ActionRequest request) {
        try {
            router.routeMessage(producerName, objectMapper.writeValueAsString(request.getWebhookRequest()));
        } catch (Exception e) {
            logger.error("Unable to route message: " + e.getMessage());
        }
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Hi! Try this sample on a speaker device, and stay silent when the mic is open. If trying on the Actions console simulator, click the no-input button next to the text input field.");
        return responseBuilder.build();
    }

    @ForIntent("actions.intent.TEXT")
    public ActionResponse fallback(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("You said " + request.getRawInput().getQuery());
        responseBuilder.add("Try this sample on a speaker device, and stay silent when the mic is open. If trying on the Actions console simulator, click the no-input button next to the text input field.");
        try {
            router.routeMessage(producerName, objectMapper.writeValueAsString(request.getWebhookRequest()));
        } catch (Exception e) {
            logger.error("Unable to route message: " + e.getMessage());
        }
        return responseBuilder.build();
    }

    @ForIntent("actions.intent.NO_INPUT")
    public ActionResponse reprompt(ActionRequest request) {

        try {
            router.routeMessage(producerName, objectMapper.writeValueAsString(request.getWebhookRequest()));
        } catch (Exception e) {
            logger.error("Unable to route message: " + e.getMessage());
        }

        ResponseBuilder responseBuilder = getResponseBuilder(request);
        int repromptCount = request.getRepromptCount();
        String response;
        if (repromptCount == 0) {
            response = "What was that?";
        } else if (repromptCount == 1) {
            response = "Sorry, I didn't catch that. Could you repeat yourself?";
        } else {
            responseBuilder.endConversation();
            response = "Okay let's try this again later.";
        }
        return responseBuilder.add(response).build();
    }
}
