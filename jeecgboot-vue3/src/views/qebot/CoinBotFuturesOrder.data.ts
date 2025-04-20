import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '交易对',
    align: 'center',
    dataIndex: 'symbol',
  },
  {
    title: '合约倍数',
    align: 'center',
    dataIndex: 'lever',
  },
  {
    title: '数量',
    align: 'center',
    dataIndex: 'num',
  },
  {
    title: '平仓盈亏',
    align: 'center',
    dataIndex: 'profit',
  },
  {
    title: '开仓价格',
    align: 'center',
    dataIndex: 'openPrice',
  },
  {
    title: '平仓价格',
    align: 'center',
    dataIndex: 'closePrice',
  },
  {
    title: '最大持仓',
    align: 'center',
    dataIndex: 'position',
  },
  {
    title: '已平仓数量',
    align: 'center',
    dataIndex: 'closePostion',
  },
  {
    title: '开仓时间',
    align: 'center',
    dataIndex: 'openTime',
  },
  {
    title: '最后平仓时间',
    align: 'center',
    dataIndex: 'closeTime',
  },
  {
    title: '所属机器人',
    align: 'center',
    dataIndex: 'botId_dictText',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '交易对',
    field: 'symbol',
    component: 'Input',
  },
  {
    label: '杠杆',
    field: 'lever',
    component: 'InputNumber',
  },
  {
    label: '数量',
    field: 'num',
    component: 'InputNumber',
  },
  {
    label: '平仓盈亏',
    field: 'profit',
    component: 'InputNumber',
  },
  {
    label: '开仓价格',
    field: 'openPrice',
    component: 'InputNumber',
  },
  {
    label: '平仓价格',
    field: 'closePrice',
    component: 'InputNumber',
  },
  {
    label: '最大持仓',
    field: 'position',
    component: 'InputNumber',
  },
  {
    label: '已平仓数量',
    field: 'closePostion',
    component: 'InputNumber',
  },
  {
    label: '开仓时间',
    field: 'openTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '最后平仓时间',
    field: 'closeTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '所属机器人',
    field: 'botId',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'coin_bot,instance_name,id',
    },
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
  symbol: { title: '交易对', order: 0, view: 'text', type: 'string' },
  lever: { title: '合约倍数', order: 1, view: 'number', type: 'number' },
  num: { title: '数量', order: 2, view: 'number', type: 'number' },
  profit: { title: '平仓盈亏', order: 3, view: 'number', type: 'number' },
  openPrice: { title: '开仓价格', order: 4, view: 'number', type: 'number' },
  closePrice: { title: '平仓价格', order: 5, view: 'number', type: 'number' },
  position: { title: '最大持仓', order: 6, view: 'number', type: 'number' },
  closePostion: { title: '已平仓数量', order: 7, view: 'number', type: 'number' },
  openTime: { title: '开仓时间', order: 8, view: 'datetime', type: 'string' },
  closeTime: { title: '最后平仓时间', order: 9, view: 'datetime', type: 'string' },
  botId: { title: '所属机器人', order: 10, view: 'list', type: 'string', dictTable: 'coin_bot', dictCode: 'id', dictText: 'instance_name' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
