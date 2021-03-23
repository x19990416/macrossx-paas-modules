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

import com.github.x19990416.mxpaas.module.common.config.RsaProperties;
import com.github.x19990416.mxpaas.module.common.utils.SpringContextHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

@Slf4j
public class SysCredentialsMatcher extends SimpleCredentialsMatcher {
  public static String privateKey="MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEA0vfvyTdGJkdbHkB8mp0f3FE0GYP3AYPaJF7jUd1M0XxFSE2ceK3k2kw20YvQ09NJKk+OMjWQl9WitG9pB6tSCQIDAQABAkA2SimBrWC2/wvauBuYqjCFwLvYiRYqZKThUS3MZlebXJiLB+Ue/gUifAAKIg1avttUZsHBHrop4qfJCwAI0+YRAiEA+W3NK/RaXtnRqmoUUkb59zsZUBLpvZgQPfj1MhyHDz0CIQDYhsAhPJ3mgS64NbUZmGWuuNKp5coY2GIj/zYDMJp6vQIgUueLFXv/eZ1ekgz2Oi67MNCk5jeTF2BurZqNLR3MSmUCIFT3Q6uHMtsB9Eha4u7hS31tj1UWE+D+ADzp59MGnoftAiBeHT7gDMuqeJHPL4b+kC+gzV4FGTfhR9q3tTbklZkD2A==";
  private static String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANL378k3RiZHWx5AfJqdH9xRNBmD9wGD2iRe41HdTNF8RUhNnHit5NpMNtGL0NPTSSpPjjI1kJfVorRvaQerUgkCAwEAAQ==";

  @SneakyThrows
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>{}",SpringContextHolder.getBean(RsaProperties.class).getPublicKey());
    //TODO:RSA解密匹配 token和info，回头一起封装
    /*
    RsaUtils.decryptByPrivateKey(privateKey,String.valueOf((char[])token.getCredentials()));
    RsaUtils.decryptByPublicKey(publicKey,info.getCredentials().toString());
    log.info(">>>>>>>>>>>>>>>>>>>>{}",Objects.isNull(token.getCredentials()));
    log.info("{}",Objects.isNull(info.getCredentials()));
    log.info("{}",token.getCredentials().getClass());
    log.info("{}",String.valueOf(info.getCredentials()));
    return !Objects.isNull(token.getCredentials())
        && !Objects.isNull(info.getCredentials())
        && token.getCredentials().equals(info.getCredentials());
    }*/
    return true;}
}
