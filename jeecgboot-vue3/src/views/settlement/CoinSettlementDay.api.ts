import { defHttp } from '/@/utils/http/axios';
import { useMessage } from '/@/hooks/web/useMessage';

const { createConfirm, createInfoModal } = useMessage();

enum Api {
  list = '/qe/coinSettlementDay/list',
  save = '/qe/coinSettlementDay/add',
  edit = '/qe/coinSettlementDay/edit',
  deleteOne = '/qe/coinSettlementDay/delete',
  deleteBatch = '/qe/coinSettlementDay/deleteBatch',
  importExcel = '/qe/coinSettlementDay/importExcel',
  exportXls = '/qe/coinSettlementDay/exportXls',
  coinSettlementDayDetailList = '/qe/coinSettlementDay/listCoinSettlementDayDetailByMainId',
  coinSettlementDayDetailSave = '/qe/coinSettlementDay/addCoinSettlementDayDetail',
  coinSettlementDayDetailEdit = '/qe/coinSettlementDay/editCoinSettlementDayDetail',
  coinSettlementDayDetailDelete = '/qe/coinSettlementDay/deleteCoinSettlementDayDetail',
  coinSettlementDayDetailDeleteBatch = '/qe/coinSettlementDay/deleteBatchCoinSettlementDayDetail',
  coinSettlementDayRewardList = '/qe/coinSettlementDay/listCoinSettlementDayRewardByMainId',
  coinSettlementDayRewardSave = '/qe/coinSettlementDay/addCoinSettlementDayReward',
  coinSettlementDayRewardEdit = '/qe/coinSettlementDay/editCoinSettlementDayReward',
  coinSettlementDayRewardDelete = '/qe/coinSettlementDay/deleteCoinSettlementDayReward',
  coinSettlementDayRewardDeleteBatch = '/qe/coinSettlementDay/deleteBatchCoinSettlementDayReward',
  coinSettlementPayBatch = '/qe/manager/settlement/pay',
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
/**
 * 保存或者更新
 * @param params
 */
export const saveOrUpdate = (params, isUpdate) => {
  const url = isUpdate ? Api.edit : Api.save;
  return defHttp.post({ url: url, params });
};
/**
 * 列表接口
 * @param params
 */
export const coinSettlementDayDetailList = (params) => {
  if (params['headId']) {
    return defHttp.get({ url: Api.coinSettlementDayDetailList, params });
  }
  return Promise.resolve({});
};

/**
 * 删除单个
 */
export const coinSettlementDayDetailDelete = (params, handleSuccess) => {
  return defHttp.delete({ url: Api.coinSettlementDayDetailDelete, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
};
/**
 * 批量删除
 * @param params
 */
export const coinSettlementDayDetailDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.coinSettlementDayDetailDeleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
  });
};
/**
 * 保存或者更新
 * @param params
 */
export const coinSettlementDayDetailSaveOrUpdate = (params, isUpdate) => {
  const url = isUpdate ? Api.coinSettlementDayDetailEdit : Api.coinSettlementDayDetailSave;
  return defHttp.post({ url: url, params });
};
/**
 * 导入
 */
export const coinSettlementDayDetailImportUrl = '/qe/coinSettlementDay/importCoinSettlementDayDetail';

/**
 * 导出
 */
export const coinSettlementDayDetailExportXlsUrl = '/qe/coinSettlementDay/exportCoinSettlementDayDetail';
/**
 * 列表接口
 * @param params
 */
export const coinSettlementDayRewardList = (params) => {
  if (params['headId']) {
    return defHttp.get({ url: Api.coinSettlementDayRewardList, params });
  }
  return Promise.resolve({});
};

/**
 * 删除单个
 */
export const coinSettlementDayRewardDelete = (params, handleSuccess) => {
  return defHttp.delete({ url: Api.coinSettlementDayRewardDelete, params }, { joinParamsToUrl: true }).then(() => {
    handleSuccess();
  });
};
/**
 * 批量删除
 * @param params
 */
export const coinSettlementDayRewardDeleteBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '确认删除',
    content: '是否删除选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.delete({ url: Api.coinSettlementDayRewardDeleteBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
  });
};

export const payBatch = (params, handleSuccess) => {
  createConfirm({
    iconType: 'warning',
    title: '批量结算',
    content: '是批量结算选中数据',
    okText: '确认',
    cancelText: '取消',
    onOk: () => {
      return defHttp.put({ url: Api.coinSettlementPayBatch, data: params }, { joinParamsToUrl: true }).then(() => {
        handleSuccess();
      });
    },
  });
};
/**
 * 保存或者更新
 * @param params
 */
export const coinSettlementDayRewardSaveOrUpdate = (params, isUpdate) => {
  const url = isUpdate ? Api.coinSettlementDayRewardEdit : Api.coinSettlementDayRewardSave;
  return defHttp.post({ url: url, params });
};
/**
 * 导入
 */
export const coinSettlementDayRewardImportUrl = '/qe/coinSettlementDay/importCoinSettlementDayReward';

/**
 * 导出
 */
export const coinSettlementDayRewardExportXlsUrl = '/qe/coinSettlementDay/exportCoinSettlementDayReward';
