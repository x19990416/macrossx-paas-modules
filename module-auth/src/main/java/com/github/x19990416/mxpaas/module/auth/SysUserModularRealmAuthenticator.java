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
package com.github.x19990416.mxpaas.module.auth;

import com.github.x19990416.mxpaas.module.auth.shiro.token.JwtToken;
import com.github.x19990416.mxpaas.module.auth.shiro.token.SysUserToken;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.HashMap;

/** 统一处理 Realm 登录策略 */
public class SysUserModularRealmAuthenticator extends ModularRealmAuthenticator {
  protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    // 判断getRealms()是否返回为空
    assertRealmsConfigured();
    // 所有Realm
    Collection<Realm> realms = getRealms();
    // 登录类型对应的所有Realm
    HashMap<String, Realm> realmHashMap = Maps.newHashMapWithExpectedSize(realms.size());
    for (Realm realm : realms) {
      realmHashMap.put(realm.getName(), realm);
    }

    if (authenticationToken instanceof SysUserToken) {
      String name = ((SysUserToken) authenticationToken).getLoginType().name();
      return doSingleRealmAuthentication(
          realmHashMap.get(name), authenticationToken);
    } else if (authenticationToken instanceof JwtToken) {
      return doSingleRealmAuthentication(realmHashMap.get("jwt_realm"), authenticationToken);
    } else {
      return doMultiRealmAuthentication(realms, authenticationToken);
    }
  }
}
