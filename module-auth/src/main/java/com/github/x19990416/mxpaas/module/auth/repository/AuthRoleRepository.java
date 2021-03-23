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
package com.github.x19990416.mxpaas.module.auth.repository;

import com.github.x19990416.mxpaas.module.auth.domain.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
  @Query(
      value =
          "SELECT r.* FROM sys_role r, sys_users_roles u WHERE "
              + "r.role_id = u.role_id AND u.user_id = ?1",
      nativeQuery = true)
  List<AuthRole> findByUserId(Long id);
}
