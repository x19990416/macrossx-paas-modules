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
package com.github.x19990416.mxpaas.module.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtUtil {
  private static final String _TOKEN_CLAIM = "macrossx-sys-auth-token-claim";
  private static final long _TOKEN_EXPIRE_TIME = 30 * 24 * 3600 * 1000;
  private static final String _SECRET = "macrossx_secret";

  public static boolean verify(String token) {
    try {
      // 根据密码生成JWT效验器
      Algorithm algorithm = Algorithm.HMAC256(_SECRET);
      JWTVerifier verifier =
          JWT.require(algorithm).withClaim(_TOKEN_CLAIM, getClaim(token)).build();
      // 效验TOKEN
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException exception) {
      return false;
    }
  }

  public static String getClaim(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim(_TOKEN_CLAIM).asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public static String sign(String username) {
    Date date = new Date(System.currentTimeMillis() + _TOKEN_EXPIRE_TIME);
    Algorithm algorithm = Algorithm.HMAC256(_SECRET);
    return JWT.create().withClaim(_TOKEN_CLAIM, username).withExpiresAt(date).sign(algorithm);
  }

  public static Date getIssuedAt(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getIssuedAt();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public static boolean isTokenExpired(String token) {
    Date now = Calendar.getInstance().getTime();
    DecodedJWT jwt = JWT.decode(token);
    return jwt.getExpiresAt().before(now);
  }

  public static String refreshTokenExpired(String token) {
    DecodedJWT jwt = JWT.decode(token);
    Map<String, Claim> claims = jwt.getClaims();
    try {
      Date date = new Date(System.currentTimeMillis() + _TOKEN_EXPIRE_TIME);
      Algorithm algorithm = Algorithm.HMAC256(_SECRET);
      JWTCreator.Builder builer = JWT.create().withExpiresAt(date);
      for (Map.Entry<String, Claim> entry : claims.entrySet()) {
        builer.withClaim(entry.getKey(), entry.getValue().asString());
      }
      return builer.sign(algorithm);
    } catch (JWTCreationException e) {
      log.error("", e);
      return null;
    }
  }
}
