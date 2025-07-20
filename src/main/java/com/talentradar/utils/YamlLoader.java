package com.talentradar.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlLoader {

  public static <T> T extract(String resourcePath, Class<T> clazz) {
    try (InputStream yis = YamlLoader.class
            .getClassLoader()
            .getResourceAsStream(resourcePath)) {
      if (yis == null)
        throw new IllegalStateException("YAML file not found:" + resourcePath);

      // Read YAML as string
      String yamlContent = new String(yis.readAllBytes(), StandardCharsets.UTF_8);

      // Replace ${VAR} with env or .env values
      String interpolated = interpolateEnvVars(yamlContent);

      // Parse YAML into POJO
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      return mapper.readValue(interpolated, clazz);

    } catch (Exception e) {
      throw new RuntimeException("Failed to load YAML test data:~" + e.getMessage());
    }
  }

  private static String interpolateEnvVars(String content) {
    Pattern pattern = Pattern.compile("\\$\\{(.+?)}");
    Matcher matcher = pattern.matcher(content);
    StringBuilder sb = new StringBuilder();

    while (matcher.find()) {
      String varName = matcher.group(1);
      String value = EnvUtil.getEnv(varName, "");

      if (value == null)
        throw new IllegalStateException("Missing environment variable: " + varName);

      matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
    }
    matcher.appendTail(sb);

    return sb.toString();
  }
}
