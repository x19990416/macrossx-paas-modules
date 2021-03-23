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
package com.github.x19990416.mxpaas.module.auth.domain;

import com.github.x19990416.mxpaas.module.jpa.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "sys_role")
@EqualsAndHashCode(callSuper = false)
@DynamicUpdate()
public class AuthRole extends BaseEntity implements Serializable {

  @Id
  @Column(name = "role_id")
  @NotNull(groups = {Update.class})
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name = "ID", hidden = true)
  private Long id;

  @NotBlank
  private String levelName;

  @NotBlank
  @Schema(name = "名称", hidden = true)
  private String name;

  @Schema(name = "数据权限，全部 、 本级 、 自定义")
  private String dataScope;

  @Column(name = "level")
  @Schema(name = "级别，数值越小，级别越大")
  private Integer level;

  @Schema(name = "描述")
  private String description;
}
