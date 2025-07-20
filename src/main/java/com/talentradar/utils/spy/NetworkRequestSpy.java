package com.talentradar.utils.spy;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Request;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class NetworkRequestSpy {

  public static Set<String> spy(Page page, Runnable action) {
    Set<String> capturedUrls = Collections.newSetFromMap(new ConcurrentHashMap<>());
    Consumer<Request> requestListener = request -> capturedUrls.add(request.url());

    page.onRequest(requestListener);

    try {
      action.run();

      long start = System.currentTimeMillis();
      while ((System.currentTimeMillis() - start) < 15_000) {
        try {
          //noinspection BusyWait
          Thread.sleep(200);
        } catch (InterruptedException ignored) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    } finally {
      page.offRequest(requestListener);
    }
    return capturedUrls;
  }
}
