SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级菜单ID',
  `sub_count` int(5) DEFAULT '0' COMMENT '子菜单数目',
  `type` int(11) DEFAULT NULL COMMENT '菜单类型',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单标题',
  `name` varchar(255) DEFAULT NULL COMMENT '组件名称',
  `component` varchar(255) DEFAULT NULL COMMENT '组件',
  `menu_sort` int(5) DEFAULT NULL COMMENT '排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `i_frame` bit(1) DEFAULT NULL COMMENT '是否外链',
  `cache` bit(1) DEFAULT b'0' COMMENT '缓存',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新者',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE KEY `uniq_title` (`title`),
  UNIQUE KEY `uniq_name` (`name`),
  KEY `inx_pid` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', null, '7', '0', '系统管理', null, null, '1', 'el-icon-setting', 'system', '\0', '\0', '\0', null, null, null, '2018-12-18 15:11:29', null);
INSERT INTO `sys_menu` VALUES ('2', '1', '3', '1', '用户管理', 'User', 'system/user/index', '2', 'peoples', 'user', '\0', '\0', '\0', 'user:list', null, null, '2018-12-18 15:14:44', null);
INSERT INTO `sys_menu` VALUES ('3', '1', '3', '1', '角色管理', 'Role', 'system/role/index', '3', 'role', 'role', '\0', '\0', '\0', 'roles:list', null, null, '2018-12-18 15:16:07', null);
INSERT INTO `sys_menu` VALUES ('5', '1', '3', '1', '菜单管理', 'Menu', 'system/menu/index', '5', 'menu', 'menu', '\0', '\0', '\0', 'menu:list', null, null, '2018-12-18 15:17:28', null);
INSERT INTO `sys_menu` VALUES ('6', null, '5', '0', '系统监控', null, null, '10', 'monitor', 'monitor', '\0', '\0', '\0', null, null, null, '2018-12-18 15:17:48', null);
INSERT INTO `sys_menu` VALUES ('7', '6', '0', '1', '操作日志', 'Log', 'monitor/log/index', '11', 'log', 'logs', '\0', '', '\0', null, null, 'admin', '2018-12-18 15:18:26', '2020-06-06 13:11:57');
INSERT INTO `sys_menu` VALUES ('9', '6', '0', '1', 'SQL监控', 'Sql', 'monitor/sql/index', '18', 'sqlMonitor', 'druid', '\0', '\0', '\0', null, null, null, '2018-12-18 15:19:34', null);
INSERT INTO `sys_menu` VALUES ('10', null, '5', '0', '组件管理', null, null, '50', 'zujian', 'components', '\0', '\0', '\0', null, null, null, '2018-12-19 13:38:16', null);
INSERT INTO `sys_menu` VALUES ('11', '10', '0', '1', '图标库', 'Icons', 'components/icons/index', '51', 'icon', 'icon', '\0', '\0', '\0', null, null, null, '2018-12-19 13:38:49', null);
INSERT INTO `sys_menu` VALUES ('14', '36', '0', '1', '邮件工具', 'Email', 'tools/email/index', '35', 'email', 'email', '\0', '\0', '\0', null, null, null, '2018-12-27 10:13:09', null);
INSERT INTO `sys_menu` VALUES ('15', '10', '0', '1', '富文本', 'Editor', 'components/Editor', '52', 'fwb', 'tinymce', '\0', '\0', '\0', null, null, null, '2018-12-27 11:58:25', null);
INSERT INTO `sys_menu` VALUES ('18', '36', '3', '1', '存储管理', 'Storage', 'tools/storage/index', '34', 'qiniu', 'storage', '\0', '\0', '\0', 'storage:list', null, null, '2018-12-31 11:12:15', null);
INSERT INTO `sys_menu` VALUES ('19', '36', '0', '1', '支付宝工具', 'AliPay', 'tools/aliPay/index', '37', 'alipay', 'aliPay', '\0', '\0', '\0', null, null, null, '2018-12-31 14:52:38', null);
INSERT INTO `sys_menu` VALUES ('21', null, '2', '0', '多级菜单', null, '', '900', 'menu', 'nested', '\0', '\0', '\0', null, null, 'admin', '2019-01-04 16:22:03', '2020-06-21 17:27:35');
INSERT INTO `sys_menu` VALUES ('22', '21', '2', '0', '二级菜单1', null, '', '999', 'menu', 'menu1', '\0', '\0', '\0', null, null, 'admin', '2019-01-04 16:23:29', '2020-06-21 17:27:20');
INSERT INTO `sys_menu` VALUES ('23', '21', '0', '1', '二级菜单2', null, 'nested/menu2/index', '999', 'menu', 'menu2', '\0', '\0', '\0', null, null, null, '2019-01-04 16:23:57', null);
INSERT INTO `sys_menu` VALUES ('24', '22', '0', '1', '三级菜单1', 'Test', 'nested/menu1/menu1-1', '999', 'menu', 'menu1-1', '\0', '\0', '\0', null, null, null, '2019-01-04 16:24:48', null);
INSERT INTO `sys_menu` VALUES ('27', '22', '0', '1', '三级菜单2', null, 'nested/menu1/menu1-2', '999', 'menu', 'menu1-2', '\0', '\0', '\0', null, null, null, '2019-01-07 17:27:32', null);
INSERT INTO `sys_menu` VALUES ('28', '1', '3', '1', '任务调度', 'Timing', 'system/timing/index', '999', 'timing', 'timing', '\0', '\0', '\0', 'timing:list', null, null, '2019-01-07 20:34:40', null);
INSERT INTO `sys_menu` VALUES ('30', '36', '0', '1', '代码生成', 'GeneratorIndex', 'generator/index', '32', 'dev', 'generator', '\0', '', '\0', null, null, null, '2019-01-11 15:45:55', null);
INSERT INTO `sys_menu` VALUES ('32', '6', '0', '1', '异常日志', 'ErrorLog', 'monitor/log/errorLog', '12', 'error', 'errorLog', '\0', '\0', '\0', null, null, null, '2019-01-13 13:49:03', null);
INSERT INTO `sys_menu` VALUES ('33', '10', '0', '1', 'Markdown', 'Markdown', 'components/MarkDown', '53', 'markdown', 'markdown', '\0', '\0', '\0', null, null, null, '2019-03-08 13:46:44', null);
INSERT INTO `sys_menu` VALUES ('34', '10', '0', '1', 'Yaml编辑器', 'YamlEdit', 'components/YamlEdit', '54', 'dev', 'yaml', '\0', '\0', '\0', null, null, null, '2019-03-08 15:49:40', null);
INSERT INTO `sys_menu` VALUES ('35', '1', '3', '1', '部门管理', 'Dept', 'system/dept/index', '6', 'dept', 'dept', '\0', '\0', '\0', 'dept:list', null, null, '2019-03-25 09:46:00', null);
INSERT INTO `sys_menu` VALUES ('36', null, '7', '0', '系统工具', null, '', '30', 'sys-tools', 'sys-tools', '\0', '\0', '\0', null, null, null, '2019-03-29 10:57:35', null);
INSERT INTO `sys_menu` VALUES ('37', '1', '3', '1', '岗位管理', 'Job', 'system/job/index', '7', 'Steve-Jobs', 'job', '\0', '\0', '\0', 'job:list', null, null, '2019-03-29 13:51:18', null);
INSERT INTO `sys_menu` VALUES ('38', '36', '0', '1', '接口文档', 'Swagger', 'tools/swagger/index', '36', 'swagger', 'swagger2', '\0', '\0', '\0', null, null, null, '2019-03-29 19:57:53', null);
INSERT INTO `sys_menu` VALUES ('39', '1', '3', '1', '字典管理', 'Dict', 'system/dict/index', '8', 'dictionary', 'dict', '\0', '\0', '\0', 'dict:list', null, null, '2019-04-10 11:49:04', null);
INSERT INTO `sys_menu` VALUES ('41', '6', '0', '1', '在线用户', 'OnlineUser', 'monitor/online/index', '10', 'Steve-Jobs', 'online', '\0', '\0', '\0', null, null, null, '2019-10-26 22:08:43', null);
INSERT INTO `sys_menu` VALUES ('44', '2', '0', '2', '用户新增', null, '', '2', '', '', '\0', '\0', '\0', 'user:add', null, null, '2019-10-29 10:59:46', null);
INSERT INTO `sys_menu` VALUES ('45', '2', '0', '2', '用户编辑', null, '', '3', '', '', '\0', '\0', '\0', 'user:edit', null, null, '2019-10-29 11:00:08', null);
INSERT INTO `sys_menu` VALUES ('46', '2', '0', '2', '用户删除', null, '', '4', '', '', '\0', '\0', '\0', 'user:del', null, null, '2019-10-29 11:00:23', null);
INSERT INTO `sys_menu` VALUES ('48', '3', '0', '2', '角色创建', null, '', '2', '', '', '\0', '\0', '\0', 'roles:add', null, null, '2019-10-29 12:45:34', null);
INSERT INTO `sys_menu` VALUES ('49', '3', '0', '2', '角色修改', null, '', '3', '', '', '\0', '\0', '\0', 'roles:edit', null, null, '2019-10-29 12:46:16', null);
INSERT INTO `sys_menu` VALUES ('50', '3', '0', '2', '角色删除', null, '', '4', '', '', '\0', '\0', '\0', 'roles:del', null, null, '2019-10-29 12:46:51', null);
INSERT INTO `sys_menu` VALUES ('52', '5', '0', '2', '菜单新增', null, '', '2', '', '', '\0', '\0', '\0', 'menu:add', null, null, '2019-10-29 12:55:07', null);
INSERT INTO `sys_menu` VALUES ('53', '5', '0', '2', '菜单编辑', null, '', '3', '', '', '\0', '\0', '\0', 'menu:edit', null, null, '2019-10-29 12:55:40', null);
INSERT INTO `sys_menu` VALUES ('54', '5', '0', '2', '菜单删除', null, '', '4', '', '', '\0', '\0', '\0', 'menu:del', null, null, '2019-10-29 12:56:00', null);
INSERT INTO `sys_menu` VALUES ('56', '35', '0', '2', '部门新增', null, '', '2', '', '', '\0', '\0', '\0', 'dept:add', null, null, '2019-10-29 12:57:09', null);
INSERT INTO `sys_menu` VALUES ('57', '35', '0', '2', '部门编辑', null, '', '3', '', '', '\0', '\0', '\0', 'dept:edit', null, null, '2019-10-29 12:57:27', null);
INSERT INTO `sys_menu` VALUES ('58', '35', '0', '2', '部门删除', null, '', '4', '', '', '\0', '\0', '\0', 'dept:del', null, null, '2019-10-29 12:57:41', null);
INSERT INTO `sys_menu` VALUES ('60', '37', '0', '2', '岗位新增', null, '', '2', '', '', '\0', '\0', '\0', 'job:add', null, null, '2019-10-29 12:58:27', null);
INSERT INTO `sys_menu` VALUES ('61', '37', '0', '2', '岗位编辑', null, '', '3', '', '', '\0', '\0', '\0', 'job:edit', null, null, '2019-10-29 12:58:45', null);
INSERT INTO `sys_menu` VALUES ('62', '37', '0', '2', '岗位删除', null, '', '4', '', '', '\0', '\0', '\0', 'job:del', null, null, '2019-10-29 12:59:04', null);
INSERT INTO `sys_menu` VALUES ('64', '39', '0', '2', '字典新增', null, '', '2', '', '', '\0', '\0', '\0', 'dict:add', null, null, '2019-10-29 13:00:17', null);
INSERT INTO `sys_menu` VALUES ('65', '39', '0', '2', '字典编辑', null, '', '3', '', '', '\0', '\0', '\0', 'dict:edit', null, null, '2019-10-29 13:00:42', null);
INSERT INTO `sys_menu` VALUES ('66', '39', '0', '2', '字典删除', null, '', '4', '', '', '\0', '\0', '\0', 'dict:del', null, null, '2019-10-29 13:00:59', null);
INSERT INTO `sys_menu` VALUES ('73', '28', '0', '2', '任务新增', null, '', '2', '', '', '\0', '\0', '\0', 'timing:add', null, null, '2019-10-29 13:07:28', null);
INSERT INTO `sys_menu` VALUES ('74', '28', '0', '2', '任务编辑', null, '', '3', '', '', '\0', '\0', '\0', 'timing:edit', null, null, '2019-10-29 13:07:41', null);
INSERT INTO `sys_menu` VALUES ('75', '28', '0', '2', '任务删除', null, '', '4', '', '', '\0', '\0', '\0', 'timing:del', null, null, '2019-10-29 13:07:54', null);
INSERT INTO `sys_menu` VALUES ('77', '18', '0', '2', '上传文件', null, '', '2', '', '', '\0', '\0', '\0', 'storage:add', null, null, '2019-10-29 13:09:09', null);
INSERT INTO `sys_menu` VALUES ('78', '18', '0', '2', '文件编辑', null, '', '3', '', '', '\0', '\0', '\0', 'storage:edit', null, null, '2019-10-29 13:09:22', null);
INSERT INTO `sys_menu` VALUES ('79', '18', '0', '2', '文件删除', null, '', '4', '', '', '\0', '\0', '\0', 'storage:del', null, null, '2019-10-29 13:09:34', null);
INSERT INTO `sys_menu` VALUES ('80', '6', '0', '1', '服务监控', 'ServerMonitor', 'monitor/server/index', '14', 'codeConsole', 'server', '\0', '\0', '\0', 'monitor:list', null, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');
INSERT INTO `sys_menu` VALUES ('82', '36', '0', '1', '生成配置', 'GeneratorConfig', 'generator/config', '33', 'dev', 'generator/config/:tableName', '\0', '', '', '', null, null, '2019-11-17 20:08:56', null);
INSERT INTO `sys_menu` VALUES ('83', '10', '0', '1', '图表库', 'Echarts', 'components/Echarts', '50', 'chart', 'echarts', '\0', '', '\0', '', null, null, '2019-11-21 09:04:32', null);
INSERT INTO `sys_menu` VALUES ('90', null, '5', '1', '运维管理', 'Mnt', '', '20', 'mnt', 'mnt', '\0', '\0', '\0', null, null, null, '2019-11-09 10:31:08', null);
INSERT INTO `sys_menu` VALUES ('92', '90', '3', '1', '服务器', 'ServerDeploy', 'mnt/server/index', '22', 'server', 'mnt/serverDeploy', '\0', '\0', '\0', 'serverDeploy:list', null, null, '2019-11-10 10:29:25', null);
INSERT INTO `sys_menu` VALUES ('93', '90', '3', '1', '应用管理', 'App', 'mnt/app/index', '23', 'app', 'mnt/app', '\0', '\0', '\0', 'app:list', null, null, '2019-11-10 11:05:16', null);
INSERT INTO `sys_menu` VALUES ('94', '90', '3', '1', '部署管理', 'Deploy', 'mnt/deploy/index', '24', 'deploy', 'mnt/deploy', '\0', '\0', '\0', 'deploy:list', null, null, '2019-11-10 15:56:55', null);
INSERT INTO `sys_menu` VALUES ('97', '90', '1', '1', '部署备份', 'DeployHistory', 'mnt/deployHistory/index', '25', 'backup', 'mnt/deployHistory', '\0', '\0', '\0', 'deployHistory:list', null, null, '2019-11-10 16:49:44', null);
INSERT INTO `sys_menu` VALUES ('98', '90', '3', '1', '数据库管理', 'Database', 'mnt/database/index', '26', 'database', 'mnt/database', '\0', '\0', '\0', 'database:list', null, null, '2019-11-10 20:40:04', null);
INSERT INTO `sys_menu` VALUES ('102', '97', '0', '2', '删除', null, '', '999', '', '', '\0', '\0', '\0', 'deployHistory:del', null, null, '2019-11-17 09:32:48', null);
INSERT INTO `sys_menu` VALUES ('103', '92', '0', '2', '服务器新增', null, '', '999', '', '', '\0', '\0', '\0', 'serverDeploy:add', null, null, '2019-11-17 11:08:33', null);
INSERT INTO `sys_menu` VALUES ('104', '92', '0', '2', '服务器编辑', null, '', '999', '', '', '\0', '\0', '\0', 'serverDeploy:edit', null, null, '2019-11-17 11:08:57', null);
INSERT INTO `sys_menu` VALUES ('105', '92', '0', '2', '服务器删除', null, '', '999', '', '', '\0', '\0', '\0', 'serverDeploy:del', null, null, '2019-11-17 11:09:15', null);
INSERT INTO `sys_menu` VALUES ('106', '93', '0', '2', '应用新增', null, '', '999', '', '', '\0', '\0', '\0', 'app:add', null, null, '2019-11-17 11:10:03', null);
INSERT INTO `sys_menu` VALUES ('107', '93', '0', '2', '应用编辑', null, '', '999', '', '', '\0', '\0', '\0', 'app:edit', null, null, '2019-11-17 11:10:28', null);
INSERT INTO `sys_menu` VALUES ('108', '93', '0', '2', '应用删除', null, '', '999', '', '', '\0', '\0', '\0', 'app:del', null, null, '2019-11-17 11:10:55', null);
INSERT INTO `sys_menu` VALUES ('109', '94', '0', '2', '部署新增', null, '', '999', '', '', '\0', '\0', '\0', 'deploy:add', null, null, '2019-11-17 11:11:22', null);
INSERT INTO `sys_menu` VALUES ('110', '94', '0', '2', '部署编辑', null, '', '999', '', '', '\0', '\0', '\0', 'deploy:edit', null, null, '2019-11-17 11:11:41', null);
INSERT INTO `sys_menu` VALUES ('111', '94', '0', '2', '部署删除', null, '', '999', '', '', '\0', '\0', '\0', 'deploy:del', null, null, '2019-11-17 11:12:01', null);
INSERT INTO `sys_menu` VALUES ('112', '98', '0', '2', '数据库新增', null, '', '999', '', '', '\0', '\0', '\0', 'database:add', null, null, '2019-11-17 11:12:43', null);
INSERT INTO `sys_menu` VALUES ('113', '98', '0', '2', '数据库编辑', null, '', '999', '', '', '\0', '\0', '\0', 'database:edit', null, null, '2019-11-17 11:12:58', null);
INSERT INTO `sys_menu` VALUES ('114', '98', '0', '2', '数据库删除', null, '', '999', '', '', '\0', '\0', '\0', 'database:del', null, null, '2019-11-17 11:13:14', null);
INSERT INTO `sys_menu` VALUES ('116', '36', '0', '1', '生成预览', 'Preview', 'generator/preview', '999', 'java', 'generator/preview/:tableName', '\0', '', '', null, null, null, '2019-11-26 14:54:36', null);
INSERT INTO `sys_menu` VALUES ('117', '1', '0', '1', '系统一览', 'SystemManager', 'system/manager/index', '3', null, 'manager', '\0', '\0', '\0', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('118', '1', '0', '1', '模块管理', 'SystemModuleConfig', 'system/manager/module', '999', null, 'module', '\0', '\0', '\0', null, null, null, null, null);
