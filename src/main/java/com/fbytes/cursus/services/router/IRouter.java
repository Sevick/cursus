package com.fbytes.cursus.services.router;

import com.fbytes.cursus.model.Producer;

public interface IRouter {
    public void routeMessage(Producer producer, String message);

    public String showConfig();
}
