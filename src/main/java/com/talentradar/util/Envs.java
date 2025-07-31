package com.talentradar.util;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Envs {

  private static final Dotenv envs = Dotenv.configure()
                                           .directory(".")
                                           .ignoreIfMissing()
                                           .load();

  // Base urls
  public static String API_BASE_URL = envs.get("API_BASE_URL"),
                       API_BASE_PATH = envs.get("API_BASE_PATH"),
                       E2E_BASE_URL = envs.get("E2E_BASE_URL");

  // Token encryption secret keys
  public static String JWT_SECRET = envs.get("JWT_SECRET");

  // Databases Configurations
  public static String USER_SERVICE_DB_URL = envs.get("USER_SERVICE_DB_URL"),
                       USER_SERVICE_DB_USER = envs.get("USER_SERVICE_DB_USER"),
                       USER_SERVICE_DB_PASS = envs.get("USER_SERVICE_DB_PASS");

  // Any user password
  public static String ANY_USER_PASS = envs.get("ANY_USER_PASS");

  // Role Ids
  public static String ADMIN_ROLE_ID = envs.get("ADMIN_ROLE_ID"),
                       MANAGER_ROLE_ID = envs.get("MANAGER_ROLE_ID"),
                       DEVELOPER_ROLE_ID = envs.get("DEVELOPER_ROLE_ID");

  // Admins info
  public static String ADMIN_ID = envs.get("ADMIN_ID"),
                       ADMIN_EMAIL = envs.get("ADMIN_EMAIL"),
                       ADMIN2_ID = envs.get("ADMIN2_ID"),
                       ADMIN2_EMAIL = envs.get("ADMIN2_EMAIL"),
                       INACTIVE_ADMIN_ID = envs.get("INACTIVE_ADMIN_ID"),
                       INACTIVE_ADMIN_EMAIL = envs.get("INACTIVE_ADMIN_EMAIL");

  // Managers' info
  public static String MANAGER_ID = envs.get("MANAGER_ID"),
                       MANAGER_EMAIL = envs.get("MANAGER_EMAIL"),
                       MANAGER2_ID = envs.get("MANAGER2_ID"),
                       MANAGER2_EMAIL = envs.get("MANAGER2_EMAIL"),
                       INACTIVE_MANAGER_ID = envs.get("INACTIVE_MANAGER_ID"),
                       INACTIVE_MANAGER_EMAIL = envs.get("INACTIVE_MANAGER_EMAIL");

  // Developers' info
  public static String DEVELOPER_ID = envs.get("DEVELOPER_ID"),
                       DEVELOPER_EMAIL = envs.get("DEVELOPER_EMAIL"),
                       DEVELOPER2_ID = envs.get("DEVELOPER2_ID"),
                       DEVELOPER2_EMAIL = envs.get("DEVELOPER2_EMAIL"),
                       INACTIVE_DEVELOPER_ID = envs.get("INACTIVE_DEVELOPER_ID"),
                       INACTIVE_DEVELOPER_EMAIL = envs.get("INACTIVE_DEVELOPER_EMAIL");

  public static String get(String key) {
    return envs.get(key);
  }
}
