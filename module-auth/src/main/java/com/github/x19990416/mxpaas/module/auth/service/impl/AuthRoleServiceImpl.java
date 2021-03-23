/** create by Guo Limin on 2021/2/6. */
package com.github.x19990416.mxpaas.module.auth.service.impl;

import com.github.x19990416.mxpaas.module.auth.domain.AuthRole;
import com.github.x19990416.mxpaas.module.auth.domain.AuthUser;
import com.github.x19990416.mxpaas.module.auth.repository.AuthRoleRepository;
import com.github.x19990416.mxpaas.module.auth.service.AuthRoleService;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthRoleServiceImpl implements AuthRoleService {
  private final AuthRoleRepository authRoleRepository;

  @Override
  public Set<String> getUserRoles(AuthUser authUser) {
    return authRoleRepository.findByUserId(authUser.getId()).stream()
        .map(AuthRole::getLevelName)
        .collect(Collectors.toSet());
  }

  public Set<AuthRole> getUserRoles(Long uid){
    return Sets.newConcurrentHashSet(authRoleRepository.findByUserId(uid));
  }
}
