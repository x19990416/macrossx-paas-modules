/** create by Guo Limin on 2021/2/6. */
package com.github.x19990416.mxpaas.module.auth;

import com.github.x19990416.mxpaas.module.auth.service.AuthUserService;
import com.github.x19990416.mxpaas.module.common.utils.RedisUtil;
import com.github.x19990416.mxpaas.module.auth.domain.AuthUser;
import com.github.x19990416.mxpaas.module.auth.domain.dto.AuthUserDto;
import com.github.x19990416.mxpaas.module.auth.domain.vo.UserPwdLogin;
import com.github.x19990416.mxpaas.module.auth.service.AuthRoleService;
import com.github.x19990416.mxpaas.module.auth.shiro.token.SysUserToken;
import com.github.x19990416.mxpaas.module.auth.util.JwtUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class SysAuthController {
  private final RedisUtil redisUtil;
  private final AuthRoleService roleService;
  private final AuthUserService userService;
  private final String _PREF_SYS_AUTH_CODE = "sys_auth_code:";

  @AnonymousAccess
  @PostMapping("/login/pwd")
  public ResponseEntity<AuthUserDto> userPwdLogin(@Validated @RequestBody UserPwdLogin login) {
    String redisCodeKey = _PREF_SYS_AUTH_CODE + login.getUuid();
    if (Objects.isNull(redisUtil.get(redisCodeKey))
        || !String.valueOf(redisUtil.get(redisCodeKey))
            .equals(login.getCode())) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    Subject subject = SecurityUtils.getSubject();
    SysUserToken sysUserToken = new SysUserToken(login.getUsername(), login.getPassword());
    subject.login(sysUserToken);
    String jwtToken = JwtUtil.sign(login.getUsername());
    return ResponseEntity.ok(
        new AuthUserDto()
            .setToken(jwtToken)
            .setRoles(roleService.getUserRoles((AuthUser) subject.getPrincipal())));
  }

  @AnonymousAccess
  @GetMapping("/code")
  public ResponseEntity<Object> getCode() {
    // TODO: 整合其他的图形验模块，暂时没空弄，待全部完成后再说。。。。
    Captcha captcha = new ArithmeticCaptcha(110, 42);
    String uuid = _PREF_SYS_AUTH_CODE + UUID.randomUUID();
    String captchaValue = captcha.text();
    if (captchaValue.contains(".")) {
      captchaValue = captchaValue.split("\\.")[0];
    }
    redisUtil.set(uuid, captchaValue, 5, TimeUnit.MINUTES);
    //        Captcha captcha = loginProperties.getCaptcha();
    //        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
    //        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
    //        String captchaValue = captcha.text();
    //        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() &&
    // captchaValue.contains(".")) {
    //            captchaValue = captchaValue.split("\\.")[0];
    //        }
    //        // 保存
    //        redisUtils.set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(),
    // TimeUnit.MINUTES);
    //        // 验证码信息
    //        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
    //            put("img", captcha.toBase64());
    //            put("uuid", uuid);
    //        }};
    return ResponseEntity.ok(Map.of("img", captcha.toBase64(), "uuid", uuid));
  }
}
