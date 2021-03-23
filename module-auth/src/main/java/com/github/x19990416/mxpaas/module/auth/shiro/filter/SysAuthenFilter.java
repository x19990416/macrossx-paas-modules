/** create by Guo Limin on 2021/2/6. */
package com.github.x19990416.mxpaas.module.auth.shiro.filter;

import com.github.x19990416.mxpaas.module.common.utils.SpringContextHolder;
import com.github.x19990416.mxpaas.module.auth.AnonymousAccess;
import com.github.x19990416.mxpaas.module.auth.shiro.token.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class SysAuthenFilter extends BasicHttpAuthenticationFilter {
  private static final String _TOKEN_HEADER = "macrossx-auth";

  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
    HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
    HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
      httpServletResponse.setStatus(HttpStatus.OK.value());
      return false;
    }
    return super.preHandle(request, response);
  }

  /** 1.如果isAccessAllowed返回false 则执行onAccessDenied 2. 有注解{@Link AnonymousAccess}的方法则为匿名访问。 */
  @Override
  protected boolean isAccessAllowed(
      ServletRequest request, ServletResponse response, Object mappedValue) {
    if (request instanceof HttpServletRequest) {
      try {
        RequestMappingHandlerMapping requestMappingHandlerMapping =
            (RequestMappingHandlerMapping)
                SpringContextHolder.getBean(RequestMappingHandlerMapping.class);
        HandlerExecutionChain handlerExecutionChain =
            requestMappingHandlerMapping.getHandler((HttpServletRequest) request);
        if (!Objects.isNull(handlerExecutionChain)) {
          HandlerMethod handlerMethod = (HandlerMethod) handlerExecutionChain.getHandler();
          if (!Objects.isNull(
              handlerMethod.getMethod().getDeclaredAnnotation(AnonymousAccess.class))) {
            return true;
          }
        }
        executeLogin(request, response);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return false;
      }
    }
    return super.isAccessAllowed(request, response, mappedValue);
  }

  protected boolean executeLogin(ServletRequest request, ServletResponse response) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader(_TOKEN_HEADER);
    if (StringUtils.isEmpty(token)) return false;
    JwtToken jwtToken = new JwtToken(token);
    getSubject(request, response).login(jwtToken);
    // 如果没有抛出异常则代表登入成功，返回true
    return true;
  }
  /** 在访问controller前判断是否登录，返回json，不进行重定向。 */
  @Override
  protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
      throws IOException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader(
        "Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
    httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json");
    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    httpServletResponse.getWriter().write("{\"message\": \"未登陆或未授权\"}");
    return false;
  }

  protected void postHandle(ServletRequest request, ServletResponse response) {
    // 添加跨域支持
    this.fillCorsHeader(WebUtils.toHttp(request), WebUtils.toHttp(response));
  }

  /** 添加跨域支持 */
  protected void fillCorsHeader(
      HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    httpServletResponse.setHeader(
        "Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,HEAD");
    httpServletResponse.setHeader(
        "Access-Control-Allow-Headers",
        httpServletRequest.getHeader("Access-Control-Request-Headers"));
  }
}
