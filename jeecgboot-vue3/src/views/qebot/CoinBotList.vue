<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" v-auth="'qe:coin_bot:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>
        <a-button type="primary" v-auth="'qe:coin_bot:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>
        <j-upload-button type="primary" v-auth="'qe:coin_bot:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls"
          >导入</j-upload-button
        >
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleStartOrStop('start')"> <Icon icon="ant-design:delete-outlined" />启动机器人 </a-menu-item>
              <a-menu-item key="1" @click="batchHandleStartOrStop('stop')"> <Icon icon="ant-design:delete-outlined" />停止机器人(平仓) </a-menu-item>
              <a-menu-item key="1" @click="batchHandleDelete"> <Icon icon="ant-design:delete-outlined" />删除 </a-menu-item>
            </a-menu>
          </template>
          <a-button v-auth="'qe:coin_bot:deleteBatch'"
            >批量操作
            <Icon icon="mdi:chevron-down" />
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
      <template #bodyCell="{ column, record, index, text }"> </template>
    </BasicTable>
    <!-- 表单区域 -->
    <CoinBotModal @register="registerModal" @success="handleSuccess" />

    <div>
      <a-modal v-model:open="open" width="100%" wrap-class-name="full-modal" title="机器人参数" @ok="handleOk">
        <a-row>
          <a-col :span="8">
            <span>网格参数</span>
            <a-table :columns="gridColumns" :data-source="currentBot" :pagination="false" @resize-column="handleResizeColumn">
            </a-table>
          </a-col>
          <a-col :span="16">
            <span>匹配订单</span>
            <a-table :columns="orderColumns" :data-source="currentOrder" :pagination="false" @resize-column="handleResizeColumn">
              <template #headerCell="{ column }">
                <template v-if="column.key === 'buy_order_num'">
                  <span>
                    <smile-outlined />
                    买入数量
                  </span>
                </template>
              </template>

              <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'buy_price'">
                  <a>
                    {{ record.buy_price }}
                  </a>
                </template>
                <template v-else-if="column.key === 'tags'">
                  <span>
                    <a-tag v-for="tag in record.tags" :key="tag" :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'">
                      {{ tag.toUpperCase() }}
                    </a-tag>
                  </span>
                </template>
                <template v-else-if="column.key === 'action'">
                  <span>
                    <a>Invite 一 {{ record.name }}</a>
                    <a-divider type="vertical" />
                    <a>Delete</a>
                    <a-divider type="vertical" />
                    <a class="ant-dropdown-link">
                      More actions
                      <down-outlined />
                    </a>
                  </span>
                </template>
              </template>
            </a-table>
          </a-col>
        </a-row>
      </a-modal>
    </div>
  </div>
</template>

<script lang="ts" name="qe-coinBot" setup>
  import { ref, reactive, computed, unref } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useModal } from '/@/components/Modal';
  import { useListPage } from '/@/hooks/system/useListPage';
  import CoinBotModal from './components/CoinBotModal.vue';
  import { columns, searchFormSchema, superQuerySchema } from './CoinBot.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, batchOperate } from './CoinBot.api';
  import { list as orderList } from '../coinOrder/CoinOrder.api';

  import { downloadFile } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';
  import msg from '@/views/demo/feat/msg/index.vue';
  import { TableColumnsType } from 'ant-design-vue';
  import HeadInfo from "@/components/chart/HeadInfo.vue";
  const queryParam = reactive<any>({});
  const checkedKeys = ref<Array<string | number>>([]);
  const userStore = useUserStore();
  //注册model
  const [registerModal, { openModal }] = useModal();
  const baseStyle: CSSProperties = {
    width: '25%',
    height: '54px',
  };
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: '机器人列表',
      api: list,
      columns,
      canResize: false,
      formConfig: {
        //labelWidth: 120,
        schemas: searchFormSchema,
        autoSubmitOnEnter: true,
        showAdvancedButton: true,
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
    },
    exportConfig: {
      name: '机器人列表',
      url: getExportUrl,
      params: queryParam,
    },
    importConfig: {
      url: getImportUrl,
      success: handleSuccess,
    },
  });
  const gridColumns = ref<TableColumnsType>([
    {
      title: '买入订单',
      dataIndex: 'buy_order_id',
      key: 'buy_order_id',
      resizable: true,
      width: 50,
    },
    {
      title: '买入数量',
      dataIndex: 'buy_order_num',
      key: 'buy_order_num',
      resizable: true,
      minWidth: 30,
      maxWidth: 50,
    },
    {
      title: '买入价格',
      dataIndex: 'buy_price',
      key: 'buy_price',
      maxWidth: 50,
    },
    {
      title: '卖出订单',
      key: 'sell_order_id',
      dataIndex: 'sell_order_id',
      maxWidth: 50,
    },
    {
      title: '卖出价格',
      key: 'sell_price',
      dataIndex: 'sell_price',
      maxWidth: 50,
    },
  ]);
  const orderColumns = ref<TableColumnsType>([
    {
      title: '方向',
      align: 'center',
      dataIndex: 'silder',
    },
    {
      title: '	订单类型',
      align: 'center',
      dataIndex: 'orderType',
    },
    {
      title: '	成交均价',
      align: 'center',
      dataIndex: 'avgPrice',
    },
    {
      title: '成交数量',
      align: 'center',
      dataIndex: 'num',
    },
    {
      title: '成交价格',
      align: 'center',
      dataIndex: 'price',
    },
    // {
    //   title: '机器人ID',
    //   align: 'center',
    //   dataIndex: 'botId_dictText',
    // },
    {
      title: '状态',
      align: 'center',
      dataIndex: 'status_dictText',
    },
    {
      title: '币安订单ID',
      align: 'center',
      dataIndex: 'orderId',
    },
    {
      title: '交易对',
      align: 'center',
      dataIndex: 'symbol',
    },
    {
      title: '匹配对',
      align: 'center',
      dataIndex: 'matchId',
    },
  ]);

  const [registerTable, { reload }, { rowSelection, selectedRowKeys }] = tableContext;

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  const open = ref<boolean>(false);
  const currentBot = ref<Object>(false);
  const currentOrder = ref<Object>(false);
  const showBuyModal = (record) => {
    currentBot.value = JSON.parse(record.gridConfig);
    let params = {
      column: 'createTime',
      order: 'desc',
      pageNo: 1,
      pageSize: 10,
      botId: '1895698783372677121_dogeusdt_BINANCE_spot_gride',
    };
    //请求匹配订单
    orderList(params).then((res) => {
      currentOrder.value = res.records;
    });
    console.log(currentBot.value);
    open.value = true;
  };

  const handleOk = (e: MouseEvent) => {
    console.log(e);
    open.value = false;
  };

  /*
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
   * 批量操作机器人
   */
  async function batchHandleStartOrStop(message) {
    let msg = '';
    if (message == 'start') {
      msg = '是否启动选中机器人，该操作执行后，将会启动机器人进行量化交易';
    }

    if (message == 'stop') {
      msg = '是否停止选中机器人，该操作执行后，机器人将会停止量化交易';
    }

    await batchOperate({ ids: selectedRowKeys.value, message: msg, type: message }, handleSuccess);
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
        auth: 'qe:coin_bot:edit',
      },
    ];
  }
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '买入明细',
        onClick: showBuyModal.bind(null, record),
      },
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
        auth: 'qe:coin_bot:delete',
      },
    ];
  }
</script>

<style lang="less" scoped>
  :deep(.ant-picker),
  :deep(.ant-input-number) {
    width: 100%;
  }
</style>
