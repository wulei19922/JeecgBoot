import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '投放渠道',
    align:"center",
    dataIndex: 'sendChannel_dictText'
   },
   {
    title: '名称',
    align:"center",
    dataIndex: 'actName'
   },
   {
    title: '活动银行',
    align:"center",
    dataIndex: 'bankName_dictText'
   },
   {
    title: '银行logo',
    align:"center",
    dataIndex: 'bankLogo_dictText'
   },
   {
    title: '消费门槛',
    align:"center",
    dataIndex: 'stockMin'
   },
   {
    title: '免减金额',
    align:"center",
    dataIndex: 'stockVal'
   },
   {
    title: '活动预算',
    align:"center",
    dataIndex: 'stockBudget'
   },
   {
    title: '开始时间',
    align:"center",
    dataIndex: 'beginTime'
   },
   {
    title: '结束时间',
    align:"center",
    dataIndex: 'endTime'
   },
   {
    title: '领券后几天可以使用',
    align:"center",
    dataIndex: 'relativeDays'
   },
   {
    title: '使用规则',
    align:"center",
    dataIndex: 'actRuleIntro'
   },
   {
    title: '单人领取上限',
    align:"center",
    dataIndex: 'userMaxQuota'
   },
   {
    title: '单日发放上限',
    align:"center",
    dataIndex: 'dailyBudget'
   },
   {
    title: '发放方',
    align:"center",
    dataIndex: 'senderMchids'
   },
   {
    title: ' 自然人防刷',
    align:"center",
    dataIndex: 'useNaturalDefense',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
   {
    title: '小号拦截',
    align:"center",
    dataIndex: 'useSpamBlock',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
   {
    title: '背景颜色',
    align:"center",
    dataIndex: 'bgColor'
   },
   {
    title: 'car_bin',
    align:"center",
    dataIndex: 'cardBinInfo'
   },
   {
    title: '叠加使用',
    align:"center",
    dataIndex: 'combineUse',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
   {
    title: '固定值',
    align:"center",
    dataIndex: 'fixedValue'
   },
   {
    title: '配额所属商户',
    align:"center",
    dataIndex: 'quotaBelongMchId'
   },
   {
    title: '配额所属银行',
    align:"center",
    dataIndex: 'quotaBelongBankName'
   },
   {
    title: '配额银行卡名称',
    align:"center",
    dataIndex: 'quotaBelongBankCardTypeName'
   },
   {
    title: '配额银行卡',
    align:"center",
    dataIndex: 'quotaBelongBankCardType'
   },
   {
    title: '配额银行所属产品',
    align:"center",
    dataIndex: 'quotaBelongProduct'
   },
   {
    title: '序列号',
    align:"center",
    dataIndex: 'sequence'
   },
   {
    title: 'TOKEN',
    align:"center",
    dataIndex: 'eccCsrfToken'
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
    label: '投放渠道',
    field: 'sendChannel',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"wechat_sent_channel"
     },
  },
  {
    label: '名称',
    field: 'actName',
    component: 'Input',
  },
  {
    label: '活动银行',
    field: 'bankName',
    component: 'JSearchSelect',
    componentProps:{
       dict:"wechat_bank,bank_name,bank_value"
    },
  },
  {
    label: '银行logo',
    field: 'bankLogo',
    component: 'JSearchSelect',
    componentProps:{
       dict:"wechat_bank,bank_name,bank_logo"
    },
  },
  {
    label: '消费门槛',
    field: 'stockMin',
    component: 'InputNumber',
  },
  {
    label: '免减金额',
    field: 'stockVal',
    component: 'InputNumber',
  },
  {
    label: '活动预算',
    field: 'stockBudget',
    component: 'InputNumber',
  },
  {
    label: '开始时间',
    field: 'beginTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '结束时间',
    field: 'endTime',
    component: 'DatePicker',
    componentProps: {
       showTime: true,
       valueFormat: 'YYYY-MM-DD HH:mm:ss'
     },
  },
  {
    label: '领券后几天可以使用',
    field: 'relativeDays',
    component: 'InputNumber',
  },
  {
    label: '使用规则',
    field: 'actRuleIntro',
    component: 'Input',
  },
  {
    label: '单人领取上限',
    field: 'userMaxQuota',
    component: 'InputNumber',
  },
  {
    label: '单日发放上限',
    field: 'dailyBudget',
    component: 'InputNumber',
  },
  {
    label: '发放方',
    field: 'senderMchids',
    component: 'Input',
  },
  {
    label: ' 自然人防刷',
    field: 'useNaturalDefense',
     component: 'JSwitch',
     componentProps:{
     },
  },
  {
    label: '小号拦截',
    field: 'useSpamBlock',
     component: 'JSwitch',
     componentProps:{
     },
  },
  {
    label: '背景颜色',
    field: 'bgColor',
    component: 'Input',
  },
  {
    label: 'car_bin',
    field: 'cardBinInfo',
    component: 'Input',
  },
  {
    label: '叠加使用',
    field: 'combineUse',
     component: 'JSwitch',
     componentProps:{
     },
  },
  {
    label: '固定值',
    field: 'fixedValue',
    component: 'InputNumber',
  },
  {
    label: '配额所属商户',
    field: 'quotaBelongMchId',
    component: 'Input',
  },
  {
    label: '配额所属银行',
    field: 'quotaBelongBankName',
    component: 'Input',
  },
  {
    label: '配额银行卡名称',
    field: 'quotaBelongBankCardTypeName',
    component: 'Input',
  },
  {
    label: '配额银行卡',
    field: 'quotaBelongBankCardType',
    component: 'Input',
  },
  {
    label: '配额银行所属产品',
    field: 'quotaBelongProduct',
    component: 'Input',
  },
  {
    label: '序列号',
    field: 'sequence',
    component: 'Input',
  },
  {
    label: 'TOKEN',
    field: 'eccCsrfToken',
    component: 'Input',
  },
  {
    label: '状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"wechat_bank_status"
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
  sendChannel: {title: '投放渠道',order: 0,view: 'list', type: 'string',dictCode: 'wechat_sent_channel',},
  actName: {title: '名称',order: 1,view: 'text', type: 'string',},
  bankName: {title: '活动银行',order: 2,view: 'sel_search', type: 'string',dictTable: "wechat_bank", dictCode: 'bank_value', dictText: 'bank_name',},
  bankLogo: {title: '银行logo',order: 3,view: 'sel_search', type: 'string',dictTable: "wechat_bank", dictCode: 'bank_logo', dictText: 'bank_name',},
  stockMin: {title: '消费门槛',order: 4,view: 'number', type: 'number',},
  stockVal: {title: '免减金额',order: 5,view: 'number', type: 'number',},
  stockBudget: {title: '活动预算',order: 6,view: 'number', type: 'number',},
  beginTime: {title: '开始时间',order: 7,view: 'datetime', type: 'string',},
  endTime: {title: '结束时间',order: 8,view: 'datetime', type: 'string',},
  relativeDays: {title: '领券后几天可以使用',order: 9,view: 'number', type: 'number',},
  actRuleIntro: {title: '使用规则',order: 10,view: 'text', type: 'string',},
  userMaxQuota: {title: '单人领取上限',order: 11,view: 'number', type: 'number',},
  dailyBudget: {title: '单日发放上限',order: 12,view: 'number', type: 'number',},
  senderMchids: {title: '发放方',order: 13,view: 'text', type: 'string',},
  useNaturalDefense: {title: ' 自然人防刷',order: 14,view: 'switch', type: 'string',},
  useSpamBlock: {title: '小号拦截',order: 15,view: 'switch', type: 'string',},
  bgColor: {title: '背景颜色',order: 16,view: 'text', type: 'string',},
  cardBinInfo: {title: 'car_bin',order: 17,view: 'text', type: 'string',},
  combineUse: {title: '叠加使用',order: 18,view: 'switch', type: 'string',},
  fixedValue: {title: '固定值',order: 19,view: 'number', type: 'number',},
  quotaBelongMchId: {title: '配额所属商户',order: 20,view: 'text', type: 'string',},
  quotaBelongBankName: {title: '配额所属银行',order: 21,view: 'text', type: 'string',},
  quotaBelongBankCardTypeName: {title: '配额银行卡名称',order: 22,view: 'text', type: 'string',},
  quotaBelongBankCardType: {title: '配额银行卡',order: 23,view: 'text', type: 'string',},
  quotaBelongProduct: {title: '配额银行所属产品',order: 24,view: 'text', type: 'string',},
  sequence: {title: '序列号',order: 25,view: 'text', type: 'string',},
  eccCsrfToken: {title: 'TOKEN',order: 26,view: 'text', type: 'string',},
  status: {title: '状态',order: 27,view: 'list', type: 'string',dictCode: 'wechat_bank_status',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}