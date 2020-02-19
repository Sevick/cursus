package com.fbytes.cursus.services.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbytes.cursus.config.RouterConfig;
import com.fbytes.cursus.model.Producer;
import com.fbytes.cursus.services.sender.ISendInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@RefreshScope
public class Router implements IRouter {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private Map<String, ISendInterface> senders;
    @Autowired
    private RouterConfig routerConfig;

    @PostConstruct
    private void init() {
        try {
            logger.info("Starting with following configuration:");
            logger.info("Available senders: " + objectMapper.writeValueAsString(senders));
            logger.info("Routing map: " + objectMapper.writeValueAsString(routerConfig.getRouting()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void routeMessage(Producer producer, String message) {
        List<String> targets = routerConfig.getRouting().get(producer.toString());
        if (CollectionUtils.isEmpty(targets)) {
            logger.warn("Unable to route message - no senders configured for producer: " + producer);
        } else {
            targets.stream()
                    .map(targetString -> senders.get(targetString))
                    .forEach(target -> {
                        logger.info("Routing message to: " + target.getClass().toString());
                        target.sendData(message);
                    });
        }
    }

    @Override
    public String showConfig() {
        try {
            return objectMapper.writeValueAsString(routerConfig.getRouting());
        } catch (JsonProcessingException e) {
            return "Router:showConfig: " + e.getMessage();
        }
    }
}
