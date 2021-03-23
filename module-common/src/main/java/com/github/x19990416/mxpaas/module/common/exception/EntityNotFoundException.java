/** create by Guo Limin on 2021/1/30. */
package com.github.x19990416.mxpaas.module.common.exception;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(Class<?> clazz, String field, Object val) {
    super(
        EntityNotFoundException.generateMessage(
            clazz.getSimpleName(), field, Objects.isNull(val) ? "null" : val.toString()));
  }

  private static String generateMessage(String entity, String field, String val) {
    return StringUtils.capitalize(entity) + " with " + field + " " + val + " does not exist";
  }
}
