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
package com.github.x19990416.mxpaas.module.menu.domain;

import com.github.x19990416.mxpaas.module.jpa.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sys_menu")
@EqualsAndHashCode(callSuper = false)
@DynamicUpdate()
public class Menu extends BaseEntity implements Serializable {

  @Id
  @Column(name = "menu_id")
  @NotNull(groups = {Update.class})
  @Schema(name = "ID", hidden = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  /*
  @JSONField(serialize = false)
  @ManyToMany(mappedBy = "menus",fetch = FetchType.EAGER)
  @Schema(name = "菜单角色")
  private Set<Role> roles;*/

  @Schema(name = "菜单标题")
  private String title;

  @Column(name = "name")
  @Schema(name = "菜单组件名称")
  private String componentName;

  @Schema(name = "排序")
  private Integer menuSort = 999;

  @Schema(name = "组件路径")
  private String component;

  @Schema(name = "路由地址")
  private String path;

  @Schema(name = "菜单类型，目录、菜单、按钮")
  private Integer type;

  @Schema(name = "权限标识")
  private String permission;

  @Schema(name = "菜单图标")
  private String icon;

  @Column(columnDefinition = "bit(1) default 0")
  @Schema(name = "缓存")
  private Boolean cache;

  @Column(columnDefinition = "bit(1) default 0")
  @Schema(name = "是否隐藏")
  private Boolean hidden;

  @Schema(name = "上级菜单")
  private Long pid;

  @Schema(name = "子节点数目", hidden = true)
  private Integer subCount = 0;

  @Schema(name = "外链菜单")
  private Boolean iFrame;
}
