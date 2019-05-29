package com.sofi.interview.transaction_service.handlers;

import com.sofi.interview.transaction_service.TransactionCache;
import com.sofi.interview.transaction_service.Utils;
import com.sofi.interview.transaction_service.models.Transaction;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpStatus;

@Log4j
public class PostHandler implements Handler<RoutingContext> {

  public void handle(RoutingContext routingContext) {
    log.info("PostHandler() start");

    routingContext.request().bodyHandler(bodyHandler -> {
      final JsonObject body = bodyHandler.toJsonObject();
      final Transaction postedTransaction = Json.mapper.convertValue(body, Transaction.class);

      if (postedTransaction == null || !postedTransaction.validate()) {
        routingContext.response()
            .setStatusCode(HttpStatus.SC_BAD_REQUEST)
            .putHeader("content-type", Utils.APPLICATION_JSON)
            .end(Utils.buildResponseJson("Invalid input"));
      }

      TransactionCache.INSTANCE.add(postedTransaction);

      routingContext.response()
          .setStatusCode(HttpStatus.SC_OK)
          .putHeader("content-type", Utils.APPLICATION_JSON)
          .end(Utils.buildResponseJson("ok"));
    });
  }
}
