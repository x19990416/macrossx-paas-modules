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
package com.github.x19990416.mxpaas.module.logging.aspect;

import com.github.x19990416.mxpaas.module.auth.service.AuthUserService;
import com.github.x19990416.mxpaas.module.common.utils.HttpServletRequestUtil;
import com.github.x19990416.mxpaas.module.logging.domain.SysLog;
import com.github.x19990416.mxpaas.module.logging.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class SysLogAspect {
	private final SysLogService sysLogService;
	private final AuthUserService authUserService;

	ThreadLocal<Long> currentTime = new ThreadLocal<>();

	public SysLogAspect(SysLogService logService, AuthUserService authUserService) {
		this.sysLogService = logService;
		this.authUserService = authUserService;
	}

	@Pointcut("@annotation(com.github.x19990416.mxpaas.module.logging.annotation.SysLog)")
	public void logPointcut() {
	}

	@Around("logPointcut()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result;
		currentTime.set(System.currentTimeMillis());
		result = joinPoint.proceed();
		SysLog log = new SysLog().setLogType("INFO").setTime(System.currentTimeMillis() - currentTime.get());
		currentTime.remove();
		sysLogService.save(getUsername(), HttpServletRequestUtil.getBrowser(), HttpServletRequestUtil.getIp(),joinPoint, log);
		return result;
	}

	public String getUsername() {
		try {
			return authUserService.getCurrentUser().getUsername();
		}catch (Exception e){
			return "";
		}
	}
}
