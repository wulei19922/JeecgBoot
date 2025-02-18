import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
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
    title: '用户',
    align: 'center',
    dataIndex: 'memberId_dictText',
  },
  {
    title: '交易对',
    align: 'center',
    dataIndex: 'symbol_dictText',
  },
  {
    title: '总投资额',
    align: 'center',
    dataIndex: 'totalInvest',
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
];
//查询数据
export const searchFormSchema: FormSchema[] = [
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
  {
    label: '用户',
    field: 'memberId',
    component: 'JSelectMultiple',
    componentProps: {
      dictCode: 'li_member,username,id',
    },
    //colProps: {span: 6},
  },
];
//表单数据
export const formSchema: FormSchema[] = [
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
    label: '用户',
    field: 'memberId',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'li_member,username,id',
    },
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
    label: '总投资额',
    field: 'totalInvest',
    component: 'InputNumber',
  },
  {
    label: '收益',
    field: 'income',
    component: 'InputNumber',
  },
  {
    label: '网格收益',
    field: 'incomeGride',
    component: 'InputNumber',
  },
  {
    label: '净利润',
    field: 'profit',
    component: 'InputNumber',
  },
  {
    label: '配对次数',
    field: 'matchNum',
    component: 'InputNumber',
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
  },
  {
    label: '实例名',
    field: 'instanceName',
    component: 'Input',
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
  status: { title: '当前状态', order: 0, view: 'list', type: 'string', dictCode: 'bot_status' },
  type: { title: '类型', order: 1, view: 'text', type: 'string' },
  openStatus: { title: '开启状态', order: 2, view: 'number', type: 'number', dictCode: 'qot_status' },
  memberId: { title: '用户', order: 3, view: 'list', type: 'string', dictTable: 'li_member', dictCode: 'id', dictText: 'username' },
  symbol: { title: '交易对', order: 4, view: 'list', type: 'string', dictTable: 'coin_support', dictCode: 'symbol', dictText: 'symbol' },
  totalInvest: { title: '总投资额', order: 5, view: 'number', type: 'number' },
  income: { title: '收益', order: 6, view: 'number', type: 'number' },
  incomeGride: { title: '网格收益', order: 7, view: 'number', type: 'number' },
  profit: { title: '净利润', order: 8, view: 'number', type: 'number' },
  matchNum: { title: '配对次数', order: 9, view: 'number', type: 'number' },
  maxPrice: { title: '区间最大价格', order: 10, view: 'number', type: 'number' },
  minPrice: { title: '区间最小价格', order: 11, view: 'number', type: 'number' },
  grideNum: { title: '网格数量', order: 12, view: 'number', type: 'number' },
  perOrder: { title: '单次交易数量', order: 13, view: 'number', type: 'number' },
  nodeName: { title: '机器人节点机器', order: 14, view: 'text', type: 'string' },
  nodeIp: { title: '节点IP', order: 15, view: 'text', type: 'string' },
  instanceName: { title: '实例名', order: 16, view: 'text', type: 'string' },
  grideProfit: { title: '单网格利润率', order: 17, view: 'number', type: 'number' },
  env: { title: '环境', order: 18, view: 'text', type: 'string' },
  positions: { title: '持仓', order: 19, view: 'number', type: 'number' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
