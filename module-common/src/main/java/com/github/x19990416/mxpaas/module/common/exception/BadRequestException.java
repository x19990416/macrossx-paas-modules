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
package com.github.x19990416.mxpaas.module.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {

  private Integer status = BAD_REQUEST.value();

  public BadRequestException(Throwable throwable,Integer status) {
    super(throwable);
    this.status = status;
  }

  public BadRequestException(String msg) {
    super(msg);
  }

  public BadRequestException(String msg, Integer status) {
    super(msg);
    this.status = status;
  }

  public BadRequestException(String msg, Throwable throwable, Integer status) {
    super(msg, throwable);
    this.status = status;
  }
}
