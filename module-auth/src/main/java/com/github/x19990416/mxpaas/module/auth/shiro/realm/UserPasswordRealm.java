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
package com.github.x19990416.mxpaas.module.auth.shiro.realm;

import com.github.x19990416.mxpaas.module.auth.domain.AuthRole;
import com.github.x19990416.mxpaas.module.auth.domain.AuthUser;
import com.github.x19990416.mxpaas.module.auth.service.AuthUserService;
import com.github.x19990416.mxpaas.module.auth.shiro.SysCredentialsMatcher;
import com.github.x19990416.mxpaas.module.auth.shiro.token.SysUserToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPasswordRealm extends AuthorizingRealm {
  private final AuthUserService authUserService;

  public String getName() {
    return SysUserToken.LoginType.USER_PASSWORD.name();
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    if (token instanceof SysUserToken) {
      return ((SysUserToken) token).getLoginType().equals(SysUserToken.LoginType.USER_PASSWORD);
    }
    return false;
  }

  /** 授权 */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    AuthUser authUser = (AuthUser) principals.getPrimaryPrincipal();
    authorizationInfo.addRoles(
        authUser.getRoles().stream().map(AuthRole::getLevelName).collect(Collectors.toSet()));
    return authorizationInfo;
  }

  /** 认证 */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
    AuthUser authUser = authUserService.getUserByUsername(usernamePasswordToken.getUsername());
    if (authUser == null) {
      throw new AccountException("用户名不正确");
    }
    return new SimpleAuthenticationInfo(authUser, authUser.getPassword(), getName());
  }

  @Override
  public CredentialsMatcher getCredentialsMatcher() {
    return new SysCredentialsMatcher();
  }
}
