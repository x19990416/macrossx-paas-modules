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
package com.github.x19990416.mxpaas.module.menu;

import com.github.x19990416.mxpaas.module.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@Slf4j
public class MenuController {

	private final MenuService menuService;

//	@GetMapping("/query")
//	public ResponseEntity<Object> query(MenuQueryCriteria criteria, Pageable pageable) {
//		return ResponseEntity.ok(menuService.queryAll(criteria, pageable));
//	}
//
//	@GetMapping("/build")
//	public ResponseEntity<Object> build(MenuQueryCriteria criteria) {
//		List<MenuDto> menus = menuService.queryAll(criteria);
//		menus.sort(Comparator.comparing(MenuDto::getMenuSort));
//		return ResponseEntity.ok(menuService.buildTree(menus));
//	}
//
//	@GetMapping("/child")
//	public ResponseEntity<Object> child(Long pid) {
//		List<MenuDto> menus = menuService.queryMenuChild(pid);
//		return ResponseEntity.ok(menuService.buildTree(menus));
//	}
//
//	@GetMapping("/query/role")
//	public ResponseEntity<Object> queryByRole(Long roleId, Pageable pageable) {
//		log.info("{}-{}-{}", roleId, pageable, "queryByRole");
//		return ResponseEntity.ok(menuService.findByRole(List.of(roleId), null, pageable));
//	}
}
