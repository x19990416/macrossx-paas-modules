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
package com.github.x19990416.mxpaas.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataScopeEnum {

	/* 全部的数据权限 */
	ALL("全部", "全部的数据权限"),

	/* 自己部门的数据权限 */
	THIS_LEVEL("本级", "自己层面的数据权限"),

	/* 自定义的数据权限 */
	CUSTOMIZE("自定义", "自定义的数据权限");

	private final String value;
	private final String description;

	public static DataScopeEnum find(String val) {
		for (DataScopeEnum dataScopeEnum : DataScopeEnum.values()) {
			if (val.equals(dataScopeEnum.getValue())) {
				return dataScopeEnum;
			}
		}
		return null;
	}

}
