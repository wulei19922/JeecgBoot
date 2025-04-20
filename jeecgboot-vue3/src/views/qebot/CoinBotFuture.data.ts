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
    title: '类型',
    align: 'center',
    dataIndex: 'type',
  },
  {
    title: '方向',
    align: 'center',
    dataIndex: 'silde_dictText',
  },
  {
    title: '平均价格',
    align: 'center',
    dataIndex: 'avgPrice',
  },
  {
    title: '价格',
    align: 'center',
    dataIndex: 'price',
  },
  {
    title: '数量',
    align: 'center',
    dataIndex: 'num',
  },
  {
    title: '只减仓',
    align: 'center',
    dataIndex: 'postionDown',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '是', value: 'Y' },
        { text: '否', value: 'N' },
      ]);
    },
  },
  {
    title: '只做Maker',
    align: 'center',
    dataIndex: 'isMaker',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '是', value: 'Y' },
        { text: '否', value: 'N' },
      ]);
    },
  },
  {
    title: '触发条件',
    align: 'center',
    dataIndex: 'activeCondition',
  },
  {
    title: '机器人',
    align: 'center',
    dataIndex: 'botId',
  },
  {
    title: '平台',
    align: 'center',
    dataIndex: 'exchange_dictText',
  },
];

//币安合约数据
//列表数据
export const binanceFututeColumns: BasicColumn[] = [
  {
    title: '交易对',
    align: 'center',
    dataIndex: 'symbol',
  },
  {
    title: '方向',
    align: 'center',
    dataIndex: 'positionSide',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '多', value: 'LONG' },
        { text: '空', value: 'SHORT' },
      ]);
    },
  },
  {
    title: '持仓',
    align: 'center',
    dataIndex: 'positionAmt',
  },
  {
    title: '开仓价格',
    align: 'center',
    dataIndex: 'entryPrice',
    customRender: ({ text }) => {
      const value = Number(text);
      // 处理非数字和零值
      if (isNaN(value) || value === 0) return text;

      // 数值处理逻辑
      if (Math.abs(value) >= 0.01) {
        // 两位小数截断（非四舍五入）
        return (Math.floor(value * 100) / 100).toFixed(2);
      } else {
        // 保留原精度显示
        const strVal = String(value);
        const [intPart, decimalPart] = strVal.split('.');
        return decimalPart ? `${intPart}.${decimalPart.replace(/0+$/, '')}` : intPart;
      }
    },
  },
  {
    title: '标记价格',
    align: 'center',
    dataIndex: 'markPrice',
    customRender: ({ text }) => {
      const value = Number(text);
      // 处理非数字和零值
      if (isNaN(value) || value === 0) return text;

      // 数值处理逻辑
      if (Math.abs(value) >= 0.01) {
        // 两位小数截断（非四舍五入）
        return (Math.floor(value * 100) / 100).toFixed(2);
      } else {
        // 保留原精度显示
        const strVal = String(value);
        const [intPart, decimalPart] = strVal.split('.');
        return decimalPart ? `${intPart}.${decimalPart.replace(/0+$/, '')}` : intPart;
      }
    },
  },
  {
    title: '盈亏价格',
    align: 'center',
    dataIndex: 'breakEvenPrice',
    customRender: ({ text }) => {
      const value = Number(text);
      // 处理非数字和零值
      if (isNaN(value) || value === 0) return text;

      // 数值处理逻辑
      if (Math.abs(value) >= 0.01) {
        // 两位小数截断（非四舍五入）
        return (Math.floor(value * 100) / 100).toFixed(2);
      } else {
        // 保留原精度显示
        const strVal = String(value);
        const [intPart, decimalPart] = strVal.split('.');
        return decimalPart ? `${intPart}.${decimalPart.replace(/0+$/, '')}` : intPart;
      }
    },
  },
  {
    title: '杠杆',
    align: 'center',
    dataIndex: 'leverage',
  },
  {
    title: '保证金模式',
    align: 'center',
    dataIndex: 'marginType',
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '全仓', value: 'cross' },
        { text: '逐仓', value: 'isolated' },
      ]);
    },
  },
  {
    title: '逐仓模式',
    align: 'center',
    dataIndex: 'isolated',
    customRender: ({ text }) => {
      const text1 = text + '';
      return render.renderSwitch(text1, [
        { text: '否', value: 'false' },
        { text: '是', value: 'true' },
      ]);
    },
  },
  {
    title: '逐仓保证金',
    align: 'center',
    dataIndex: 'isolatedMargin',
    customRender: ({ text }) => {
      const value = Number(text);
      // 处理非数字和零值
      if (isNaN(value) || value === 0) return 0;

      // 数值处理逻辑
      if (Math.abs(value) >= 0.01) {
        // 两位小数截断（非四舍五入）
        return (Math.floor(value * 100) / 100).toFixed(2);
      } else {
        // 保留原精度显示
        const strVal = String(value);
        const [intPart, decimalPart] = strVal.split('.');
        return decimalPart ? `${intPart}.${decimalPart.replace(/0+$/, '')}` : intPart;
      }
    },
  },
  {
    title: '逐仓钱包余额',
    align: 'center',
    dataIndex: 'isolatedWallet',
  },
  {
    title: '自动追加保证金',
    align: 'center',
    dataIndex: 'isAutoAddMargin',
    customRender: ({ text }) => {
      const text1 = text + '';
      return render.renderSwitch(text1, [
        { text: '否', value: 'false' },
        { text: '是', value: 'true' },
      ]);
    },
  },
  {
    title: '未实现盈亏',
    align: 'center',
    dataIndex: 'unRealizedProfit',
    customRender: ({ text }) => {
      const value = Number(text);
      // 处理非数字和零值
      if (isNaN(value) || value === 0) return 0;

      // 数值处理逻辑
      if (Math.abs(value) >= 0.01) {
        // 两位小数截断（非四舍五入）
        return (Math.floor(value * 100) / 100).toFixed(2);
      } else {
        // 保留原精度显示
        const strVal = String(value);
        const [intPart, decimalPart] = strVal.split('.');
        return decimalPart ? `${intPart}.${decimalPart.replace(/0+$/, '')}` : intPart;
      }
    },
  },
  {
    title: '仓位名义价值',
    align: 'center',
    dataIndex: 'notional',
    customRender: ({ text }) => {
      const value = Number(text);
      // 处理非数字和零值
      if (isNaN(value) || value === 0) return text;

      // 数值处理逻辑
      if (Math.abs(value) >= 0.01) {
        // 两位小数截断（非四舍五入）
        return (Math.floor(value * 100) / 100).toFixed(2);
      } else {
        // 保留原精度显示
        const strVal = String(value);
        const [intPart, decimalPart] = strVal.split('.');
        return decimalPart ? `${intPart}.${decimalPart.replace(/0+$/, '')}` : intPart;
      }
    },
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
    label: '类型',
    field: 'type',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'delegation_type',
    },
  },
  {
    label: '方向',
    field: 'silde',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'future_side',
    },
  },
  {
    label: '平均价格',
    field: 'avgPrice',
    component: 'InputNumber',
  },
  {
    label: '价格',
    field: 'price',
    component: 'InputNumber',
  },
  {
    label: '数量',
    field: 'num',
    component: 'InputNumber',
  },
  {
    label: '只减仓',
    field: 'postionDown',
    component: 'JSwitch',
    componentProps: {},
  },
  {
    label: '只做Maker',
    field: 'isMaker',
    component: 'JSwitch',
    componentProps: {},
  },
  {
    label: '触发条件',
    field: 'activeCondition',
    component: 'Input',
  },
  {
    label: '机器人',
    field: 'botId',
    component: 'Input',
  },
  {
    label: '平台',
    field: 'exchange',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'exchange',
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
  type: { title: '类型', order: 1, view: 'text', type: 'string' },
  silde: { title: '方向', order: 2, view: 'list', type: 'string', dictCode: 'future_side' },
  avgPrice: { title: '平均价格', order: 3, view: 'number', type: 'number' },
  price: { title: '价格', order: 4, view: 'number', type: 'number' },
  num: { title: '数量', order: 5, view: 'number', type: 'number' },
  postionDown: { title: '只减仓', order: 6, view: 'switch', type: 'string' },
  isMaker: { title: '只做Maker', order: 7, view: 'switch', type: 'string' },
  activeCondition: { title: '触发条件', order: 8, view: 'text', type: 'string' },
  botId: { title: '机器人', order: 9, view: 'text', type: 'string' },
  exchange: { title: '平台', order: 10, view: 'list', type: 'string', dictCode: 'exchange' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
