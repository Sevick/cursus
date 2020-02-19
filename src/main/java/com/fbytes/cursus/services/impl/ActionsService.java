package com.fbytes.cursus.services.impl;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.ActionsSdkApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ActionsService extends ActionsSdkApp {

    @PostConstruct
    private void init() {

    }

    @ForIntent("actions.intent.MAIN")
    public ActionResponse welcome(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("Hi! Try this sample on a speaker device, and stay silent when the mic is open. If trying on the Actions console simulator, click the no-input button next to the text input field.");
        return responseBuilder.build();
    }

    @ForIntent("actions.intent.TEXT")
    public ActionResponse fallback(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        responseBuilder.add("You said " + request.getRawInput().getQuery());
        responseBuilder.add("Try this sample on a speaker device, and stay silent when the mic is open. If trying on the Actions console simulator, click the no-input button next to the text input field.");
        return responseBuilder.build();
    }

    @ForIntent("actions.intent.NO_INPUT")
    public ActionResponse reprompt(ActionRequest request) {
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
