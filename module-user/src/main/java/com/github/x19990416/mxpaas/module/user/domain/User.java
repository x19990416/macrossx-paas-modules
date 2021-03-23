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
package com.github.x19990416.mxpaas.module.user.domain;

import com.github.x19990416.mxpaas.module.auth.domain.AuthRole;
import com.github.x19990416.mxpaas.module.jpa.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "sys_user")
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate()
public class User extends BaseEntity implements Serializable {

  @Id
  @Column(name = "user_id")
  @NotNull(groups = Update.class)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(name = "ID", hidden = true)
  private Long id;

  @ManyToMany()
  @Schema(name = "用户角色")
  @JoinTable(
      name = "sys_users_roles",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
  private Set<AuthRole> roles;
  /*
  @ManyToMany
  @Schema(name = "用户岗位")
  @JoinTable(
      name = "sys_users_jobs",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "job_id", referencedColumnName = "job_id")})
  private Set<Job> jobs;*/
  /*
   @OneToOne
   @JoinColumn(name = "dept_id")
   @Schema(name = "用户部门")
   private Dept dept;
  */

  @NotBlank
  @Column(unique = true)
  @Schema(name = "用户名称")
  private String username;

  @NotBlank
  @Schema(name = "用户昵称")
  private String nickName;

  @Email
  @NotBlank
  @Schema(name = "邮箱")
  private String email;

  @NotBlank
  @Schema(name = "电话号码")
  private String phone;

  @Schema(name = "用户性别")
  private String gender;

  @Schema(name = "头像真实名称", hidden = true)
  private String avatarName;

  @Schema(name = "头像存储的路径", hidden = true)
  private String avatarPath;

  @Schema(name = "密码")
  private String password;

  @NotNull
  @Schema(name = "是否启用")
  private Boolean enabled;

  @Schema(name = "是否为admin账号", hidden = true)
  private Boolean isAdmin = false;

  @Column(name = "pwd_reset_time")
  @Schema(name = "最后修改密码的时间", hidden = true)
  private Date pwdResetTime;
}
