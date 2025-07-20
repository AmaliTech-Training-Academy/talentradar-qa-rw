package com.talentradar.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnvUtil {

  private static Dotenv $;

  private static void load() {
    if (Objects.isNull($))
      $ = Dotenv.configure()
        .directory(".")
        .ignoreIfMissing()
        .load();
  }

  public static String getEnv(String key, String fallback) {
    String value = getEnv(key);

    if (Objects.isNull(value))
      return fallback;
    return value;
  }

  public static String getEnv(String key) {
    load();
    return  $.get(key);
  }
}
