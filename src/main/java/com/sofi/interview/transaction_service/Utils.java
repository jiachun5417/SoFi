package com.sofi.interview.transaction_service;

import io.vertx.core.json.JsonObject;

public class Utils {

  public static final String APPLICATION_JSON = "application/json";

  public static String buildResponseJson(String msg) {
    return new JsonObject().put("result", msg).encode();
  }
}
