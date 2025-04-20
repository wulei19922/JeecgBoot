<template>
  <a-space style="width: 100%">
    <a-table :dataSource="dataSource" :columns="botColumns">
      <template #title>当前委托</template>
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
</template>

<script lang="ts">
  import { defineComponent, h, ref } from 'vue';
  import { watch, onMounted } from 'vue';
  import { TableColumnsType } from 'ant-design-vue';
  import { initDictOptions } from '/@/utils/dict/index';
  import { DownOutlined, CloudSyncOutlined, LoadingOutlined, PlayCircleTwoTone, PauseCircleTwoTone, PauseCircleFilled } from '@ant-design/icons-vue';
  import { Tooltip as aTooltip } from 'ant-design-vue/es/components';
  import { FormSchema } from '@/components/Form'; // 添加导入
  import { kafkaApi } from '/@/views/qebot/CoinBot.api';
  import { List } from 'postcss/lib/list';
  export default defineComponent({
    name: 'PodManager',
    components: {
      PlayCircleTwoTone,
      CloudSyncOutlined,
      PauseCircleFilled,
    },
    props: {
      bots: {
        type: Object,
        required: true,
        default: () => {},
      },
    },
    setup(props, { emit }) {
      const botColumns = ref<TableColumnsType>([
        {
          title: '用户',
          align: 'center',
          dataIndex: 'memberId_dictText',
          width: 100,
        },
        {
          title: '交易对',
          align: 'center',
          dataIndex: 'symbol',
          width: 100,
        },
        {
          title: '当前状态',
          align: 'center',
          dataIndex: 'status_dictText',
          width: 100,
        },
        {
          title: '操作',
          align: 'center',
          key: 'operate',
          dataIndex: 'operate',
          width: 300,
        },
      ]);
      const statusOptions = ref<[]>([]);
      initDictOptions('bot_status').then((data) => {
        statusOptions.value = data;
      });

      const dataSource = ref<[]>([]);

      const loading = ref(false);
      watch(
        () => props.bots,
        (bots) => {
          dataSource.value = bots;
        },
        { deep: true, immediate: false }
      );
      onMounted(() => {
        // 在挂载时如果有初始bot值则加载数据
        //
        if (props.bots?.length > 0) {
          dataSource.value = props.bots;
        }
      });
      const onBotOperate = (status, record) => {
        record['loading'] = true;
        const params = ref<Object>({});
        params.value['ids'] = record['id'];
        params.value['status'] = status;
        kafkaApi(params.value).then((res) => {
          record['loading'] = false;
          record['status'] = status;
          let tmp = statusOptions.value.filter((d) => d['value'] == status);
          record['status_dictText'] = tmp && tmp.length > 0 ? tmp[0]['text'] : '';
        });
      };
      const onBotStop = () => {};
      const onBotPause = () => {};

      return { loading, onBotOperate, onBotStop, onBotPause, botColumns, dataSource };
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
