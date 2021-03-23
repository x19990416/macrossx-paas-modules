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
package com.github.x19990416.mxpaas.module.logging.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.x19990416.mxpaas.module.common.utils.ConvertUtil;
import com.github.x19990416.mxpaas.module.jpa.QueryHelper;
import com.github.x19990416.mxpaas.module.logging.domain.SysLog;
import com.github.x19990416.mxpaas.module.logging.domain.dto.SysLogMapper;
import com.github.x19990416.mxpaas.module.logging.domain.dto.SysLogQueryCriteria;
import com.github.x19990416.mxpaas.module.logging.repository.SysLogRepository;
import com.github.x19990416.mxpaas.module.logging.service.SysLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SysLogServiceImpl implements SysLogService {
  private final SysLogRepository sysLogRepository;
  private final SysLogMapper sysLogMapper;

  @Override
  public Object queryAll(SysLogQueryCriteria criteria, Pageable pageable) {
    Page<SysLog> page =
        sysLogRepository.findAll(
            ((root, criteriaQuery, cb) -> QueryHelper.getPredicate(root, criteria, cb)), pageable);
    return ConvertUtil.toPageVo(page, sysLogMapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog log) {

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    com.github.x19990416.mxpaas.module.logging.annotation.SysLog aopLog = method.getAnnotation(com.github.x19990416.mxpaas.module.logging.annotation.SysLog.class);

    // 方法路径
    String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

    // 描述
    if (log != null) {
      log.setDescription(aopLog.value());
    }
    assert log != null;
    log.setRequestIp(ip);

    //log.setAddress(HttpRequesth.getCityInfo(log.getRequestIp()));
    log.setMethod(methodName);
    log.setUsername(username);
    log.setParams(getParameter(method, joinPoint.getArgs()));
    log.setBrowser(browser);
    sysLogRepository.save(log);
  }

  private String getParameter(Method method, Object[] args) {
    List<Object> argList = new ArrayList<>();
    Parameter[] parameters = method.getParameters();
    for (int i = 0; i < parameters.length; i++) {
      //将RequestBody注解修饰的参数作为请求参数
      RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
      if (requestBody != null) {
        argList.add(args[i]);
      }
      //将RequestParam注解修饰的参数作为请求参数
      RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
      if (requestParam != null) {
        Map<String, Object> map = new HashMap<>();
        String key = parameters[i].getName();
        if (!StringUtils.isEmpty(requestParam.value())) {
          key = requestParam.value();
        }
        map.put(key, args[i]);
        argList.add(map);
      }
    }
    if (argList.size() == 0) {
      return "";
    }
    return JSON.toJSONString(argList);
  }
}
