package com.vmeet.authentication;

import com.vmeet.data.user.UserDTO;
import com.vmeet.data.user.UserRepository;
import com.vmeet.data.user.UserRequestDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class AuthServiceImpl implements AuthService {

  private static final Duration SESSION_EXPIRY_DURATION = Duration.ofHours(12);

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
    return removeSession(logoutRequestDTO.getSessionId());
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
}
