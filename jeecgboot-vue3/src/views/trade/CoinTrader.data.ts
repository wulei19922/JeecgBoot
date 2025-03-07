import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '支付货币',
    align: 'center',
    dataIndex: 'symbol',
  },
  {
    title: '支付金额',
    align: 'center',
    dataIndex: 'paymount',
  },
  {
    title: '支付交易所',
    align: 'center',
    dataIndex: 'exchange_dictText',
  },
  {
    title: '支付状态',
    align: 'center',
    dataIndex: 'status_dictText',
  },
  {
    title: '支出账户',
    align: 'center',
    dataIndex: 'fromAccount_dictText',
  },
  {
    title: '接受账户',
    align: 'center',
    dataIndex: 'toAccount_dictText',
  },
  {
    title: '支付备注',
    align: 'center',
    dataIndex: 'descr',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '支付货币',
    field: 'symbol',
    component: 'Input',
  },
  {
    label: '支付金额',
    field: 'paymount',
    component: 'Input',
  },
  {
    label: '支付交易所',
    field: 'exchange',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'exchange',
    },
  },
  {
    label: '支付状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'paystatus',
    },
  },
  {
    label: '支出账户',
    field: 'fromAccount',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'coin_keys,key_name,id',
    },
  },
  {
    label: '接受账户',
    field: 'toAccount',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'coin_keys,key_name,id',
    },
  },
  {
    label: '支付备注',
    field: 'descr',
    component: 'Input',
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
  symbol: { title: '支付货币', order: 0, view: 'text', type: 'string' },
  paymount: { title: '支付金额', order: 1, view: 'text', type: 'string' },
  exchange: { title: '支付交易所', order: 2, view: 'list', type: 'string', dictCode: 'exchange' },
  status: { title: '支付状态', order: 3, view: 'list', type: 'string', dictCode: 'paystatus' },
  fromAccount: { title: '支出账户', order: 4, view: 'list', type: 'string', dictTable: 'coin_keys', dictCode: 'id', dictText: 'key_name' },
  toAccount: { title: '接受账户', order: 5, view: 'list', type: 'string', dictTable: 'coin_keys', dictCode: 'id', dictText: 'key_name' },
  descr: { title: '支付备注', order: 6, view: 'text', type: 'string' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
