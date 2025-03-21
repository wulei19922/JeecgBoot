import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
import { rules } from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '申请时间',
    align: 'center',
    dataIndex: 'createTime',
  },
  {
    title: '申请人',
    align: 'center',
    dataIndex: 'createBy',
  },
  {
    title: '转出地址',
    align: 'center',
    dataIndex: 'addressFrom',
  },
  {
    title: '转出地址',
    align: 'center',
    dataIndex: 'addressFrom',
  },
  {
    title: '接收地址',
    align: 'center',
    dataIndex: 'addressTo',
  },
  {
    title: '金额',
    align: 'center',
    dataIndex: 'num',
  },
  {
    title: '序列号',
    align: 'center',
    dataIndex: 'serinal',
  },
  {
    title: '申请类别',
    align: 'center',
    dataIndex: 'type_dictText',
  },
  {
    title: '审核状态',
    align: 'center',
    dataIndex: 'auditStatus_dictText',
  },
  {
    title: '交易图片',
    align: 'center',
    dataIndex: 'orderImg',
    customRender: render.renderImage,
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '转出地址',
    field: 'addressFrom',
    component: 'Input',
  },
  {
    label: '接收地址',
    field: 'addressTo',
    component: 'Input',
  },
  {
    label: '金额',
    field: 'num',
    component: 'InputNumber',
  },
  {
    label: '序列号',
    field: 'serinal',
    component: 'Input',
  },
  {
    label: '申请类别',
    field: 'type',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'trade_apply_type',
    },
  },
  {
    label: '审核状态',
    field: 'auditStatus',
    component: 'JDictSelectTag',
    componentProps: {
      dictCode: 'audit_status',
    },
  },
  {
    label: '交易图片',
    field: 'orderImg',
    component: 'JImageUpload',
    componentProps: {
      fileMax: 0,
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
  addressFrom: { title: '转出地址', order: 0, view: 'text', type: 'string' },
  addressTo: { title: '接收地址', order: 1, view: 'text', type: 'string' },
  num: { title: '金额', order: 2, view: 'number', type: 'number' },
  serinal: { title: '序列号', order: 3, view: 'text', type: 'string' },
  type: { title: '申请类别', order: 4, view: 'list', type: 'string', dictCode: 'trade_apply_type' },
  auditStatus: { title: '审核状态', order: 5, view: 'list', type: 'string', dictCode: 'audit_status' },
  orderImg: { title: '交易图片', order: 6, view: 'image', type: 'string' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
