<template>
  <div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
        <a-button type="primary" v-auth="'qe:coin_bot:add'" @click="handleControlPanel" preIcon="ant-design:plus-outlined"> 控制面板 </a-button>
        <a-button type="primary" v-auth="'qe:coin_bot:add'" @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增 </a-button>
        <a-button type="primary" v-auth="'qe:coin_bot:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出 </a-button>
        <j-upload-button type="primary" v-auth="'qe:coin_bot:importExcel'" preIcon="ant-design:import-outlined" @click="onImportXls"
          >导入
        </j-upload-button>
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleStartOrStop('start')">
                <Icon icon="ant-design:delete-outlined" />
                启动机器人
              </a-menu-item>
              <a-menu-item key="1" @click="batchHandleStartOrStop('stop')">
                <Icon icon="ant-design:delete-outlined" />
                停止机器人(平仓)
              </a-menu-item>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined" />
                删除
              </a-menu-item>
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
      <template #bodyCell="{ column, record, index, text }"></template>
    </BasicTable>
    <!-- 表单区域 -->
    <CoinBotModal @register="registerModal" @success="handleSuccess" />

    <div>
      <a-modal v-model:open="open" width="100%" wrap-class-name="full-modal" title="动态调仓" @ok="handleOk">
        <a-row>
          <a-col :span="8" style="margin-right: 10px">
            <span>网格参数</span>
            <a-table :columns="gridColumns" :data-source="currentBotGrid" :pagination="false" />
          </a-col>
          <a-col :span="15">
            <span>匹配订单</span>
            <a-table :columns="orderColumns" @change="handleChange" :pagination="pagination" :data-source="currentOrder">
              <template #bodyCell="{ column, record }">
                <template v-if="column.dataIndex === 'silder'">
                  <a v-if="record[column.dataIndex] === 'SELL'" style="color: red"> 卖 </a>
                  <a v-else-if="record[column.dataIndex] === 'BUY'" style="color: green"> 买 </a>
                </template>
              </template>
            </a-table>
          </a-col>
        </a-row>
      </a-modal>
    </div>

    <div>
      <a-modal v-model:open="openPostionEdit" width="100%" wrap-class-name="full-modal" title="机器人参数" @ok="handlePositonOk">
        <a-row>
          <a-col :span="8" style="margin-right: 10px">
            <span>网格参数</span>
            <a-table :columns="gridColumns" @change="handleChangePisition" :data-source="currentBotGrid" :pagination="paginationGride" />
          </a-col>
          <a-col :span="15">
            <span>调整参数</span>
            <a-form
              :model="positionFormState"
              name="basic"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
              autocomplete="off"
              @finish="onFinish"
              @finish-failed="onFinishFailed"
            >
              <a-form-item label="已有网格数" name="current_grid" :rules="[{ required: true, message: '已有网格' }]">
                <a-input v-model:value="positionFormState.current_grid" />
              </a-form-item>

              <a-form-item label="向下扩展网格" name="down" :rules="[{ required: true, message: '输入向下扩展网格数' }]">
                <a-input v-model:value="positionFormState.down" />
              </a-form-item>

              <a-form-item label="向上扩展网格" name="up" :rules="[{ required: true, message: '输入向上扩展网格数' }]">
                <a-input v-model:value="positionFormState.up" />
              </a-form-item>

              <a-form-item label="追加投资" name="addInvest" :rules="[{ required: true, message: '需要追加投资' }]">
                <a-input v-model:value="positionFormState.addInvest" />
              </a-form-item>
              <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
                <a-button type="primary" @click="calculateGride" html-type="submit">计算参数 </a-button>
              </a-form-item>
            </a-form>
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
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl, batchOperate, editConfigApi } from './CoinBot.api';
  import { list as orderList } from '../coinOrder/CoinOrder.api';

  import { downloadFile, render } from '/@/utils/common/renderUtils';
  import { useUserStore } from '/@/store/modules/user';
  import msg from '@/views/demo/feat/msg/index.vue';
  import { notification, TableColumnsType } from 'ant-design-vue';
  import HeadInfo from '@/components/chart/HeadInfo.vue';
  import { useRouter } from 'vue-router';
  import { useMessage } from '@/hooks/web/useMessage';
  let router = useRouter();
  const { NotifyApi } = useMessage();

  //动态调仓区域

  interface positionForm {
    down: number;
    up: number;
    current_grid: number;
    addInvest: number;
  }

  const positionFormState = reactive<positionForm>({
    down: 0,
    up: 0,
    current_grid: 20,
    addInvest: 0,
  });
  const onFinish = (values: any) => {
    console.log('Success:', values);
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };
  //计算调整后的网格

  const calculateGride = () => {
    currentBotGrid.value = JSON.parse(currentBot.value['gridConfig']);
    const currentPrice = currentBot.value['currentPrice'];
    let oldGrid = [...currentBotGrid.value].sort((a, b) => a['buy_price'] - b['buy_price']);
    //向下调整网格
    const grideProfit = currentBot.value['grideProfit'];
    const addGrid = [];

    //向下添加网格
    if (positionFormState.down > 0) {
      const firstGrid = oldGrid[0];
      let addInvest = 0;
      for (let i = 1; i <= positionFormState.down; i++) {
        let addGridItem = {
          buy_price: firstGrid['buy_price'] * (1 - grideProfit) ** i,
          buy_order_num: currentBot.value['perOrder'],
        };
        addInvest += addGridItem['buy_price'] * addGridItem['buy_order_num'];
        if (i == 1) {
          addGridItem['sell_price'] = firstGrid['buy_price'];
        } else {
          addGridItem['sell_price'] = firstGrid['buy_price'] * (1 - grideProfit) ** (i - 1);
        }
        addGridItem['buy_order_id'] = '';
        addGridItem['sell_order_id'] = '';
        addGrid.push(addGridItem);
      }
      const newGrid = addGrid.concat(oldGrid);
      const addNewGrid = [...newGrid].sort((a, b) => {
        return a['buy_price'] - b['buy_price'];
      });
      console.log(addNewGrid);
      positionFormState.addInvest = addInvest;
      currentBotGrid.value = addNewGrid;
    }

    //向上添加网格
    if (positionFormState.up > 0) {
      const firstGrid = oldGrid[oldGrid.length - 1];
      let addInvest = 0;
      for (let i = 1; i <= positionFormState.up; i++) {
        let addGridItem = {
          buy_price: firstGrid['buy_price'] * (1 + grideProfit) ** i,
          buy_order_num: currentBot.value['perOrder'],
        };
        addInvest += addGridItem['buy_price'] * addGridItem['buy_order_num'];
        if (i == 1) {
          addGridItem['sell_price'] = firstGrid['buy_price'];
        } else {
          addGridItem['sell_price'] = firstGrid['buy_price'] * (1 + grideProfit) ** (i - 1);
        }
        addGridItem['buy_order_id'] = '';
        addGridItem['sell_order_id'] = '';
        addGrid.push(addGridItem);
      }
      const newGrid = addGrid.concat(oldGrid);
      const addNewGrid = [...newGrid].sort((a, b) => {
        return a['buy_price'] - b['buy_price'];
      });
      positionFormState.addInvest = addInvest;
      console.log(addNewGrid);
      paginationGride.value['total'] = addNewGrid.length;
      currentBotGrid.value = addNewGrid;
    }
  };

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
      minWidth: 30,
      width: 50,
    },
    {
      title: '买入数量',
      dataIndex: 'buy_order_num',
      key: 'buy_order_num',
      minWidth: 30,
      maxWidth: 50,
    },
    {
      title: '买入价格',
      dataIndex: 'buy_price',
      key: 'buy_price',
      minWidth: 30,
      maxWidth: 50,
      customRender: ({ text }) => {
        if (text) {
          return parseFloat(text).toFixed(8);
        }
        return text;
      },
    },
    {
      title: '卖出订单',
      key: 'sell_order_id',
      dataIndex: 'sell_order_id',
      minWidth: 30,
      maxWidth: 50,
    },
    {
      title: '卖出价格',
      key: 'sell_price',
      dataIndex: 'sell_price',
      customRender: ({ text }) => {
        if (text) {
          return parseFloat(text).toFixed(8);
        }
        return text;
      },
      minWidth: 30,
      maxWidth: 50,
    },
  ]);
  const orderColumns = ref<TableColumnsType>([
    {
      title: '创建时间',
      align: 'center',
      dataIndex: 'createTime',
    },
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
      customRender: ({ text }) => {
        if (text) {
          return parseFloat(text).toFixed(8);
        }
        return text;
      },
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
      customRender: ({ text }) => {
        if (text) {
          return parseFloat(text).toFixed(8);
        }
        return text;
      },
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
    // {
    //   title: '币安订单ID',
    //   align: 'center',
    //   dataIndex: 'orderId',
    // },
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
  const openPostionEdit = ref<boolean>(false);
  const currentBotGrid = ref<Object>({});
  const currentBot = ref<Object>({});
  const currentOrder = ref<Object>(false);
  const params = ref<Object>({
    column: 'createTime',
    order: 'desc',
    pageNo: 1,
    pageSize: 10,
    botId: '1895698783372677121_dogeusdt_BINANCE_spot_gride',
  });
  const pagination = ref<Object>({ total: 10, current: 1, pageSize: 10 });
  //网格参数调仓
  const paginationGride = ref<Object>({ total: 10, current: 1, pageSize: 10 });
  const showBuyModal = (record) => {
    if (!record.gridConfig) {
      notification.error({
        message: '提示',
        description: '机器人初始化中！',
      });
      return;
    }
    currentBotGrid.value = JSON.parse(record.gridConfig);
    currentBot.value = record;
    //请求匹配订单
    params.value['botId'] = record['instanceName'];
    orderList(params.value).then((res) => {
      pagination.value.total = res.total;
      pagination.value.current = res.current;
      pagination.value.pageSize = res.size;
      currentOrder.value = res.records;
    });
    open.value = true;
  };
  const showPostionEdit = (record) => {
    openPostionEdit.value = true;
    let tmp = JSON.parse(record.gridConfig);
    const sortTmp = [...tmp].sort((a, b) => a['buy_price'] - b['buy_price']);
    currentBotGrid.value = sortTmp;
    currentBot.value = record;
    paginationGride.value['total'] = sortTmp.length;
    paginationGride.value['current'] = 1;
    paginationGride.value['pageSize'] = 10;
    positionFormState.addInvest = 0;
    positionFormState.up = 0;
    positionFormState.down = 0;
    positionFormState.current_grid = sortTmp.length;
  };

  const handleOk = (e: MouseEvent) => {
    open.value = false;
  };

  const handlePositonOk = (e: MouseEvent) => {
    openPostionEdit.value = false;
    console.log('保存配置', currentBotGrid.value, positionFormState.addInvest);
    const param = {
      gridConfig: JSON.stringify(currentBotGrid.value),
      addInvest: positionFormState.addInvest,
      id: currentBot.value['id'],
    };
    editConfigApi(param).then((res) => {
      if (res.success) {
      } else {
      }
    });
  };

  const handleChangePisition = (data) => {
    paginationGride.value['total'] = 20;
    paginationGride.value['current'] = data.current;
    paginationGride.value['pageSize'] = 10;
  };

  const handleChange = (data) => {
    params.value['pageNo'] = data.current;
    params.value['pageSize'] = data.pageSize;
    params.value['pageSize'] = data.pageSize;
    orderList(params.value).then((res) => {
      pagination.value['total'] = res.total;
      pagination.value['current'] = res.current;
      pagination.value['pageSize'] = res.size;
      console.log(pagination.value);
      currentOrder.value = res.records;
    });
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
   *控制面板
   */

  function handleControlPanel(record) {
    router.push('/panel');
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
        label: '动态调仓',
        onClick: showPostionEdit.bind(null, record),
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
