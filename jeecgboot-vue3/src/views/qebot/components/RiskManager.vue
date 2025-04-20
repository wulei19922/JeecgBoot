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
          <SyncOutlined spin /> </a-space
      ></template>
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

  <a-space style="width: 100%">
    <a-table :dataSource="dataSource" :columns="futureColumns">
      <template #title>历史仓位</template>
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

  <!-- 表单区域 -->
  <CoinBotFutureModal @register="registerModal" @success="handleSuccess" />
</template>

<script lang="ts">
  import { defineComponent, h, ref } from 'vue';
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
  import { list as futureList, listBinance } from '/@/views/qebot/CoinBotFuture.api';
  import { columns as delegationColumn, binanceFututeColumns } from '/@/views/qebot/CoinBotFuture.data';
  import { columns as futureOrderColumn } from '/@/views/qebot/CoinBotFuturesOrder.data';
  import { useModal } from '/@/components/Modal';
  import { List } from 'postcss/lib/list';
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
      const binancePostionColumns = binanceFututeColumns;
      const statusOptions = ref<[]>([]);
      initDictOptions('bot_status').then((data) => {
        statusOptions.value = data;
      });
      const currentBot = ref<[]>([]);
      const dataSource = ref<[]>([]);
      const futureOrders = ref<[]>([]);
      const delagationOrders = ref<[]>([]);
      const loading = ref(false);
      watch(
        () => props.bot,
        (bots) => {
          currentBot.value = bots;
        },
        { deep: true, immediate: false }
      );
      onMounted(() => {
        // 在挂载时如果有初始bot值则加载数据
        if (props.bot) {
          currentBot.value = props.bot;
          //初始化委托数据
          const params = ref<Object>({});
          params.value = {
            botId: currentBot.value['id'],
            type: 'postion',
          };

          //查询持仓
          listBinance(params.value).then((res) => {
            futureOrders.value = res;
          });

          params.value = {
            botId: currentBot.value['id'],
            type: 'openorders',
          };
          //创建委托
          // listBinance(params.value).then((res) => {
          //   delagationOrders.value = res;
          // });
        }
      });
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
          isUpdate: false,
          showFooter: true,
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
  ::v-deep(.ant-table-thead > tr > th) {
    font-size: 11px;
  }
</style>
