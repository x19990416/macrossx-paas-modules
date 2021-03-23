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
package com.github.x19990416.mxpaas.module.menu.service.impl;

import com.github.x19990416.mxpaas.module.common.utils.ConvertUtil;
import com.github.x19990416.mxpaas.module.common.vo.PageVo;
import com.github.x19990416.mxpaas.module.auth.domain.AuthRole;
import com.github.x19990416.mxpaas.module.auth.service.AuthRoleService;
import com.github.x19990416.mxpaas.module.jpa.QueryHelper;
import com.github.x19990416.mxpaas.module.menu.domain.Menu;
import com.github.x19990416.mxpaas.module.menu.domain.dto.MenuDto;
import com.github.x19990416.mxpaas.module.menu.domain.dto.MenuMapper;
import com.github.x19990416.mxpaas.module.menu.domain.dto.MenuQueryCriteria;
import com.github.x19990416.mxpaas.module.menu.domain.vo.MenuMetaVo;
import com.github.x19990416.mxpaas.module.menu.domain.vo.MenuVo;
import com.github.x19990416.mxpaas.module.menu.repository.MenuRepository;
import com.github.x19990416.mxpaas.module.menu.service.MenuService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "menu")
@Slf4j
public class MenuServiceImpl implements MenuService {
  private final AuthRoleService authRoleService;
  private final MenuRepository menuRepository;
  private final MenuMapper menuMapper;
  //
  @Override
  @Cacheable(key = "'user:' + #p0")
  public List<MenuDto> findByUser(Long userId) {
    List<Long> roles =
        authRoleService.getUserRoles(userId).stream()
            .map(AuthRole::getId)
            .collect(Collectors.toList());
    List<Menu> menus = menuRepository.findByRoleIdsAndTypeNot(roles, 2);
    return menuMapper.toDto(menus);
  }

  public PageVo<MenuDto> findByRole(List<Long> roleIds, Integer type, Pageable pageable) {
    Page<Menu> page =
        menuRepository.findByRoleIdsAndTypeNotByPage(
            roleIds, Objects.isNull(type) ? Integer.MAX_VALUE : type, pageable);
    return ConvertUtil.toPageVo(page, menuMapper);
  }

  public List<MenuDto> buildTree(List<MenuDto> menuDtos) {
    List<MenuDto> trees = new ArrayList<>();
    Set<Long> ids = new HashSet<>();
    for (MenuDto menuDTO : menuDtos) {
      if (menuDTO.getPid() == null) {
        trees.add(menuDTO);
      }
      for (MenuDto it : menuDtos) {
        if (menuDTO.getId().equals(it.getPid())) {
          if (menuDTO.getChildren() == null) {
            menuDTO.setChildren(new ArrayList<>());
          }
          menuDTO.getChildren().add(it);
          ids.add(it.getId());
        }
      }
    }
    if (trees.size() == 0) {
      trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
    }
    return trees;
  }

  @Override
  public PageVo<MenuDto> queryAll(MenuQueryCriteria criteria, Pageable pageable) {
    Page<Menu> page =
        menuRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) ->
                QueryHelper.getPredicate(root, criteria, criteriaBuilder),
            pageable);
    return ConvertUtil.toPageVo(page, menuMapper);
  }

  @Override
  public List<MenuDto> queryAll(MenuQueryCriteria criteria) {
    List<Menu> menus =
        menuRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) ->
                QueryHelper.getPredicate(root, criteria, criteriaBuilder));
    return menuMapper.toDto(menus);
  }

  @Override
  public List<MenuDto> queryMenuChild(Long pid) {

    return menuMapper.toDto(menuRepository.findByPid(pid));
  }

  public List<MenuVo> buildMenu(List<MenuDto> menuDtos) {
    List<MenuVo> menus = Lists.newArrayList();
    menuDtos.forEach(
        menuDto -> {
          if (menuDto != null) {
            List<MenuDto> children = menuDto.getChildren();
            MenuVo menuVo = new MenuVo();
            menuVo.setName(
                StringUtils.isNotEmpty(menuDto.getComponentName())
                    ? menuDto.getComponentName()
                    : menuDto.getTitle());
            // 一级目录需要加斜杠，不然会报警告
            menuVo.setPath(menuDto.getPid() == null ? "/" + menuDto.getPath() : menuDto.getPath());
            menuVo.setHidden(menuDto.getHidden());

            if (!menuDto.getIFrame()) {
              if (menuDto.getPid() == null) {
                menuVo.setComponent(
                    StringUtils.isEmpty(menuDto.getComponent())
                        ? "Layout"
                        : menuDto.getComponent());
                // 如果不是一级菜单，并且菜单类型为目录，则代表是多级菜单
              } else if (menuDto.getType() == 0) {
                menuVo.setComponent(
                    StringUtils.isEmpty(menuDto.getComponent())
                        ? "ParentView"
                        : menuDto.getComponent());
              } else if (StringUtils.isNotEmpty(menuDto.getComponent())) {
                menuVo.setComponent(menuDto.getComponent());
              }
            }
            menuVo.setMeta(
                new MenuMetaVo()
                    .setTitle(menuDto.getTitle())
                    .setIcon(menuDto.getIcon())
                    .setNoCache(!menuDto.getCache()));
            if (CollectionUtils.isNotEmpty(children)) {
              menuVo.setAlwaysShow(true);
              menuVo.setRedirect("noredirect");
              menuVo.setChildren(buildMenu(children));
              // 处理是一级菜单并且没有子菜单的情况
            } else if (menuDto.getPid() == null) {
              MenuVo menuVo1 = new MenuVo();
              menuVo1.setMeta(menuVo.getMeta());
              // 非外链
              if (!menuDto.getIFrame()) {
                menuVo1.setPath("index");
                menuVo1.setName(menuVo.getName());
                menuVo1.setComponent(menuVo.getComponent());
              } else {
                menuVo1.setPath(menuDto.getPath());
              }
              menuVo.setName(null);
              menuVo.setMeta(null);
              menuVo.setComponent("Layout");
              List<MenuVo> list1 = new ArrayList<>();
              list1.add(menuVo1);
              menuVo.setChildren(list1);
            }
            menus.add(menuVo);
          }
        });

    return menus;
  }
}
