package com.sofi.interview.transaction_service;

import com.sofi.interview.transaction_service.handlers.GetHandler;
import com.sofi.interview.transaction_service.handlers.PostHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j;

@Log4j
public class MainVerticle extends AbstractVerticle {

  private static final String API_ENDPOINT = "/v1/transaction";
  private static final String APPLICATION_JSON = "application/json";
  private static final int PORT = 8080;

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    Router router = Router.router(this.vertx);
    mountRoutes(router);

    vertx.createHttpServer().requestHandler(router::accept).listen(PORT, http -> {
      if (http.succeeded()) {
        startFuture.complete();
        log.info("HTTP server started on port " + PORT);
      } else {
        log.error("service failed to start", http.cause());
        startFuture.fail(http.cause());
      }
    });
  }

  private void mountRoutes(Router router) {
    router.route(API_ENDPOINT)
        .method(HttpMethod.GET)
        .produces(APPLICATION_JSON)
        .handler(new GetHandler());

    router.route(API_ENDPOINT)
        .method(HttpMethod.POST)
        .produces(APPLICATION_JSON)
        .handler(new PostHandler());
  }
}
