import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '代金券名称',
    align: 'center',
    dataIndex: 'stockName',
  },
  {
    title: '描述',
    align: 'center',
    dataIndex: 'comment',
  },
  {
    title: '所属商户',
    align: 'center',
    dataIndex: 'belongMerchant',
  },
  {
    title: '开始时间',
    align: 'center',
    dataIndex: 'availableBeginTime',
  },
  {
    title: '结束时间',
    align: 'center',
    dataIndex: 'availableEndTime',
  },
  {
    title: '规则',
    align: 'center',
    dataIndex: 'stockUseRule',
  },
  {
    title: '最大发券数',
    align: 'center',
    dataIndex: 'maxCoupons',
  },
  {
    title: '总预算',
    align: 'center',
    dataIndex: 'maxAmount',
  },
  {
    title: '单天预算发放上限单位：分',
    align: 'center',
    dataIndex: 'maxAmountByDay',
  },
  {
    title: '单个用户可领个数',
    align: 'center',
    dataIndex: 'maxCouponsPerUser',
  },
  {
    title: '是否开启自然人限制',
    align: 'center',
    dataIndex: 'naturalPersonLimit',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '是', value: 'Y' },
        { text: '否', value: 'N' },
      ]);
    },
  },
  {
    title: '是否开启防刷拦截',
    align: 'center',
    dataIndex: 'preventApiAbuse',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '是', value: 'Y' },
        { text: '否', value: 'N' },
      ]);
    },
  },
  {
    title: '代金券详情页',
    align: 'center',
    dataIndex: 'patternInfo',
  },
  {
    title: '详细的活动规则',
    align: 'center',
    dataIndex: 'description',
  },
  {
    title: '商户logo',
    align: 'center',
    dataIndex: 'merchantLogo',
    customRender: render.renderImage,
  },
  {
    title: '品牌名称',
    align: 'center',
    dataIndex: 'merchantName',
  },
  {
    title: '背景颜色',
    align: 'center',
    dataIndex: 'backgroundColor_dictText',
  },
  {
    title: '券详情图片',
    align: 'center',
    dataIndex: 'couponImage',
    customRender: render.renderImage,
  },
  {
    title: '卡包跳转目标',
    align: 'center',
    dataIndex: 'jumpTarget',
  },
  {
    title: '小程序appid',
    align: 'center',
    dataIndex: 'miniProgramAppid',
  },
  {
    title: '小程序path',
    align: 'center',
    dataIndex: 'miniProgramPath',
  },
  {
    title: '核销规则',
    align: 'center',
    dataIndex: 'couponUseRule',
  },
  {
    title: '券生效时间',
    align: 'center',
    dataIndex: 'couponAvailableTime',
  },
  {
    title: '固定时间段可用',
    align: 'center',
    dataIndex: 'fixAvailableTime',
  },
  {
    title: '固定面额满减券使用规则',
    align: 'center',
    dataIndex: 'fixedNormalCoupon',
  },
  {
    title: '面额',
    align: 'center',
    dataIndex: 'couponAmount',
  },
  {
    title: '门槛',
    align: 'center',
    dataIndex: 'transactionMinimum',
  },
  {
    title: '订单优惠标记',
    align: 'center',
    dataIndex: 'goodsTag',
  },
  {
    title: '指定支付模式',
    align: 'center',
    dataIndex: 'tradeType',
  },
  {
    title: '是否可叠加其他优惠',
    align: 'center',
    dataIndex: 'combineUse',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '是', value: 'Y' },
        { text: '否', value: 'N' },
      ]);
    },
  },
  {
    title: '可核销商品编码',
    align: 'center',
    dataIndex: 'availableItems',
  },
  {
    title: '不参与优惠商品编码',
    align: 'center',
    dataIndex: 'unavailableItems',
  },
  {
    title: '可核销商户号',
    align: 'center',
    dataIndex: 'availableMerchants',
  },
  {
    title: '指定银行卡BIN',
    align: 'center',
    dataIndex: 'limitCard',
  },
  {
    title: '银行卡名字',
    align: 'center',
    dataIndex: 'limitCardName',
  },
  {
    title: '银行卡BIN',
    align: 'center',
    dataIndex: 'limitCardBin',
  },
  {
    title: '指定付款方式',
    align: 'center',
    dataIndex: 'limitPay',
  },
  {
    title: '营销经费',
    align: 'center',
    dataIndex: 'noCash_dictText',
  },
  {
    title: '批次类型',
    align: 'center',
    dataIndex: 'stockType_dictText',
  },
  {
    title: '商户单据号',
    align: 'center',
    dataIndex: 'outRequestNo',
  },
  {
    title: '扩展属性',
    align: 'center',
    dataIndex: 'extInfo',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '代金券名称',
    field: 'stockName',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入代金券名称!' }];
    },
  },
  {
    label: '描述',
    field: 'comment',
    component: 'Input',
  },
  {
    label: '所属商户',
    field: 'belongMerchant',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入所属商户!' }];
    },
  },
  {
    label: '开始时间',
    field: 'availableBeginTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入开始时间!' }];
    },
  },
  {
    label: '结束时间',
    field: 'availableEndTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入结束时间!' }];
    },
  },
  {
    label: '规则',
    field: 'stockUseRule',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入规则!' }];
    },
  },
  {
    label: '最大发券数',
    field: 'maxCoupons',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入最大发券数!' }];
    },
  },
  {
    label: '总预算',
    field: 'maxAmount',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入总预算!' }];
    },
  },
  {
    label: '单天预算发放上限单位：分',
    field: 'maxAmountByDay',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入单天预算发放上限单位：分!' }];
    },
  },
  {
    label: '单个用户可领个数',
    field: 'maxCouponsPerUser',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入单个用户可领个数!' }];
    },
  },
  {
    label: '是否开启自然人限制',
    field: 'naturalPersonLimit',
    component: 'JSwitch',
    componentProps: {},
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入是否开启自然人限制!' }];
    },
  },
  {
    label: '是否开启防刷拦截',
    field: 'preventApiAbuse',
    component: 'JSwitch',
    componentProps: {},
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入是否开启防刷拦截!' }];
    },
  },
  {
    label: '代金券详情页',
    field: 'patternInfo',
    component: 'Input',
  },
  {
    label: '详细的活动规则',
    field: 'description',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入详细的活动规则!' }];
    },
  },
  {
    label: '商户logo',
    field: 'merchantLogo',
    component: 'JImageUpload',
    componentProps: {
      fileMax: 0,
    },
  },
  {
    label: '品牌名称',
    field: 'merchantName',
    component: 'Input',
  },
  {
    label: '背景颜色',
    field: 'backgroundColor',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'COLOR',
    },
  },
  {
    label: '券详情图片',
    field: 'couponImage',
    component: 'JImageUpload',
    componentProps: {
      fileMax: 0,
    },
  },
  {
    label: '卡包跳转目标',
    field: 'jumpTarget',
    component: 'Input',
  },
  {
    label: '小程序appid',
    field: 'miniProgramAppid',
    component: 'Input',
  },
  {
    label: '小程序path',
    field: 'miniProgramPath',
    component: 'Input',
  },
  {
    label: '核销规则',
    field: 'couponUseRule',
    component: 'Input',
  },
  {
    label: '券生效时间',
    field: 'couponAvailableTime',
    component: 'DatePicker',
    componentProps: {
      showTime: true,
      valueFormat: 'YYYY-MM-DD HH:mm:ss',
    },
  },
  {
    label: '固定时间段可用',
    field: 'fixAvailableTime',
    component: 'Input',
    dynamicDisabled: true,
  },
  {
    label: '固定面额满减券使用规则',
    field: 'fixedNormalCoupon',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入固定面额满减券使用规则!' }];
    },
  },
  {
    label: '面额',
    field: 'couponAmount',
    component: 'InputNumber',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入面额!' }];
    },
  },
  {
    label: '门槛',
    field: 'transactionMinimum',
    component: 'InputNumber',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入门槛!' }];
    },
  },
  {
    label: '订单优惠标记',
    field: 'goodsTag',
    component: 'Input',
  },
  {
    label: '指定支付模式',
    field: 'tradeType',
    component: 'Input',
  },
  {
    label: '是否可叠加其他优惠',
    field: 'combineUse',
    component: 'JSwitch',
    componentProps: {},
  },
  {
    label: '可核销商品编码',
    field: 'availableItems',
    component: 'InputTextArea',
  },
  {
    label: '不参与优惠商品编码',
    field: 'unavailableItems',
    component: 'InputTextArea',
  },
  {
    label: '可核销商户号',
    field: 'availableMerchants',
    component: 'InputTextArea',
  },
  {
    label: '指定银行卡BIN',
    field: 'limitCard',
    component: 'InputTextArea',
  },
  {
    label: '银行卡名字',
    field: 'limitCardName',
    component: 'Input',
  },
  {
    label: '银行卡BIN',
    field: 'limitCardBin',
    component: 'Input',
  },
  {
    label: '指定付款方式',
    field: 'limitPay',
    component: 'InputTextArea',
  },
  {
    label: '营销经费',
    field: 'noCash',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'no_cash',
      type: 'radio',
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入营销经费!' }];
    },
  },
  {
    label: '批次类型',
    field: 'stockType',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'stock_type',
    },
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入批次类型!' }];
    },
  },
  {
    label: '商户单据号',
    field: 'outRequestNo',
    component: 'Input',
  },
  {
    label: '扩展属性',
    field: 'extInfo',
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
  stockName: { title: '代金券名称', order: 0, view: 'text', type: 'string' },
  comment: { title: '描述', order: 1, view: 'text', type: 'string' },
  belongMerchant: { title: '所属商户', order: 2, view: 'text', type: 'string' },
  availableBeginTime: { title: '开始时间', order: 3, view: 'datetime', type: 'string' },
  availableEndTime: { title: '结束时间', order: 4, view: 'datetime', type: 'string' },
  stockUseRule: { title: '规则', order: 5, view: 'text', type: 'string' },
  maxCoupons: { title: '最大发券数', order: 6, view: 'text', type: 'string' },
  maxAmount: { title: '总预算', order: 7, view: 'text', type: 'string' },
  maxAmountByDay: { title: '单天预算发放上限单位：分', order: 8, view: 'text', type: 'string' },
  maxCouponsPerUser: { title: '单个用户可领个数', order: 9, view: 'text', type: 'string' },
  naturalPersonLimit: { title: '是否开启自然人限制', order: 10, view: 'switch', type: 'string' },
  preventApiAbuse: { title: '是否开启防刷拦截', order: 11, view: 'switch', type: 'string' },
  patternInfo: { title: '代金券详情页', order: 12, view: 'text', type: 'string' },
  description: { title: '详细的活动规则', order: 13, view: 'text', type: 'string' },
  merchantLogo: { title: '商户logo', order: 14, view: 'image', type: 'string' },
  merchantName: { title: '品牌名称', order: 15, view: 'text', type: 'string' },
  backgroundColor: { title: '背景颜色', order: 16, view: 'list', type: 'string', dictCode: 'COLOR' },
  couponImage: { title: '券详情图片', order: 17, view: 'image', type: 'string' },
  jumpTarget: { title: '卡包跳转目标', order: 18, view: 'text', type: 'string' },
  miniProgramAppid: { title: '小程序appid', order: 19, view: 'text', type: 'string' },
  miniProgramPath: { title: '小程序path', order: 20, view: 'text', type: 'string' },
  couponUseRule: { title: '核销规则', order: 21, view: 'text', type: 'string' },
  couponAvailableTime: { title: '券生效时间', order: 22, view: 'datetime', type: 'string' },
  fixAvailableTime: { title: '固定时间段可用', order: 23, view: 'text', type: 'string' },
  fixedNormalCoupon: { title: '固定面额满减券使用规则', order: 24, view: 'text', type: 'string' },
  couponAmount: { title: '面额', order: 25, view: 'number', type: 'number' },
  transactionMinimum: { title: '门槛', order: 26, view: 'number', type: 'number' },
  goodsTag: { title: '订单优惠标记', order: 27, view: 'text', type: 'string' },
  tradeType: { title: '指定支付模式', order: 28, view: 'text', type: 'string' },
  combineUse: { title: '是否可叠加其他优惠', order: 29, view: 'switch', type: 'string' },
  availableItems: { title: '可核销商品编码', order: 30, view: 'textarea', type: 'string' },
  unavailableItems: { title: '不参与优惠商品编码', order: 31, view: 'textarea', type: 'string' },
  availableMerchants: { title: '可核销商户号', order: 32, view: 'textarea', type: 'string' },
  limitCard: { title: '指定银行卡BIN', order: 33, view: 'textarea', type: 'string' },
  limitCardName: { title: '银行卡名字', order: 34, view: 'text', type: 'string' },
  limitCardBin: { title: '银行卡BIN', order: 35, view: 'text', type: 'string' },
  limitPay: { title: '指定付款方式', order: 36, view: 'textarea', type: 'string' },
  noCash: { title: '营销经费', order: 37, view: 'radio', type: 'string', dictCode: 'no_cash' },
  stockType: { title: '批次类型', order: 38, view: 'list', type: 'string', dictCode: 'stock_type' },
  outRequestNo: { title: '商户单据号', order: 39, view: 'text', type: 'string' },
  extInfo: { title: '扩展属性', order: 40, view: 'text', type: 'string' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
