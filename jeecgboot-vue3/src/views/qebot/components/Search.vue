<template>
  <div class="search-container">
    <!-- 用户搜索 -->
    <a-select
      allowClear
      v-model:value="userSearch"
      show-search
      style="width: 300px"
      placeholder="用户"
      :options="userOptions"
      :filter-option="filterOption"
    />
    <!-- Symbol搜索 -->
    <a-select
      v-model:value="symbolSearch"
      show-search
      allowClear
      style="width: 300px"
      placeholder="交易对"
      :options="symbolOptions"
      :filter-option="filterOption"
    />
    <!-- 状态搜索 -->
    <a-select
      allowClear
      v-model:value="statusSearch"
      show-search
      style="width: 300px"
      placeholder="状态"
      :options="statusOptions"
      :filter-option="filterOption"
    />
    <a-space>
      <a-button type="primary" @click="onBotSearch" :loading="loading">搜索</a-button>
    </a-space>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref } from 'vue';
  import { initDictOptions } from '/@/utils/dict/index';
  import { list as botList } from '/@/views/qebot/CoinBot.api';
  interface Option {
    value: string;
  }

  export default defineComponent({
    name: 'SearchHeader',
    emits: ['searchSuccess'],
    setup(props, { emit }) {
      // 用户搜索状态
      const userSearch = ref<string>();
      const loading = ref<Boolean>();
      const userOptions = ref<Option[]>([]);
      // Symbol搜索状态
      const symbolSearch = ref<string>();
      const symbolOptions = ref<Option[]>([]);

      // 状态搜索状态
      const statusSearch = ref<string>();
      const statusOptions = ref<Option[]>([]);

      //初始用户搜索数据
      initDictOptions('sys_user,username,id').then((data) => {
        userOptions.value = data;
      });

      //初始化交易对搜索数据
      initDictOptions('coin_support,symbol,symbol').then((data) => {
        symbolOptions.value = data;
      });

      //初始机器人状态
      initDictOptions('bot_status').then((data) => {
        statusOptions.value = data;
      });
      const searchData = ref<Object>();
      //初始数据
      loading.value = true;
      botList({
        order: 'desc',
        pageNo: 1,
        pageSize: 10,
        // status: '2',
        memberId: '1895698783372677121',
      }).then((res) => {
        emit('searchSuccess', res);
        searchData.value = res;
        loading.value = false;
      });

      // 检索服务器数据
      const onBotSearch = (value: string) => {
        // column=createTime&order=desc&pageNo=1&pageSize=10&_t=1744439364503
        loading.value = true;
        botList({
          order: 'desc',
          pageNo: 1,
          pageSize: 10,
          memberId: userSearch.value,
          symbol: symbolSearch.value,
          status: statusSearch.value,
        }).then((res) => {
          emit('searchSuccess', res);
          searchData.value = res;
          loading.value = false;
        });
      };

      const filterOption = (input: string, option: any) => {
        return option.text.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      };

      return {
        userSearch,
        userOptions,
        symbolSearch,
        symbolOptions,
        statusSearch,
        statusOptions,
        onBotSearch,
        filterOption,
        searchData,
        loading,
      };
    },
  });
</script>

<style scoped>
  .search-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 100%;
  }
</style>
