<template>
  <a-space style="width: 100%; margin-left: 10px">
    <a-input-number v-model:value="currentBot['startBuyPrice']">
      <template #addonAfter>
        <SaveTwoTone @click="saveConfig()" />
      </template>
      <template #addonBefore>指定买入价格</template>
    </a-input-number>
  </a-space>

  <div class="search-container">
    <a-space>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('SELL_ALL')">平仓</a-button>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('ONLY_SELL')">只卖不买</a-button>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('STOP_TRADE')">停止交易</a-button>
      <a-button type="default" size="small" :loading="loading" @click="onBotOperate('TRADE')">启动交易</a-button>
    </a-space>
  </div>
</template>

<script lang="ts">
  import { defineComponent, h, ref } from 'vue';
  import { watch, onMounted } from 'vue';
  import { TableColumnsType } from 'ant-design-vue';
  import { initDictOptions } from '/@/utils/dict/index';
  import { Tooltip as aTooltip } from 'ant-design-vue/es/components';
  import { FormSchema } from '@/components/Form'; // 添加导入
  import { kafkaApiPod } from '/@/views/qebot/CoinBot.api';
  import { List } from 'postcss/lib/list';
  import { SettingTwoTone, SaveTwoTone } from '@ant-design/icons-vue';
  export default defineComponent({
    name: 'TradeManager',
    components: {
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

      const loading = ref(false);
      watch(
        () => props.bot,
        (bot) => {
          currentBot.value = bot;
        },
        { deep: true, immediate: false }
      );
      onMounted(() => {
        // 在挂载时如果有初始bot值则加载数据
        //
        if (props.bot) {
          currentBot.value = props.bot;
          console.log('当前机器人0', currentBot.value);
        }
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
      const onBotStop = () => {};
      const onBotPause = () => {};

      return { loading, onBotOperate, onBotStop, onBotPause, currentBot, saveConfig };
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
