import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '结算日期',
    align: 'center',
    dataIndex: 'day',
  },
  {
    title: '运行中的机器人',
    align: 'center',
    dataIndex: 'botRuning',
  },
  {
    title: '用户机器人日利润',
    align: 'center',
    dataIndex: 'profitDay',
  },
  {
    title: '用户机器人日亏损',
    align: 'center',
    dataIndex: 'lossDay',
  },
  {
    title: '公司机器人净利润',
    align: 'center',
    dataIndex: 'profitDayNetpfofit',
  },
  {
    title: '市场待结利润',
    align: 'center',
    dataIndex: 'settlementDayIng',
  },
  {
    title: '市场已结算利润',
    align: 'center',
    dataIndex: 'settlementDayEd',
  },
  {
    title: '结算状态',
    align: 'center',
    dataIndex: 'status_dictText',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
  {
    label: '结算日期',
    field: 'day',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
    //colProps: {span: 6},
  },
];

//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '结算日期',
    field: 'day',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '运行中的机器人',
    field: 'botRuning',
    component: 'InputNumber',
  },
  {
    label: '用户机器人日利润',
    field: 'profitDay',
    component: 'InputNumber',
  },
  {
    label: '用户机器人日亏损',
    field: 'lossDay',
    component: 'InputNumber',
  },
  {
    label: '公司机器人净利润',
    field: 'profitDayNetpfofit',
    component: 'InputNumber',
  },
  {
    label: '市场待结利润',
    field: 'settlementDayIng',
    component: 'Input',
  },
  {
    label: '市场已结算利润',
    field: 'settlementDayEd',
    component: 'Input',
  },
  {
    label: '结算状态',
    field: 'status',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'settlement',
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

//子表列表数据
export const coinSettlementDayDetailColumns: BasicColumn[] = [
  {
    title: '表头ID',
    align: 'center',
    dataIndex: 'headId',
  },
  {
    title: '用户',
    align: 'center',
    dataIndex: 'memberId_dictText',
  },
  {
    title: '交易对',
    align: 'center',
    dataIndex: 'symbol',
  },
  {
    title: '用户利润',
    align: 'center',
    dataIndex: 'profitDayUser',
  },
  {
    title: '机器人费用',
    align: 'center',
    dataIndex: 'botProfit',
  },
];
//子表表单数据
export const coinSettlementDayDetailFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
  {
    label: '表头ID',
    field: 'headId',
    component: 'Input',
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
    label: '交易对',
    field: 'symbol',
    component: 'Input',
  },
  {
    label: '用户利润',
    field: 'profitDayUser',
    component: 'InputNumber',
  },
  {
    label: '机器人费用',
    field: 'botProfit',
    component: 'InputNumber',
  },
];
//子表列表数据
export const coinSettlementDayRewardColumns: BasicColumn[] = [
  {
    title: '分润用户',
    align: 'center',
    dataIndex: 'memberId_dictText',
  },
  {
    title: '用户级别',
    align: 'center',
    dataIndex: 'memberGrade_dictText',
  },
  {
    title: '明细说明',
    align: 'center',
    dataIndex: 'detail',
  },
  {
    title: '奖励数量',
    align: 'center',
    dataIndex: 'reward',
  },
  {
    title: '所属平台',
    align: 'center',
    dataIndex: 'exchange_dictText',
  },
  {
    title: '表头',
    align: 'center',
    dataIndex: 'headId',
  },
  {
    title: '基础用户',
    align: 'center',
    dataIndex: 'inviteMemberId_dictText',
  },
  {
    title: '结算状态',
    align: 'center',
    dataIndex: 'settlementStatus_dictText',
  },
];
//子表表单数据
export const coinSettlementDayRewardFormSchema: FormSchema[] = [
  // TODO 子表隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
  {
    label: '分润用户',
    field: 'memberId',
    component: 'JSearchSelect',
    componentProps: {
      dict: 'sys_user,username,id',
    },
  },
  {
    label: '用户级别',
    field: 'memberGrade',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'sys_role,role_name,id',
    },
  },
  {
    label: '明细说明',
    field: 'detail',
    component: 'Input',
  },
  {
    label: '奖励数量',
    field: 'reward',
    component: 'Input',
  },
  {
    label: '所属平台',
    field: 'exchange',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'exchange',
    },
  },
  {
    label: '表头',
    field: 'headId',
    component: 'Input',
    dynamicDisabled: true,
  },
  {
    label: '基础用户',
    field: 'inviteMemberId',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'sys_user,username,id',
    },
  },
  {
    label: '结算状态',
    field: 'settlementStatus',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'settlement',
    },
  },
];

// 高级查询数据
export const superQuerySchema = {
  day: { title: '结算日期', order: 0, view: 'datetime', type: 'string' },
  botRuning: { title: '运行中的机器人', order: 1, view: 'number', type: 'number' },
  profitDay: { title: '用户机器人日利润', order: 2, view: 'number', type: 'number' },
  lossDay: { title: '用户机器人日亏损', order: 3, view: 'number', type: 'number' },
  profitDayNetpfofit: { title: '公司机器人净利润', order: 4, view: 'number', type: 'number' },
  settlementDayIng: { title: '市场待结利润', order: 5, view: 'text', type: 'string' },
  settlementDayEd: { title: '市场已结算利润', order: 6, view: 'text', type: 'string' },
  status: { title: '结算状态', order: 7, view: 'list', type: 'string', dictCode: 'settlement' },
};
