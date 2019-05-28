package com.sofi.interview.transaction_service.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpStatus;

@Log4j
public class PostHandler implements Handler<RoutingContext> {

  public void handle(RoutingContext routingContext) {
    log.info("PostHandler()");

    routingContext.response()
        .setStatusCode(HttpStatus.SC_OK)
        .putHeader("content-type", "application/json")
        .end("{\"result\":\"ok\"}");
  }
}
