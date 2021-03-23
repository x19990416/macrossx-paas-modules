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
package com.github.x19990416.mxpaas.module.common.vo;

import com.github.x19990416.mxpaas.module.common.base.BaseMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageVo<T> {
  private List<T> contents;
  private Long total;
  private int page;
  private int size;

  public static <D, E> PageVo<D> toPageVo(Page<E> page, BaseMapper<D, E> mapper) {
    return new PageVo<D>()
        .setContents(mapper.toDto(page.getContent()))
        .setTotal(page.getTotalElements())
        .setPage(page.getNumber())
        .setSize(page.getSize());
  }
}
