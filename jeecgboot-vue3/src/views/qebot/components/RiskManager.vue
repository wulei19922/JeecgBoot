<template>
  <a-space style="width: 100%">
    <a-table :dataSource="futureOrders" :columns="binancePostionColumns">
      <template #title>当前持仓</template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.title === '操作'">
          <a-button type="link" @click="onBotOperate('restart', record)">
            <PlayCircleTwoTone :spin="record['loading'] == true" />
            重启
          </a-button>
          <a-button type="link" @click="onBotOperate('stop', record)">
            <PauseCircleFilled :spin="loading" />
            停止
          </a-button>

          <a-button type="link" @click="onBotOperate('start', record)">
            <PlayCircleTwoTone :spin="loading" />
            启动
          </a-button>
        </template>
      </template>
    </a-table>
  </a-space>

  <a-space style="width: 100%" direction="vertical">
    <a-table :dataSource="delagationOrders" :columns="botColumns">
      <template #title
        >当前委托
        <a-space style="margin-left: 10px" direction="horizontal">
          <PlusCircleTwoTone @click="addFutureDelegation()" />
          <SyncOutlined :spin="isRefrest" @click="refreshData" /> </a-space
      ></template>
    </a-table>
  </a-space>

  <a-space style="width: 100%">
    <a-table :dataSource="futuresIncome" style="width: 100%" :pagination="incomePage" :columns="incomeColumns" @change="handleIncomeTableChange">
      <template #title
        >持仓盈亏
        <a-space style="margin-left: 10px" direction="horizontal">
          <SyncOutlined :spin="isRefrest" @click="refreshData" />
          <a-date-picker style="width: 150px;" v-model:value="incomeDay" @change="daySelect" /> </a-space
      ></template>
    </a-table>
  </a-space>
  <a-space style="width: 100%">
    <a-table :dataSource="historyPositions" :columns="futureColumns" :pagination="historyPositionsPage" @change="handlePositionHisTableChange"  >
      <template #title
        >历史订单 <a-space style="margin-left: 10px" direction="horizontal"> <SyncOutlined :spin="isRefrest" @click="refreshData" /> </a-space
      ></template>
    </a-table>
  </a-space>

  <!-- 表单区域 -->
  <CoinBotFutureModal @register="registerModal" @success="handleSuccess" />
</template>

<script lang="ts">
  import { defineComponent, h, ref } from 'vue';
  import dayjs, { Dayjs } from 'dayjs';
  import { watch, onMounted } from 'vue';
  import { TableColumnsType } from 'ant-design-vue';
  import { initDictOptions } from '/@/utils/dict/index';
  import CoinBotFutureModal from './CoinBotFutureModal.vue';
  import {
    DownOutlined,
    CloudSyncOutlined,
    FileAddTwoTone,
    PlayCircleTwoTone,
    PlusCircleTwoTone,
    PauseCircleFilled,
    SyncOutlined,
  } from '@ant-design/icons-vue';
  import { Tooltip as aTooltip } from 'ant-design-vue/es/components';
  import { FormSchema } from '@/components/Form'; // 添加导入
  import { kafkaApi } from '/@/views/qebot/CoinBot.api';
  import { incomeBinance, list as futureList, listBinance } from '/@/views/qebot/CoinBotFuture.api';
  import { list as historyFutureList } from '/@/views/qebot/CoinBotFuturesOrder.api';
  import { columns as delegationColumn } from '/@/views/qebot/CoinBotFuture.data';
  import { binanceFututeColumns, binanceFututeIncomeColumns } from '/@/views/qebot/Binance.data';
  import { columns as futureOrderColumn } from '/@/views/qebot/CoinBotFuturesOrder.data';
  import { useModal } from '/@/components/Modal';
  import { List } from 'postcss/lib/list';
  import { property } from 'xe-utils';
  export default defineComponent({
    name: 'RiskManager',
    components: {
      PlayCircleTwoTone,
      CloudSyncOutlined,
      PauseCircleFilled,
      PlusCircleTwoTone,
      SyncOutlined,
      useModal,
      CoinBotFutureModal,
    },
    props: {
      bot: {
        type: Object,
        required: true,
        default: () => {},
      },
    },
    setup(props, { emit }) {
      //注册model
      const [registerModal, { openModal }] = useModal();
      const botColumns = delegationColumn;
      const futureColumns = futureOrderColumn;
      const incomeColumns = binanceFututeIncomeColumns;
      const binancePostionColumns = binanceFututeColumns;
      const statusOptions = ref<[]>([]);
      initDictOptions('bot_status').then((data) => {
        statusOptions.value = data;
      });
      const currentBot = ref<[]>([]);
      const dataSource = ref<[]>([]);
      const futureOrders = ref<[]>([]);
      const delagationOrders = ref<[]>([]);
      const historyPositions = ref<[]>([]);
      const futuresIncome = ref<[]>([]);
      const loading = ref(false);
      const isRefrest = ref(false);

      const dateFormat = 'YYYY-MM-DD';
      const incomeDay = ref<Dayjs>(dayjs());
      watch(
        () => props.bot,
        (bot) => {
          currentBot.value = bot;
          refreshData();
        },
        { deep: true, immediate: false }
      );
      onMounted(() => {
        // 在挂载时如果有初始bot值则加载数据
        console.log('更新机器人数据', props.bot);
        if (props.bot) {
          currentBot.value = props.bot;
          refreshData();
        }
      });
      const incomePage = ref<Object>({ total: 20, current: 1, pageSize: 2 });
      const historyPositionsPage = ref<Object>({ total: 20, current: 1, pageSize: 2 });
      const futuresIncomeParam = ref<Object>({});
      //查询历史仓位
      const futuresOrders = ref<Object>({});
      const refreshData = () => {
        //初始化委托数据
        isRefrest.value = true;
        const params = ref<Object>({});
        params.value = {
          botId: currentBot.value['id'],
          type: 'postion',
        };

        //查询持仓
        listBinance(params.value).then((res) => {
          futureOrders.value = res;
        });

        const delegationParams = ref<Object>({});
        delegationParams.value = {
          botId: currentBot.value['id'],
        };
        //委托数据
        delegationParams.value['orderStatus'] = 'NEW';
        futureList(delegationParams.value).then((res) => {
          delagationOrders.value = res['records'];
          isRefrest.value = false;
        });


        futuresOrders.value = {
          botId: currentBot.value['id'],
        };
        historyFutureList(futuresOrders.value).then((r) => {
          historyPositions.value = r.records;
          historyPositionsPage.value['pageSize'] = r.size;
          historyPositionsPage.value['total'] = r.total;
          historyPositionsPage.value['current'] = r.current;
        });
        futuresIncomeParam.value = {
          userId: currentBot.value['memberId'],
          symbol: currentBot.value['symbol'],
          day: incomeDay.value.valueOf(),
        };
        incomeBinance(futuresIncomeParam.value).then((r) => {
          incomePage.value['pageSize'] = 10;
          incomePage.value['total'] = r.length;
          incomePage.value['current'] = 1;
          futuresIncome.value = r;
        });
      };

      const onBotOperate = (status) => {
        const params = ref<Object>({});
        params.value['id'] = currentBot.value['id'];
        params.value['status'] = status;
        console.log('保存机器人', status);
      };

      /**
       * 添加委托
       */
      const addFutureDelegation = (record: Recordable) => {
        openModal(true, {
          record: { botId: currentBot.value['id'] },
          isUpdate: false,
          showFooter: true,
        });
      };

      const daySelect = (day) => {
        futuresIncomeParam.value = {
          userId: currentBot.value['memberId'],
          symbol: currentBot.value['symbol'],
          day: incomeDay.value.valueOf(),
        };
        incomeBinance(futuresIncomeParam.value).then((r) => {
          incomePage.value['pageSize'] = 10;
          incomePage.value['total'] = r.length;
          incomePage.value['current'] = 1;
          futuresIncome.value = r;
        });
      };
      const handleIncomeTableChange = (page) => {
        incomePage.value['current'] = page.current;
        incomePage.value['pageSize'] = page.pageSize;
      };
      const handlePositionHisTableChange = (page) => {
        futuresOrders.value['pageNo'] = page.current;
        historyFutureList(futuresOrders.value).then((r) => {
          historyPositions.value = r.records;
          historyPositionsPage.value['pageSize'] = r.size;
          historyPositionsPage.value['total'] = r.total;
          historyPositionsPage.value['current'] = r.current;
        });
      };

      /**
       * 成功回调
       */
      const handleSuccess = () => {
        console.log('处理回调信息');
      };
      return {
        loading,
        onBotOperate,
        botColumns,
        dataSource,
        futureColumns,
        binancePostionColumns,
        futureOrders,
        delagationOrders,
        registerModal,
        openModal,
        handleSuccess,
        addFutureDelegation,
        refreshData,
        isRefrest,
        historyPositions,
        incomeColumns,
        futuresIncome,
        incomePage,
        historyPositionsPage,
        daySelect,
        incomeDay,
        handleIncomeTableChange,
        handlePositionHisTableChange,
      };
    },
  });
</script>

<script setup lang="ts"></script>
<style scoped>
  .search-container {
    margin-top: 20px;
    margin-left: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
  }
  ::v-deep(.ant-table) {
    font-size: 11px;
  }
  ::v-deep(.ant-space-item) {
    width: 100%;
  }
  ::v-deep(.ant-table-thead > tr > th) {
    font-size: 11px;
  }
</style>
