/** create by Guo Limin on 2021/2/14. */
package com.github.x19990416.mxpaas.module.menu.domain.dto;

import com.github.x19990416.mxpaas.module.jpa.annotation.Query;
import lombok.Data;

@Data
public class MenuQueryCriteria {
  @Query private Long id;

  @Query(blurry = "title")
  private String blurry;

  @Query(type = Query.Type.EQUAL, propName = "type")
  private Integer type;

  @Query private Long pid;

  @Query(type = Query.Type.IS_NULL, propName = "pid")
  private Boolean isRoot;
}
