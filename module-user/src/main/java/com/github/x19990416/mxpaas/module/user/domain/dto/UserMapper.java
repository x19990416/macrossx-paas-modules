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
package com.github.x19990416.mxpaas.module.user.domain.dto;

import com.github.x19990416.mxpaas.module.common.base.BaseMapper;
import com.github.x19990416.mxpaas.module.auth.domain.AuthRole;
import com.github.x19990416.mxpaas.module.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDto, User> {
  @Mappings({@Mapping(source = "role.name", target = "roles")})
  default Set<String> toRoleName(Set<AuthRole> roles) {
    if (roles == null) {
      return null;
    }
    return roles.stream().map(AuthRole::getLevelName).collect(Collectors.toSet());
  }

  default Set<AuthRole> toRole(Set<String> roleName) {
    if (roleName == null) {
      return null;
    }
    return roleName.stream()
        .map(
            name -> {
              AuthRole role = new AuthRole();
              role.setLevelName(name);
              return role;
            })
        .collect(Collectors.toSet());
  }
}
