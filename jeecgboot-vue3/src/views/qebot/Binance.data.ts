import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';

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

export const binanceFututeIncomeColumns: BasicColumn[] = [
  {
    title: '交易对',
    align: 'center',
    dataIndex: 'symbol',
    minWidth: 100,
  },
  {
    title: '收益类型',
    align: 'center',
    dataIndex: 'incomeType',
    minWidth: 100,
    customRender: ({ text }) => {
      return render.renderSwitch(text, [
        { text: '划转', value: 'TRANSFER' },
        { text: '欢迎资金', value: 'WELCOME_BONUS' },
        { text: '资费', value: 'FUNDING_FEE' },
        { text: '实现盈亏', value: 'REALIZED_PNL' },
        { text: '手续费', value: 'COMMISSION' },
        { text: '清算费用', value: 'INSURANCE_CLEAR' },
        { text: '到期交割', value: 'DELIVERED_SETTLEMENT' },
      ]);
    },
  },
  {
    title: '收益',
    align: 'center',
    dataIndex: 'income',
    minWidth: 100,
  },
  {
    title: '资产',
    align: 'center',
    dataIndex: 'asset',
    minWidth: 100,
  },
  {
    title: '信息',
    align: 'center',
    dataIndex: 'info',
    minWidth: 100,
  },
  {
    title: '时间',
    align: 'center',
    minWidth: 100,
    dataIndex: 'time',
    customRender: ({ text }) => {
      if (!text) return '-';
      const date = new Date(text);

      // 处理无效日期
      if (isNaN(date.getTime())) return text;

      const pad = (n: number) => n.toString().padStart(2, '0');
      return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
    }
  },
];
