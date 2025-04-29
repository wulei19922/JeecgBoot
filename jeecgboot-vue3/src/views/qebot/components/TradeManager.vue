<template>
  <a-divider style="border-color: darkgrey" orientation="left" dashed>机器人操作</a-divider>
  <!--  <a-space style="width: 100%; margin-left: 10px">-->
  <!--    <a-input-number v-model:value="currentBot['startBuyPrice']">-->
  <!--      <template #addonAfter>-->
  <!--        <SaveTwoTone @click="saveConfig()" />-->
  <!--      </template>-->
  <!--      <template #addonBefore>指定买入价格</template>-->
  <!--    </a-input-number>-->
  <!--  </a-space>-->
  <div class="search-container">
    <a-space>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('SELL_ALL')">平仓</a-button>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('ONLY_SELL')">只卖不买</a-button>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('STOP_TRADE')">停止交易</a-button>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('TRADE')">启动交易</a-button>
    </a-space>
  </div>
  <a-divider style="border-color: darkgrey" orientation="left" dashed>合约账户</a-divider>
  <a-space style="width: 100%; margin: 16px 0">
    <a-input-number v-model:value="transferAmount" placeholder="划转金额" :min="0" :precision="2" style="width: 200px">
      <template #addonBefore>金额</template>
      <template #addonAfter>
        <a-button type="primary" size="small" @click="handleTransfer('tofutures')">划转</a-button>
        <a-button style="margin-left: 5px" type="primary" size="small" @click="getWallet('tofutures')">刷新</a-button>
      </template>
    </a-input-number>


  </a-space>
  <!--  账户余额-->
  <a-list :grid="{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 6 }" :data-source="futuresAccount">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card :title="item.asset">{{ item.availableBalance }}</a-card>
      </a-list-item>
    </template>
  </a-list>
  <a-divider style="border-color: darkgrey" orientation="left" dashed>现货账户</a-divider>
  <a-space style="width: 100%; margin: 16px 0">
    <a-input-number v-model:value="transferAmount" placeholder="划转金额" :min="0" :precision="2" style="width: 200px">
      <template #addonBefore>金额</template>
      <template #addonAfter>
        <a-button type="primary" size="small" @click="handleTransfer('tospot')">划转</a-button>
        <a-button type="primary" style="margin-left: 5px" size="small" @click="getWallet">刷新</a-button>
      </template>
    </a-input-number>
  </a-space>

  <!--  账户余额-->
  <a-list :grid="{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 6 }" :data-source="spotAccount">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card :title="item.asset">{{ item.free }}</a-card>
      </a-list-item>
    </template>
  </a-list>
</template>

<script lang="ts">
  import { defineComponent, h, ref } from 'vue';
  import { watch, onMounted } from 'vue';
  import { message, notification, TableColumnsType } from "ant-design-vue";
  import { initDictOptions } from '/@/utils/dict/index';
  import { Tooltip as aTooltip } from 'ant-design-vue/es/components';
  import { FormSchema } from '@/components/Form'; // 添加导入
  import { kafkaApiPod, walletApi, transferApi } from '/@/views/qebot/CoinBot.api';
  import { List } from 'postcss/lib/list';
  import { SettingTwoTone, SaveTwoTone, SyncOutlined } from '@ant-design/icons-vue';
  interface DataItem {
    title: string;
  }
  export default defineComponent({
    name: 'TradeManager',
    components: {
      SyncOutlined,
      aTooltip,
      SettingTwoTone,
      SaveTwoTone,
    },
    props: {
      bot: {
        type: Object,
        required: true,
        default: () => {},
      },
    },
    setup(props, { emit }) {
      const currentBot = ref<[]>([]);
      const transferAmount = ref<number>(0);

      const loading = ref(false);
      watch(
        () => props.bot,
        (bot) => {
          currentBot.value = bot;
          getWallet();
        },
        { deep: true, immediate: false }
      );
      onMounted(() => {
        // 在挂载时如果有初始bot值则加载数据
        if (props.bot) {
          currentBot.value = props.bot;
          console.log('当前机器人0', currentBot.value);
        }
        getWallet();
      });
      const onBotOperate = (status) => {
        loading.value = true;
        const params = ref<Object>({});
        params.value['ids'] = currentBot.value['id'];
        params.value['status'] = status;
        console.log('当前机器人1', currentBot.value);
        kafkaApiPod(params.value).then((res) => {
          loading.value = false;
        });
        loading.value = false;
      };

      const saveConfig = (status) => {
        loading.value = true;
        const params = ref<Object>({});
        params.value['ids'] = currentBot.value['id'];
        params.value['status'] = status;
        console.log('保存机器人', currentBot.value['startBuyPrice']);
      };
      const futuresAccount = ref<DataItem[]>([]);
      const spotAccount = ref<DataItem[]>([]);
      const FundsAccount = ref<DataItem[]>([]);
      const getWallet = () => {
        const params = ref<Object>({});
        params.value['userId'] = currentBot.value['memberId'];
        walletApi(params.value).then((res) => {
          console.log('钱包', res);
          futuresAccount.value = res['futures'];
          spotAccount.value = res['spot'];
        });
      };
      const handleTransfer = (type) => {
        console.log('划转金额:', transferAmount.value);
        const params = ref<Object>({});
        params.value['userId'] = currentBot.value['memberId'];
        params.value['amount'] = transferAmount.value;
        params.value['type'] = type;
        transferApi(params.value).then((res) => {
          notification.success({
            message: '划转完成',
            description: '',
          });
          getWallet();
        });
      };
      const onBotStop = () => {};
      const onBotPause = () => {};

      return {
        loading,
        onBotOperate,
        onBotStop,
        onBotPause,
        currentBot,
        saveConfig,
        futuresAccount,
        spotAccount,
        FundsAccount,
        transferAmount,
        handleTransfer,
        getWallet,
      };
    },
  });
</script>

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
