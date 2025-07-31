package com.talentradar.util;

import com.talentradar.dto.seeds.RoleSeedDto;
import com.talentradar.dto.seeds.SeedsRootDto;
import com.talentradar.dto.seeds.UserSeedDto;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;

@SuppressWarnings("LoggingSimilarMessage")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class PostgresYamlSeeder {

  private static SeedsRootDto seeds;
  private static final HikariDataSource source;

  static {
    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(Envs.USER_SERVICE_DB_URL);
    config.setUsername(Envs.USER_SERVICE_DB_USER);
    config.setPassword(Envs.USER_SERVICE_DB_PASS);
    config.setMaximumPoolSize(10);

    source = new HikariDataSource(config);
  }

  static Connection connection() throws SQLException {
    return source.getConnection();
  }

  private static SeedsRootDto load() {
    if (Objects.isNull(seeds)) {
      log.info("Extracting seeds from yaml file..");
      seeds = YamlLoader.extract("seeds.yaml", SeedsRootDto.class);
      log.info("✅ Seeds extracted");
    } else
      log.info("Using seeds cache");
    return seeds;
  }

  public static void seedUsers() {
    log.info("Starting users seeding job...");
    log.info("Initiating connection with database...");

    try {
      String sql = "INSERT INTO users (id, email, username, full_name, status, role, password) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?)";
      try (PreparedStatement preparedStatement = connection().prepareStatement(sql)) {
        for (UserSeedDto user : load().users()) {
          preparedStatement.setObject(1, UUID.fromString(user.id()));
          preparedStatement.setString(2, user.email());
          preparedStatement.setString(3, user.username());
          preparedStatement.setString(4, user.name());
          preparedStatement.setString(5, user.status());
          preparedStatement.setObject(6, UUID.fromString(user.role()));
          preparedStatement.setString(7, user.password());
          preparedStatement.executeUpdate();
        }
      }
      log.info("✅ Users seeded successfully.");
    } catch (Exception e) {
      log.error("Users seed job failed: {}", e.getMessage());
    }
  }

  public static void seedRoles() {
    log.info("Starting roles seed job...");
    log.info("Initiating connection with database...");

    try {
      String sql = "INSERT INTO roles (id, roleName) VALUES (?, ?)";
      try (PreparedStatement preparedStatement = connection().prepareStatement(sql)) {
        for (RoleSeedDto role : load().roles()) {
          preparedStatement.setObject(1, UUID.fromString(role.id()));
          preparedStatement.setString(2, role.name());
          preparedStatement.executeUpdate();
        }
      }
      log.info("✅ Roles seeded successfully.");
    } catch (Exception e) {
      log.error("Roles seed job failed: {}", e.getMessage());
    }
  }

  public static void seed() {
    purge();
    seedRoles();
    seedUsers();
  }

  public static void resetUsers() {
    log.info("Starting users reset job...");
    log.info("Initiating connection with database...");

    try {
      List<UUID> keepIds = List.of(
        UUID.fromString(Envs.ADMIN_ID),
        UUID.fromString(Envs.ADMIN2_ID),
        UUID.fromString(Envs.INACTIVE_ADMIN_ID),

        UUID.fromString(Envs.MANAGER_ID),
        UUID.fromString(Envs.MANAGER2_ID),
        UUID.fromString(Envs.INACTIVE_MANAGER_ID),

        UUID.fromString(Envs.DEVELOPER_ID),
        UUID.fromString(Envs.DEVELOPER2_ID),
        UUID.fromString(Envs.INACTIVE_DEVELOPER_ID)
      );

      String placeholders = String.join(",", Collections.nCopies(keepIds.size(), "?"));
      String sql = "DELETE FROM users WHERE id NOT IN (" + placeholders + ")";

      try (PreparedStatement statement = connection().prepareStatement(sql)) {
        for (int i = 0; i < keepIds.size(); i++)
          statement.setObject(i + 1, keepIds.get(i));
        statement.executeUpdate();
        log.info("✅ Users reset successfully.");
      }
    } catch (Exception e) {
      log.error("Users reset job failed: {}", e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public static void resetSessions() {
    log.info("Starting sessions reset job...");
    log.info("Initiating connection with database...");

    try (PreparedStatement statement = connection().prepareStatement("DELETE FROM sessions")) {
        statement.execute();
        log.info("✅ Sessions reset successfully.");
    } catch (Exception e) {
      log.error("Sessions reset job failed: {}", e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public static void purge() {
    log.info("Starting purge job...");
    log.info("Initiating connection with database...");

    try {
      List<Map<String, String>> queries = List.of(
        Map.of("roles", "DELETE FROM roles WHERE"),
        Map.of("sessions", "DELETE FROM sessions WHERE"),
        Map.of("users", "DELETE FROM users WHERE")
      );
      queries.forEach(query -> query
        .forEach((key, value) -> {
          try (PreparedStatement statement = connection().prepareStatement(value)) {
            log.info("Purging {}..", key);
            statement.execute();
            log.info("{} purged successfully", key);
          } catch (SQLException e) {
            log.info("Failed to purge {}: {}", key, e.getMessage());
            throw new RuntimeException(e.getMessage());
          }
        })
      );
      log.info("Purge job completed");
    } catch (Exception e) {
      log.error("Purge job failed: {}", e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public static void disconnect() {
    if (source.isRunning())
      source.close();
  }
}

