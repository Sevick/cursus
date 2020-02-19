package com.fbytes.cursus.controller;

import com.google.actions.api.ActionsSdkApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("gaction")
public class GoogleActionsController {

    @Autowired
    ActionsSdkApp actionsSdkApp;

    private static final Logger logger = LoggerFactory.getLogger(GoogleActionsController.class);

    private String userText = "hello";
    private final String LANG_CODE = "en-US";
    private final String PROJECT_ID = "my-goblin";
    private String sessionId = UUID.randomUUID().toString();
    private final String credential = "MY_PATH_TO_GOOGLE_JSON_CREDENTIAL_FILE";
    private final String URL = "https://dialogflow.googleapis.com/v2/{session=projects/my-goblin/agent/sessions/" +
            sessionId + "}:detectIntent";

    /*
    @RequestMapping("/intent")
    public String doThings() throws IOException {

        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of(PROJECT_ID, sessionId);

            TextInput.Builder textInput = TextInput.newBuilder().setText(userText).setLanguageCode(LANG_CODE);

            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            return response.toString();
        }
    }
     */

    @RequestMapping(value = "/gaction", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json"})
    public String gAction(@RequestBody String body, @RequestHeader Map<String, String> headers) throws IOException {
        logger.debug("Request body: " + body);
        try {
            return actionsSdkApp.handleRequest(body, headers).get();
        } catch (Exception e) {
            return handleError(e);
        }
    }

    private String handleError(Exception e) {
        logger.error("Error in App.handleRequest ", e);
        return "Error handling the intent - " + e.getMessage();
    }

}
