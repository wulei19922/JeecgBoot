import { defHttp } from '/@/utils/http/axios';
import { useMessage } from '/@/hooks/web/useMessage';

const { createConfirm } = useMessage();

enum Api {
  list = '/qe/coinBot/list',
  save = '/qe/coinBot/add',
  edit = '/qe/coinBot/edit',
  editConfigApi = '/qe/coinBot/editconfig',
  deleteOne = '/qe/coinBot/delete',
  deleteBatch = '/qe/coinBot/deleteBatch',
  operateBatch = '/qe/coinBot/operateBatch',
  importExcel = '/qe/coinBot/importExcel',
  exportXls = '/qe/coinBot/exportXls',
  kafka = '/qe/coinBot/kafka/manager',
  kafkaPod = '/qe/coinBot/kafka/pod',
  wallet = '/qe/coinBotFuture/binance/wallet',
}
/**
 * 导出api
 * @param params
 */
export const getExportUrl = Api.exportXls;
/**
 * 导入api
 */
export const getImportUrl = Api.importExcel;
/**
 * 列表接口
 * @param params
 */
export const list = (params) => defHttp.get({ url: Api.list, params });

/**
 * 删除单个
 */
export const deleteOne = (params, handleSuccess) => {
  return defHttp.delete({ url: Api.deleteOne, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
};
/**
 * 批量删除
 * @param params
 */
export const batchDelete = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.deleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
  });
};
export const batchOperate = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认',
    content: params['message'],
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.operateBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
  });
};

/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  const url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({ url: url, params });
};

/**
 * 保存或者更新
 * @param params
 */
export const editConfigApi = (params) => {
  const url = Api.editConfigApi;
  return defHttp.post({ url: url, params });
};
/**
 * 保存或者更新
 * @param params
 */
export const kafkaApi = (params) => {
  const url = Api.kafka;
  return defHttp.post({ url: url, params });
};
export const kafkaApiPod = (params) => {
  const url = Api.kafkaPod;
  return defHttp.post({ url: url, params });
};
export const walletApi = (params) => {
  const url = Api.wallet;
  return defHttp.get({ url: url, params });
};
