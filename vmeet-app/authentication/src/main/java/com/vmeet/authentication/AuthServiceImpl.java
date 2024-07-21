package com.vmeet.authentication;

import com.vmeet.data.user.UserDTO;
import com.vmeet.data.user.UserRepository;
import com.vmeet.data.user.UserRequestDTO;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Duration SESSION_EXPIRY_DURATION = Duration.ofHours(12);

  private static final String SESSION_SCAN_PATTERN = "*";
  private static final int SESSION_SCAN_BATCH_SIZE = 100;

  RedisTemplate<String, String> redisTemplate;
  UserRepository userRepository;

  public AuthServiceImpl(RedisTemplate<String, String> redisTemplate, UserRepository userRepository) {
    this.redisTemplate = redisTemplate;
    this.userRepository = userRepository;
  }

  @Override
  public LoginResponse login(LoginRequestDTO loginRequestDTO) {
    UserRequestDTO userRequestDTO = new UserRequestDTO()
        .username(loginRequestDTO.getUsername())
        .password(loginRequestDTO.getPassword());

    UserDTO userDTO = userRepository.getUser(userRequestDTO);
    if (userDTO == null) {
      return LoginResponse.failed();
    }

    String sessionId = createSession(userDTO.getId());
    return LoginResponse.successful(sessionId);
  }

  @Override
  public boolean logout(LogoutRequestDTO logoutRequestDTO) {
    String sessionId = logoutRequestDTO.getSessionId();
    return sessionId != null && removeSession(logoutRequestDTO.getSessionId());
  }

  @Override
  public boolean logoutAllSessions(LogoutRequestDTO logoutRequestDTO) {
    String userId = logoutRequestDTO.getUserId();
    String sessionId = logoutRequestDTO.getSessionId();
    if(userId == null && sessionId != null) {
      userId = getUserId(sessionId);
    }

    return removeAllSessions(userId);
  }

  @Override
  public ValidateSessionResponseDTO validateSession(String sessionId) {
    if(sessionId == null) {
      return ValidateSessionResponseDTO.invalidSession();
    }

    String userId = getUserId(sessionId);
    return userId == null ? ValidateSessionResponseDTO.invalidSession() : ValidateSessionResponseDTO.validSession(userId);
  }

  private static String getSessionUserIdRedisKey(String sessionId) {
    return String.format("session:%s:userId", sessionId);
  }

  private static String getUserSessionsSetRedisKey(String userId) {
    return String.format("user:%s:sessions", userId);
  }

  private String createSession(String userId) {
    String sessionId = AuthUtils.createRandomSessionId();

    // Create mapping from sessionId to userId (unique userId per session)
    String sessionUserIdRedisKey = getSessionUserIdRedisKey(sessionId);
    redisTemplate.opsForValue().set(sessionUserIdRedisKey, userId);
    redisTemplate.expire(sessionUserIdRedisKey, SESSION_EXPIRY_DURATION);

    // Create mapping from userId to sessionId (multiple sessions possible per userId)
    String userSessionsSetRedisKey = getUserSessionsSetRedisKey(userId);
    redisTemplate.opsForSet().add(userSessionsSetRedisKey, sessionId);

    return sessionId;
  }

  private String getUserId(String sessionId) {
    String sessionUserIdRedisKey = getSessionUserIdRedisKey(sessionId);
    String userId = redisTemplate.opsForValue().get(sessionUserIdRedisKey);

    return userId;
  }

  private boolean removeSession(String sessionId) {
    String sessionUserIdRedisKey = getSessionUserIdRedisKey(sessionId);
    String userId = redisTemplate.opsForValue().get(sessionUserIdRedisKey);
    redisTemplate.delete(sessionUserIdRedisKey);

    if (userId != null) {
      String userSessionsSetRedisKey = getUserSessionsSetRedisKey(userId);
      redisTemplate.opsForSet().remove(userSessionsSetRedisKey, sessionId);

      return true;
    }

    return false;
  }

  private boolean removeAllSessions(String userId) {
    if(userId == null) {
      return false;
    }

    String userSessionsSetRedisKey = getUserSessionsSetRedisKey(userId);

    ScanOptions scanOptions = ScanOptions.scanOptions().match(SESSION_SCAN_PATTERN).count(SESSION_SCAN_BATCH_SIZE).build();

    try(Cursor<String> cursor = redisTemplate.opsForSet().scan(userSessionsSetRedisKey, scanOptions)) {
      cursor.forEachRemaining(sessionId -> {
        String sessionUserIdRedisKey = getSessionUserIdRedisKey(sessionId);
        redisTemplate.delete(sessionUserIdRedisKey);
      });
    }

    redisTemplate.delete(userSessionsSetRedisKey);
    return true;
  }
}
