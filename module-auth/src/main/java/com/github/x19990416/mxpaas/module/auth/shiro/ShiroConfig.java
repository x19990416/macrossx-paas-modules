/*
 *  Copyright (c) 2020-2021 Guo Limin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.x19990416.mxpaas.module.auth.shiro;

import com.github.x19990416.mxpaas.module.auth.SysUserModularRealmAuthenticator;
import com.github.x19990416.mxpaas.module.auth.shiro.filter.SysAuthenFilter;
import com.github.x19990416.mxpaas.module.auth.shiro.realm.JwtRealm;
import com.github.x19990416.mxpaas.module.auth.shiro.realm.UserPasswordRealm;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfig {
  private final ShiroProperties shiroProperties;
  private final ShiroRedisCacheManager shiroRedisCacheManager;
  private final UserPasswordRealm userPasswordRealm;
  private final JwtRealm jwtRealm;

  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 1. cookies
    securityManager.setRememberMeManager(getCookieRememberMeManager());
    // 2. cache
    securityManager.setCacheManager(shiroRedisCacheManager);
    // 3. realm
   // securityManager.setRealms(Lists.newArrayList(userPasswordRealm,jwtRealm));
    // 4. session

     securityManager.setAuthenticator(sysUserModularRealmAuthenticator());
    // RequestMappingHandlerMapping:

    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    shiroFilterFactoryBean.setFilterChainDefinitionMap(genFilterChainMap());
    shiroFilterFactoryBean.getFilters().put("authc", sysAuthenFilter());
    return shiroFilterFactoryBean;
  }

  private Map<String, String> genFilterChainMap() {
    Map<String, String> filterChainMap = Maps.newConcurrentMap();
    // 置免认证 url
    if (!StringUtils.isEmpty(shiroProperties.getAnonUrl())) {
      for (String url : shiroProperties.getAnonUrl().split(",")) {
        // filterChainMap.put(url, "anon");
      }
    }
    filterChainMap.put("/login", "anon");
    filterChainMap.put("/**", "authc");
    System.err.println(filterChainMap);
    return filterChainMap;
  }

  @Bean
  public SysCredentialsMatcher getSysCredentialsMatcher() {
    return new SysCredentialsMatcher();
  }

  private CookieRememberMeManager getCookieRememberMeManager() {
    SimpleCookie cookie = new SimpleCookie(CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME);
    // no https only
    cookie.setSecure(shiroProperties.isHttps());
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(cookie);
    return cookieRememberMeManager;
  }

  @Bean("SysUserModularRealmAuthenticator")
  public SysUserModularRealmAuthenticator sysUserModularRealmAuthenticator() {
    SysUserModularRealmAuthenticator sysUserModularRealmAuthenticator =
        new SysUserModularRealmAuthenticator();
    // 设置realm判断条件
    sysUserModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());

    sysUserModularRealmAuthenticator.setRealms(Lists.newArrayList(userPasswordRealm,jwtRealm));
    return sysUserModularRealmAuthenticator;
  }

  @Bean("SysAuthenFilter")
  public SysAuthenFilter sysAuthenFilter() {
    return new SysAuthenFilter();
  }

  @Bean
  public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }
}
