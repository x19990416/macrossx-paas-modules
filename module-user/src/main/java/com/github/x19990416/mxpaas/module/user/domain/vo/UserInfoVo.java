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
package com.github.x19990416.mxpaas.module.user.domain.vo;

import com.github.x19990416.mxpaas.module.menu.domain.vo.MenuVo;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class UserInfoVo {
  private Set<String> roles;

  @NotEmpty(message = "用户名不能为空")
  private String username;

  @NotEmpty(message = "姓名不能为空")
  private String nickname;

  private String avatar;
  private List<MenuVo> menus;
}
