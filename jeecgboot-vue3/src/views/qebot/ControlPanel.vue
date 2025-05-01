<template>
  <a-layout style="height: 100vh">
    <a-layout-sider width="50%" theme="light">
      <a-layout-header style="background-color: white; padding: 0">
        <SearchHeader @search-success="searchSuccess" />
      </a-layout-header>
      <a-layout-content>
        <div class="left-content">
          <a-card title="机器人控制面板" size="small" :bordered="false">
            <a-card-grid
              @click="selectBot(b)"
              :class="{ 'selected-bot': currentBot?.id === b.id }"
              :title="'test'"
              style="width: 33%; padding: 5px; text-align: left"
              v-for="b in bot"
            >
              <a-row :wrap="true">
                <a-col :span="12">用户: {{ b.memberId_dictText }}</a-col>
                <a-col :span="12">交易对: {{ b.symbol }}</a-col>
              </a-row>
              <a-row>
                <a-col :span="12">持仓利润: {{ b.positionProfit }}</a-col>
                <a-col :span="12">总计利润: {{ b.profit }} </a-col>
              </a-row>
              <a-row>
                <a-col :span="12">投入: {{ b.totalInvest }}</a-col>
                <a-col :span="12">{{ b.status_dictText }}</a-col>
              </a-row>
              <a-row>
                <a-col :span="12">{{ b.tradeStatus_dictText }}</a-col>
              </a-row>
            </a-card-grid>
          </a-card>
        </div>
      </a-layout-content>
    </a-layout-sider>
    <a-layout>
      <div class="right-content">
        <a-layout-header style="background-color: white">
          <a-space> <a-menu @click="operateMenuClick" v-model:selectedKeys="current" mode="horizontal" :items="items" /> </a-space
        ></a-layout-header>
        <a-layout-content style="background-color: white">
          <div v-if="current == 'orders'">
            <OrderHistory :bot="currentBot" @click="controlFunction" />
          </div>
          <div v-if="current == 'podmanager'">
            <PodManager :bots="bot" />
          </div>
          <div v-if="current == 'trademanager'">
            <TradeManager :bot="currentBot" />
          </div>
          <div v-if="current == 'riskSetting'">
            <RiskManager :bot="currentBot" />
          </div>
          <div v-if="current == 'botSetting'">
            <CoinBotSetting @register="registerForm" :bot="currentBot" :isUpdate="true" />
          </div>
        </a-layout-content>
      </div>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" name="qe-coinBot" setup>
  import { h, ref } from 'vue';
  import SearchHeader from './components/Search.vue'; // 根据实际路径调整
  import OrderHistory from './components/OrderHistory.vue'; // 根据实际路径调整
  import PodManager from './components/PodManager.vue'; // 根据实际路径调整
  import RiskManager from './components/RiskManager.vue'; // 根据实际路径调整
  import TradeManager from './components/TradeManager.vue'; // 根据实际路径调整
  import CoinBotSetting from './components/CoinBotSetting.vue'; // 根据实际路径调整
  import { PlayCircleTwoTone, SettingTwoTone, FundTwoTone, SlidersTwoTone, EditTwoTone } from '@ant-design/icons-vue';
  import { MenuProps } from 'ant-design-vue';
  import { useForm } from '@/components/Form';
  import { formSchema } from '@/views/qebot/CoinBot.data';
  const current = ref<string[]>(['orders']);
  const bot = ref<Object>([]);
  const currentBot = ref<Object>({});
  const items = ref<MenuProps['items']>([
    {
      key: 'orders',
      icon: () => h(FundTwoTone),
      label: '历史成交',
      title: '历史成交',
    },
    {
      key: 'podmanager',
      icon: () => h(PlayCircleTwoTone),
      label: '启停',
      title: '启停',
    },
    {
      key: 'trademanager',
      icon: () => h(SettingTwoTone),
      label: '交易面板',
      title: '交易面板',
    },
    {
      key: 'riskSetting',
      icon: () => h(SlidersTwoTone),
      label: '风险对冲设置',
      title: '风险对冲设置',
    },
    {
      key: 'botSetting',
      icon: () => h(EditTwoTone),
      label: '机器人设置',
      title: '机器人设置',
    },
  ]);

  const [registerForm, { setProps, resetFields, setFieldsValue, validate, scrollToField }] = useForm({
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 12 },
  });

  const searchSuccess = (data) => {
    //计算持仓盈亏
    data.records.forEach((bot) => {
      if (bot['gridConfig']) {
        let botGrids = JSON.parse(bot['gridConfig']);
        let buy_order_num_all = 0;
        let buy_order_num_all_cost = 0;
        botGrids.forEach((grid) => {
          if (grid['buy_order_id']) {
            buy_order_num_all += grid['buy_order_num'];
            buy_order_num_all_cost += grid['buy_order_num'] * grid['buy_price'];
          }
        });
        let buy_order_num_all_current = buy_order_num_all * bot['currentPrice'];
        bot['positionProfit'] = (buy_order_num_all_current - buy_order_num_all_cost).toFixed(2);
      }
    });
    bot.value = data.records;

    currentBot.value = data.records[0];
  };
  const controlFunction = (data) => {
    console.log(data);
  };

  const selectBot = (bot) => {
    // console.log(bot);
    currentBot.value = bot;
  };

  const operateMenuClick = (menu) => {
    console.log(menu);
  };
</script>

<style scoped>
  .left-content,
  .right-content {
    height: 100%;
    font-size: 20px;
    background: #ffffff;
  }

  .left-content {
    background-color: #ffffff;
  }

  .right-content {
    background-color: #ffffff;
  }

  :deep(.ant-card-grid) {
    transition: all 0.3s;
    border: 1px solid #e8e8e8; /* 默认边框 */
  }

  :deep(.selected-bot) {
    border: 2px solid #1890ff !important; /* 选中时的蓝色边框 */
    box-shadow: 0 2px 8px rgba(24, 144, 255, 0.2);
    z-index: 1;
  }
</style>
