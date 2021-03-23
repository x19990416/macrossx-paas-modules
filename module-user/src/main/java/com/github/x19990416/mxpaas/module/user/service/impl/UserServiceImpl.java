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
package com.github.x19990416.mxpaas.module.user.service.impl;

import com.github.x19990416.mxpaas.module.common.exception.EntityExistException;
import com.github.x19990416.mxpaas.module.common.utils.ConvertUtil;
import com.github.x19990416.mxpaas.module.common.vo.PageVo;
import com.github.x19990416.mxpaas.module.jpa.QueryHelper;
import com.github.x19990416.mxpaas.module.user.domain.User;
import com.github.x19990416.mxpaas.module.user.domain.dto.UserDto;
import com.github.x19990416.mxpaas.module.user.domain.dto.UserMapper;
import com.github.x19990416.mxpaas.module.user.domain.dto.UserQueryCriteria;
import com.github.x19990416.mxpaas.module.user.repository.UserRepository;
import com.github.x19990416.mxpaas.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public PageVo<UserDto> queryAll(UserQueryCriteria criteria, Pageable pageable) {
    Page<User> page =
        userRepository.findAll(
            (root, criteriaQuery, criteriaBuilder) ->
                QueryHelper.getPredicate(root, criteria, criteriaBuilder),
            pageable);
    return ConvertUtil.toPageVo(page,userMapper);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createUser(UserDto resourceDto) {
    log.info("{}", resourceDto);
    if (!Objects.isNull(userRepository.findByUsername(resourceDto.getUsername()))) {
      throw new EntityExistException(UserDto.class, "username", resourceDto.getUsername());
    }
    userRepository.save(userMapper.toEntity(resourceDto));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteUser(Set<Long> ids) {
    // TODO: 添加权限相关判定
    userRepository.deleteAllByIdIn(ids);
  }
}
