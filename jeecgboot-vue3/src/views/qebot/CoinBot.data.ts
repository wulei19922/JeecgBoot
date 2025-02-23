import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '算法类型',
    align: 'center',
    dataIndex: 'categoryType_dictText',
  },
  {
    title: '用户',
    align: 'center',
    dataIndex: 'memberId_dictText',
  },
  {
    title: '当前状态',
    align: 'center',
    dataIndex: 'status_dictText',
  },
  {
    title: '类型',
    align: 'center',
    dataIndex: 'type',
  },
  {
    title: '开启状态',
    align: 'center',
    dataIndex: 'openStatus_dictText',
  },
  {
    title: '总投资额',
    align: 'center',
    dataIndex: 'totalInvest',
  },
  {
    title: '交易对',
    align: 'center',
    dataIndex: 'symbol_dictText',
  },
  {
    title: '收益',
    align: 'center',
    dataIndex: 'income',
  },
  {
    title: '网格收益',
    align: 'center',
    dataIndex: 'incomeGride',
  },
  {
    title: '净利润',
    align: 'center',
    dataIndex: 'profit',
  },
  {
    title: '配对次数',
    align: 'center',
    dataIndex: 'matchNum',
  },
  {
    title: '区间最大价格',
    align: 'center',
    dataIndex: 'maxPrice',
  },
  {
    title: '区间最小价格',
    align: 'center',
    dataIndex: 'minPrice',
  },
  {
    title: '网格数量',
    align: 'center',
    dataIndex: 'grideNum',
  },
  {
    title: '单次交易数量',
    align: 'center',
    dataIndex: 'perOrder',
  },
  {
    title: '机器人节点机器',
    align: 'center',
    dataIndex: 'nodeName',
  },
  {
    title: '节点IP',
    align: 'center',
    dataIndex: 'nodeIp',
  },
  {
    title: '实例名',
    align: 'center',
    dataIndex: 'instanceName',
  },
  {
    title: '单网格利润率',
    align: 'center',
    dataIndex: 'grideProfit',
  },
  {
    title: '环境',
    align: 'center',
    dataIndex: 'env',
  },
  {
    title: '持仓',
    align: 'center',
    dataIndex: 'positions',
  },
  {
    title: '初始买入资金比例',
    align: 'center',
    dataIndex: 'initRate',
  },
  {
    title: '机器人异常信息',
    align: 'center',
    dataIndex: 'errmsg',
  },
  {
    title: '止损比例',
    align: 'center',
    dataIndex: 'stopLoss',
  },
  {
    title: '减仓初始价格',
    align: 'center',
    dataIndex: 'basePrice',
  },
  {
    title: '网格配置',
    align: 'center',
    dataIndex: 'gridConfig',
  },
  {
    title: '合约杠杆',
    align: 'center',
    dataIndex: 'lever',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '算法类型',
    field: 'categoryType',
    component: 'JSelectMultiple',
    componentProps: {
      dictCode: 'qe_bot_type',
    },
    //colProps: {span: 6},
  },
  {
    label: '用户',
    field: 'memberId',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'sys_user,username,id',
    },
    //colProps: {span: 6},
  },
  {
    label: '当前状态',
    field: 'status',
    component: 'JSelectMultiple',
    componentProps: {
      dictCode: 'bot_status',
    },
    //colProps: {span: 6},
  },
  {
    label: '类型',
    field: 'type',
    component: 'Input',
    //colProps: {span: 6},
  },
  {
    label: '开启状态',
    field: 'openStatus',
    component: 'JSelectMultiple',
    componentProps: {
      dictCode: 'qot_status',
    },
    //colProps: {span: 6},
  },
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '算法类型',
    field: 'categoryType',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'qe_bot_type',
    },
  },
  {
    label: '用户',
    field: 'memberId',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'sys_user,username,id',
    },
  },
  {
    label: '当前状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'bot_status',
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入当前状态!' }];
    },
  },
  {
    label: '类型',
    field: 'type',
    component: 'Input',
  },
  {
    label: '开启状态',
    field: 'openStatus',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'qot_status',
    },
  },
  {
    label: '总投资额',
    field: 'totalInvest',
    component: 'InputNumber',
  },
  {
    label: '交易对',
    field: 'symbol',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'coin_support,symbol,symbol',
    },
  },
  {
    label: '收益',
    field: 'income',
    component: 'InputNumber',
    dynamicDisabled: true,
  },
  {
    label: '网格收益',
    field: 'incomeGride',
    component: 'InputNumber',
    dynamicDisabled: true,
  },
  {
    label: '净利润',
    field: 'profit',
    component: 'InputNumber',
    dynamicDisabled: true,
  },
  {
    label: '配对次数',
    field: 'matchNum',
    component: 'InputNumber',
    dynamicDisabled: true,
  },
  {
    label: '区间最大价格',
    field: 'maxPrice',
    component: 'InputNumber',
  },
  {
    label: '区间最小价格',
    field: 'minPrice',
    component: 'InputNumber',
  },
  {
    label: '网格数量',
    field: 'grideNum',
    component: 'InputNumber',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入网格数量!' }];
    },
  },
  {
    label: '单次交易数量',
    field: 'perOrder',
    component: 'InputNumber',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入单次交易数量!' }];
    },
  },
  {
    label: '机器人节点机器',
    field: 'nodeName',
    component: 'Input',
  },
  {
    label: '节点IP',
    field: 'nodeIp',
    component: 'Input',
    dynamicDisabled: true,
  },
  {
    label: '实例名',
    field: 'instanceName',
    component: 'Input',
    dynamicDisabled: true,
  },
  {
    label: '单网格利润率',
    field: 'grideProfit',
    component: 'InputNumber',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入单网格利润率!' }];
    },
  },
  {
    label: '环境',
    field: 'env',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入环境!' }];
    },
  },
  {
    label: '持仓',
    field: 'positions',
    component: 'InputNumber',
    dynamicDisabled: true,
  },
  {
    label: '初始买入资金比例',
    field: 'initRate',
    component: 'InputNumber',
  },
  {
    label: '机器人异常信息',
    field: 'errmsg',
    component: 'Input',
  },
  {
    label: '止损比例',
    field: 'stopLoss',
    component: 'InputNumber',
  },
  {
    label: '减仓初始价格',
    field: 'basePrice',
    component: 'InputNumber',
  },
  {
    label: '网格配置',
    field: 'gridConfig',
    component: 'Input',
  },
  {
    label: '合约杠杆',
    field: 'lever',
    component: 'InputNumber',
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];

// 高级查询数据
export const superQuerySchema = {
  categoryType: { title: '算法类型', order: 0, view: 'list', type: 'string', dictCode: 'qe_bot_type' },
  memberId: { title: '用户', order: 1, view: 'sel_search', type: 'string', dictTable: 'sys_user', dictCode: 'id', dictText: 'username' },
  status: { title: '当前状态', order: 2, view: 'list', type: 'string', dictCode: 'bot_status' },
  type: { title: '类型', order: 3, view: 'text', type: 'string' },
  openStatus: { title: '开启状态', order: 4, view: 'number', type: 'number', dictCode: 'qot_status' },
  totalInvest: { title: '总投资额', order: 5, view: 'number', type: 'number' },
  symbol: { title: '交易对', order: 6, view: 'list', type: 'string', dictTable: 'coin_support', dictCode: 'symbol', dictText: 'symbol' },
  income: { title: '收益', order: 7, view: 'number', type: 'number' },
  incomeGride: { title: '网格收益', order: 8, view: 'number', type: 'number' },
  profit: { title: '净利润', order: 9, view: 'number', type: 'number' },
  matchNum: { title: '配对次数', order: 10, view: 'number', type: 'number' },
  maxPrice: { title: '区间最大价格', order: 11, view: 'number', type: 'number' },
  minPrice: { title: '区间最小价格', order: 12, view: 'number', type: 'number' },
  grideNum: { title: '网格数量', order: 13, view: 'number', type: 'number' },
  perOrder: { title: '单次交易数量', order: 14, view: 'number', type: 'number' },
  nodeName: { title: '机器人节点机器', order: 15, view: 'text', type: 'string' },
  nodeIp: { title: '节点IP', order: 16, view: 'text', type: 'string' },
  instanceName: { title: '实例名', order: 17, view: 'text', type: 'string' },
  grideProfit: { title: '单网格利润率', order: 18, view: 'number', type: 'number' },
  env: { title: '环境', order: 19, view: 'text', type: 'string' },
  positions: { title: '持仓', order: 20, view: 'number', type: 'number' },
  initRate: { title: '初始买入资金比例', order: 21, view: 'number', type: 'number' },
  errmsg: { title: '机器人异常信息', order: 22, view: 'text', type: 'string' },
  stopLoss: { title: '止损比例', order: 23, view: 'number', type: 'number' },
  basePrice: { title: '减仓初始价格', order: 24, view: 'number', type: 'number' },
  gridConfig: { title: '网格配置', order: 25, view: 'text', type: 'string' },
  lever: { title: '合约杠杆', order: 26, view: 'number', type: 'number' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
