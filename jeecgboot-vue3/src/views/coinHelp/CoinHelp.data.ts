import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '帮助标题',
    align:"center",
    dataIndex: 'title'
   },
   {
    title: '文章内容',
    align:"center",
    dataIndex: 'content',
   },
   {
    title: '缩略图40x40',
    align:"center",
    dataIndex: 'thumb',
    customRender:render.renderImage,
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '帮助标题',
    field: 'title',
    component: 'Input',
  },
  {
    label: '文章内容',
    field: 'content',
    component: 'JEditor',
  },
  {
    label: '缩略图40x40',
    field: 'thumb',
     component: 'JImageUpload',
     componentProps:{
        fileMax: 0
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
  title: {title: '帮助标题',order: 0,view: 'text', type: 'string',},
  content: {title: '文章内容',order: 1,view: 'umeditor', type: 'string',},
  thumb: {title: '缩略图40x40',order: 2,view: 'image', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}