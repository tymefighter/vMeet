package com.vmeet.application.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationSecurityConfig implements WebMvcConfigurer {

  private final SecurityHandlerInterceptor securityHandlerInterceptor;

  public ApplicationSecurityConfig(SecurityHandlerInterceptor securityHandlerInterceptor) {
    this.securityHandlerInterceptor = securityHandlerInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(securityHandlerInterceptor);
  }
}
