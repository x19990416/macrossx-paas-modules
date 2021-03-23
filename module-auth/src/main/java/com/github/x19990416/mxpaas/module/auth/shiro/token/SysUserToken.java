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
package com.github.x19990416.mxpaas.module.auth.shiro.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.shiro.authc.UsernamePasswordToken;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class SysUserToken extends UsernamePasswordToken {
    @Getter
  public static enum LoginType {
    USER_PASSWORD("user_passord_realm"),
    USER_MOBIE("user_mobile_realm");
    private String type;

    LoginType(String type) {
      this.type = type;
    }
  }
  /** 其他额信息，比如微信登录放入的是微信返回的code */
  private Object extra;

  private LoginType loginType;

  public SysUserToken() {

    super();
  }

  public SysUserToken(String username, String password) {
    super(username, password);
    loginType = LoginType.USER_PASSWORD;

  }

  public SysUserToken(LoginType logintype, String username, String password, Object extra) {
    super(username, password);
    this.loginType = loginType;
    this.extra = extra;
  }

}
