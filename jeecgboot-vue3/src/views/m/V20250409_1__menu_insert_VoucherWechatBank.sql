-- 注意：该页面对应的前台目录为views/m文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('2025040908254430180', NULL, '银行营销表', '/m/voucherWechatBankList', 'm/VoucherWechatBankList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2025-04-09 20:25:18', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025040908254430181', '2025040908254430180', '添加银行营销表', NULL, NULL, 0, NULL, NULL, 2, 'm:voucher_wechat_bank:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-09 20:25:18', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025040908254430182', '2025040908254430180', '编辑银行营销表', NULL, NULL, 0, NULL, NULL, 2, 'm:voucher_wechat_bank:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-09 20:25:18', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025040908254430183', '2025040908254430180', '删除银行营销表', NULL, NULL, 0, NULL, NULL, 2, 'm:voucher_wechat_bank:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-09 20:25:18', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025040908254430184', '2025040908254430180', '批量删除银行营销表', NULL, NULL, 0, NULL, NULL, 2, 'm:voucher_wechat_bank:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-09 20:25:18', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025040908254430185', '2025040908254430180', '导出excel_银行营销表', NULL, NULL, 0, NULL, NULL, 2, 'm:voucher_wechat_bank:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-09 20:25:18', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2025040908254430186', '2025040908254430180', '导入excel_银行营销表', NULL, NULL, 0, NULL, NULL, 2, 'm:voucher_wechat_bank:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2025-04-09 20:25:18', NULL, NULL, 0, 0, '1', 0);