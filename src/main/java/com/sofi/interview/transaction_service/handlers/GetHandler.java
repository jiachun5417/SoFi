package com.sofi.interview.transaction_service.handlers;

import com.sofi.interview.transaction_service.TransactionCache;
import com.sofi.interview.transaction_service.Utils;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

@Log4j
public class GetHandler implements Handler<RoutingContext> {

  public void handle(RoutingContext routingContext) {
    log.info("GetHandler() start");

    String userId = routingContext.request().getHeader("user-id");
    if (StringUtils.isEmpty(userId)) {
      routingContext.response()
          .setStatusCode(HttpStatus.SC_BAD_REQUEST)
          .putHeader("content-type", Utils.APPLICATION_JSON)
          .end(Utils.buildResponseJson("user-is is required"));
    }

    List<String> merchantList = TransactionCache.INSTANCE.getTopMerchats(Integer.valueOf(userId));
    if (merchantList == null) {
      routingContext.response()
          .setStatusCode(HttpStatus.SC_METHOD_FAILURE)
          .putHeader("content-type", Utils.APPLICATION_JSON)
          .end(Utils.buildResponseJson("Insufficient number of transactions"));
    }

    routingContext.response()
        .setStatusCode(HttpStatus.SC_OK)
        .putHeader("content-type", Utils.APPLICATION_JSON)
        .end((new JsonArray(merchantList)).encode());
  }
}
