SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`menu_id`,`role_id`) USING BTREE,
  KEY `FKcngg2qadojhi3a651a5adkvbq` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色菜单关联';

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
INSERT INTO `sys_roles_menus` VALUES ('1', '1');
INSERT INTO `sys_roles_menus` VALUES ('2', '1');
INSERT INTO `sys_roles_menus` VALUES ('5', '1');
INSERT INTO `sys_roles_menus` VALUES ('117', '1');
INSERT INTO `sys_roles_menus` VALUES ('118', '1');
INSERT INTO `sys_roles_menus` VALUES ('1', '2');
INSERT INTO `sys_roles_menus` VALUES ('2', '2');
INSERT INTO `sys_roles_menus` VALUES ('6', '2');
INSERT INTO `sys_roles_menus` VALUES ('7', '2');
INSERT INTO `sys_roles_menus` VALUES ('9', '2');
INSERT INTO `sys_roles_menus` VALUES ('10', '2');
INSERT INTO `sys_roles_menus` VALUES ('11', '2');
INSERT INTO `sys_roles_menus` VALUES ('14', '2');
INSERT INTO `sys_roles_menus` VALUES ('15', '2');
INSERT INTO `sys_roles_menus` VALUES ('19', '2');
INSERT INTO `sys_roles_menus` VALUES ('21', '2');
INSERT INTO `sys_roles_menus` VALUES ('22', '2');
INSERT INTO `sys_roles_menus` VALUES ('23', '2');
INSERT INTO `sys_roles_menus` VALUES ('24', '2');
INSERT INTO `sys_roles_menus` VALUES ('27', '2');
INSERT INTO `sys_roles_menus` VALUES ('30', '2');
INSERT INTO `sys_roles_menus` VALUES ('32', '2');
INSERT INTO `sys_roles_menus` VALUES ('33', '2');
INSERT INTO `sys_roles_menus` VALUES ('34', '2');
INSERT INTO `sys_roles_menus` VALUES ('36', '2');
INSERT INTO `sys_roles_menus` VALUES ('80', '2');
INSERT INTO `sys_roles_menus` VALUES ('82', '2');
INSERT INTO `sys_roles_menus` VALUES ('83', '2');
INSERT INTO `sys_roles_menus` VALUES ('116', '2');
