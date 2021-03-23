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
package com.github.x19990416.mxpaas.module.menu.repository;

import com.github.x19990416.mxpaas.module.menu.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

  List<Menu> findByPid(Long pid);

  @Query(
      value =
          "SELECT m.* FROM sys_menu m, sys_roles_menus r WHERE "
              + "m.menu_id = r.menu_id AND r.role_id IN ?1 AND type != ?2 order by m.menu_sort asc",
      nativeQuery = true)
  List<Menu> findByRoleIdsAndTypeNot(List<Long> roleIds, int type);

  @Query(
      value =
          "SELECT m.* FROM sys_menu m, sys_roles_menus r WHERE "
              + "m.menu_id = r.menu_id AND r.role_id IN ?1 AND type != ?2 order by m.menu_sort asc #{#pageable}",
      nativeQuery = true)
  Page<Menu> findByRoleIdsAndTypeNotByPage(List<Long> roleIds, int type, Pageable pageable);
}
