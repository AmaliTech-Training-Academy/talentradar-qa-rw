package com.talentradar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemLogger {
  private SystemLogger() {}

  public static Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }
}
