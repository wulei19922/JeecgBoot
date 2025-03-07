<template>
  <div class="p-2 cgformErpList">
    <div class="content">
      <!--引用表格-->
      <BasicTable @register="registerTable" :rowSelection="rowSelection">
        <!--插槽:table标题-->
        <template #tableTitle>
          <a-button type="primary" v-auth="'qe:coin_settlement_day:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
          <a-button type="primary" v-auth="'qe:coin_settlement_day:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls">
            导出</a-button
          >
          <j-upload-button type="primary" v-auth="'qe:coin_settlement_day:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls"
            >导入</j-upload-button
          >
          <a-dropdown v-if="selectedRowKeys.length > 0">
            <template #overlay>
              <a-menu>
                <a-menu-item key="1" @click="batchHandleDelete">
                  <Icon icon="ant-design:delete-outlined"></Icon>
                  删除
                </a-menu-item>
              </a-menu>
            </template>
            <a-button v-auth="'qe:coin_settlement_day:deleteBatch'"
              >批量操作
              <Icon icon="mdi:chevron-down"></Icon>
            </a-button>
          </a-dropdown>
          <!-- 高级查询 -->
          <super-query :config="superQueryConfig" @search="handleSuperQuery" />
        </template>
        <!--操作栏-->
        <template #action="{ record }">
          <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)" />
        </template>
        <!--字段回显插槽-->
        <template v-slot:bodyCell="{ column, record, index, text }"> </template>
      </BasicTable>
      <!--子表表格tab-->
      <a-tabs defaultActiveKey="1" style="margin: 10px">
        <a-tab-pane tab="机器人点数利润明细" key="1">
          <CoinSettlementDayDetailList />
        </a-tab-pane>
        <a-tab-pane tab="结算给市场的利润明细" key="2" forceRender>
          <CoinSettlementDayRewardList />
        </a-tab-pane>
      </a-tabs>
    </div>
    <!-- 表单区域 -->
    <CoinSettlementDayModal @register="registerModal" @success="handleSuccess"></CoinSettlementDayModal>
  </div>
</template>

<script lang="ts" name="qe-coinSettlementDay" setup>
  import { ref, reactive, computed, unref, provide } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { useModal } from '/@/components/Modal';
  import CoinSettlementDayModal from './components/CoinSettlementDayModal.vue';
  import { useUserStore } from '/@/store/modules/user';
  import CoinSettlementDayDetailList from './CoinSettlementDayDetailList.vue';
  import CoinSettlementDayRewardList from './CoinSettlementDayRewardList.vue';
  import { columns, searchFormSchema, superQuerySchema } from './CoinSettlementDay.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './CoinSettlementDay.api';
  import { downloadFile } from '/@/utils/common/renderUtils';
  const queryParam = reactive<any>({});
  //注册model
  const [registerModal, { openModal }] = useModal();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: '每日结算',
      api: list,
      columns,
      canResize: false,
      clickToRowSelect: true,
      rowSelection: { type: 'radio' },
      formConfig: {
        schemas: searchFormSchema,
        fieldMapToNumber: [],
        fieldMapToTime: [],
      },
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: (params) => {
        return Object.assign(params, queryParam);
      },
      pagination: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '20'],
      },
    },
    exportConfig: {
      name: '每日结算',
      url: getExportUrl,
      params: queryParam,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const userStore = useUserStore();
  const [registerTable, { reload }, { rowSelection, selectedRowKeys }] = tableContext;

  const mainId = computed(() => (unref(selectedRowKeys).length > 0 ? unref(selectedRowKeys)[0] : ''));
  //下发 mainId,子组件接收
  provide('mainId', mainId);

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  /**
   * 高级查询事件
   */
  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    reload();
  }

  /**
   * 新增事件
   */
  function handleAdd() {
    openModal(true, {
      isUpdate: false,
      showFooter: true,
    });
  }
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: true,
    });
  }
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    openModal(true, {
      record,
      isUpdate: true,
      showFooter: false,
    });
  }
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }
  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
        auth: 'qe:coin_settlement_day:edit',
      },
    ];
  }

  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      },
      {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
          placement: 'topLeft',
        },
        auth: 'qe:coin_settlement_day:delete',
      },
    ];
  }
</script>

<style lang="less" scoped>
  html[data-theme='light'] {
    .cgformErpList {
      height: 100%;
      .content {
        background-color: #fff;
        height: 100%;
      }
    }
  }

  :deep(.ant-picker),
  :deep(.ant-input-number) {
    width: 100%;
  }
</style>
