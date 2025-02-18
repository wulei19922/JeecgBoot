import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '方向',
    align:"center",
    dataIndex: 'silder'
   },
   {
    title: '	订单类型',
    align:"center",
    dataIndex: 'orderType'
   },
   {
    title: '	成交均价',
    align:"center",
    dataIndex: 'avgPrice'
   },
   {
    title: '成交数量',
    align:"center",
    dataIndex: 'num'
   },
   {
    title: '成交价格',
    align:"center",
    dataIndex: 'price'
   },
   {
    title: '机器人ID',
    align:"center",
    dataIndex: 'botId'
   },
   {
    title: '状态',
    align:"center",
    dataIndex: 'status_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '方向',
    field: 'silder',
    component: 'Input',
  },
  {
    label: '	订单类型',
    field: 'orderType',
    component: 'Input',
  },
  {
    label: '	成交均价',
    field: 'avgPrice',
    component: 'InputNumber',
  },
  {
    label: '成交数量',
    field: 'num',
    component: 'InputNumber',
  },
  {
    label: '成交价格',
    field: 'price',
    component: 'InputNumber',
  },
  {
    label: '机器人ID',
    field: 'botId',
    component: 'Input',
  },
  {
    label: '状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"BINANCE_ORDER_STATUS"
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  silder: {title: '方向',order: 0,view: 'text', type: 'string',},
  orderType: {title: '	订单类型',order: 1,view: 'text', type: 'string',},
  avgPrice: {title: '	成交均价',order: 2,view: 'number', type: 'number',},
  num: {title: '成交数量',order: 3,view: 'number', type: 'number',},
  price: {title: '成交价格',order: 4,view: 'number', type: 'number',},
  botId: {title: '机器人ID',order: 5,view: 'text', type: 'string',},
  status: {title: '状态',order: 6,view: 'list', type: 'string',dictCode: 'BINANCE_ORDER_STATUS',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}