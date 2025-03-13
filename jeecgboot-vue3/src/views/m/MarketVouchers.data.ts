import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { JVxeTypes, JVxeColumn } from '/@/components/jeecg/JVxeTable/types';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '批次名称',
    align: 'center',
    dataIndex: 'name',
  },
  {
    title: '批次备注',
    align: 'center',
    dataIndex: 'remark',
  },
  {
    title: '活动平台',
    align: 'center',
    dataIndex: 'platform_dictText',
  },
  {
    title: '开始时间',
    align: 'center',
    dataIndex: 'startTime',
  },
  {
    title: '结束时间',
    align: 'center',
    dataIndex: 'endTime',
  },
  {
    title: '状态',
    align: 'center',
    dataIndex: 'status_dictText',
  },
  {
    title: '平台配置参数',
    align: 'center',
    dataIndex: 'params',
  },
  {
    title: '规则配置ID',
    align: 'center',
    dataIndex: 'settingId',
  },
  {
    title: '规则配置表',
    align: 'center',
    dataIndex: 'settingTable',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '批次名称',
    field: 'name',
    component: 'Input',
  },
  {
    label: '批次备注',
    field: 'remark',
    component: 'Input',
  },
  {
    label: '活动平台',
    field: 'platform',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'merchant_platfrom',
    },
  },
  {
    label: '开始时间',
    field: 'startTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'vouchers_status',
    },
  },
  {
    label: '平台配置参数',
    field: 'params',
    component: 'Input',
    dynamicDisabled: true,
  },
  {
    label: '规则配置ID',
    field: 'settingId',
    component: 'Input',
    dynamicDisabled: true,
  },
  {
    label: '规则配置表',
    field: 'settingTable',
    component: 'Input',
    dynamicDisabled: true,
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];
//子表单数据
//子表表格配置
export const marketVouchersMerchantsColumns: JVxeColumn[] = [
  {
    title: '商户信息',
    key: 'merchantId',
    type: JVxeTypes.select,
    options: [],
    dictCode: 'market_merchant,name,id',
    width: '200px',
    placeholder: '请输入${title}',
    defaultValue: '',
  },
];

// 高级查询数据
export const superQuerySchema = {
  name: { title: '批次名称', order: 0, view: 'text', type: 'string' },
  remark: { title: '批次备注', order: 1, view: 'text', type: 'string' },
  platform: { title: '活动平台', order: 2, view: 'list', type: 'string', dictCode: 'merchant_platfrom' },
  startTime: { title: '开始时间', order: 3, view: 'datetime', type: 'string' },
  endTime: { title: '结束时间', order: 4, view: 'datetime', type: 'string' },
  status: { title: '状态', order: 5, view: 'list', type: 'string', dictCode: 'vouchers_status' },
  params: { title: '平台配置参数', order: 6, view: 'text', type: 'string' },
  settingId: { title: '规则配置ID', order: 7, view: 'text', type: 'string' },
  settingTable: { title: '规则配置表', order: 8, view: 'text', type: 'string' },
  //子表高级查询
  marketVouchersMerchants: {
    title: '代金券场景归属商户',
    view: 'table',
    fields: {
      merchantId: { title: '商户信息', order: 0, view: 'list', type: 'string', dictTable: 'market_merchant', dictCode: 'id', dictText: 'name' },
    },
  },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
