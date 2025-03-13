<template>
  <a-layout>
    <a-layout-sider class="a-layout-sider">
      <!-- 左侧区域 -->
      <a-list :data-source="paginatedDates" :pagination="false">
        <template #renderItem="{ item }">
          <a-list-item>{{ item }}</a-list-item>
        </template>
      </a-list>
      <a-pagination
        v-model:current="pagination.current"
        :page-size="pagination.pageSize"
        :total="pagination.total"
        @change="pagination.onChange"
        style="margin-top: 16px; text-align: center"
      />
    </a-layout-sider>
    <a-layout>
      <a-layout-content>
        <!-- 中间区域 -->
        <div style="display: flex; justify-content: space-around; margin-bottom: 16px">
          <a-card title="运行中的机器人" style="width: 23%">
            <p>托管总资金 30000U</p>
            <p>机器人数量 300</p>
          </a-card>
          <a-card title="今日利润" style="width: 23%">
            <p>200U</p>
          </a-card>
          <a-card title="亏损机器人" style="width: 23%">
            <p>亏损机器人数量 10</p>
            <p>亏损U 10</p>
          </a-card>
          <a-card title="结算金额" style="width: 23%">
            <p>待结算金额：100</p>
            <p>已结算金额：100</p>
          </a-card>
        </div>
        <div>
          <!-- 中间区域中间部分 -->
          <a-table :columns="columns" :data-source="paginatedTableData" style="height: 400px; overflow-y: auto" />
          <a-pagination
            v-model:current="tablePagination.current"
            :page-size="tablePagination.pageSize"
            :total="tablePagination.total"
            @change="tablePagination.onChange"
            style="margin-top: 16px; text-align: center"
          />
        </div>
        <div>
          <!-- 中间区域底部部分 -->
          <!--          <a-table :columns="bottomColumns" :data-source="paginatedBottomTableData" style="height: 400px; overflow-y: auto;" />-->
          <!--          <a-pagination-->
          <!--            v-model:current="bottomTablePagination.current"-->
          <!--            :page-size="bottomTablePagination.pageSize"-->
          <!--            :total="bottomTablePagination.total"-->
          <!--            @change="bottomTablePagination.onChange"-->
          <!--            style="margin-top: 16px; text-align: center"-->
          <!--          />-->
        </div>
      </a-layout-content>
      <a-layout-footer> <!-- 右侧区域 --> </a-layout-footer>
    </a-layout>
  </a-layout>
</template>

<script lang="ts">
  import { defineComponent, ref, computed } from 'vue';
  import { Layout, LayoutSider, LayoutContent, LayoutFooter, List, Pagination, Card, Table } from 'ant-design-vue';

  export default defineComponent({
    components: {
      ALayout: Layout,
      ALayoutSider: LayoutSider,
      ALayoutContent: LayoutContent,
      ALayoutFooter: LayoutFooter,
      AList: List,
      APagination: Pagination,
      ACard: Card,
      ATable: Table,
    },
    setup() {
      const dates = ref<string[]>([]);
      for (let i = 1; i <= 100; i++) {
        const date = new Date(2025, 0, i);
        dates.value.push(date.toISOString().split('T')[0]);
      }

      const pagination = ref({
        pageSize: 20,
        current: 1,
        total: dates.value.length,
        onChange(page: number) {
          pagination.value.current = page;
        },
      });

      const paginatedDates = computed(() => {
        const start = (pagination.value.current - 1) * pagination.value.pageSize;
        const end = start + pagination.value.pageSize;
        return dates.value.slice(start, end);
      });

      const columns = [
        { title: '用户名', dataIndex: 'username', key: 'username' },
        { title: '货币', dataIndex: 'currency', key: 'currency' },
        { title: '本金', dataIndex: 'principal', key: 'principal' },
        { title: '利润', dataIndex: 'profit', key: 'profit' },
        { title: '机器人费用', dataIndex: 'robotFee', key: 'robotFee' },
        { title: '初始点卡', dataIndex: 'initialCard', key: 'initialCard' },
        { title: '剩余点卡', dataIndex: 'remainingCard', key: 'remainingCard' },
      ];

      const tableData = ref([
        { key: '1', username: 'user1', currency: 'U', principal: '10000', profit: '500', robotFee: '100', initialCard: '50', remainingCard: '30' },
        { key: '2', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '3', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '4', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '5', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '6', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '7', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '8', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '9', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '10', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '11', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
        { key: '12', username: 'user2', currency: 'U', principal: '15000', profit: '750', robotFee: '150', initialCard: '75', remainingCard: '50' },
      ]);

      const tablePagination = ref({
        pageSize: 10,
        current: 1,
        total: tableData.value.length,
        onChange(page: number) {
          tablePagination.value.current = page;
        },
      });

      const paginatedTableData = computed(() => {
        const start = (tablePagination.value.current - 1) * tablePagination.value.pageSize;
        const end = start + tablePagination.value.pageSize;
        return tableData.value.slice(start, end);
      });

      // 新增底部表格字段
      const bottomColumns = [
        { title: '用户名', dataIndex: 'username', key: 'username' },
        { title: '角色', dataIndex: 'role', key: 'role' },
        { title: '结算前账户金额', dataIndex: 'preSettlementAmount', key: 'preSettlementAmount' },
        { title: '结算后账户金额', dataIndex: 'postSettlementAmount', key: 'postSettlementAmount' },
        { title: '今日获得收益', dataIndex: 'dailyEarnings', key: 'dailyEarnings' },
        { title: '结算日期', dataIndex: 'settlementDate', key: 'settlementDate' },
        { title: '结算平台', dataIndex: 'settlementPlatform', key: 'settlementPlatform' },
      ];

      // 新增底部表格数据
      const bottomTableData = ref([
        {
          key: '1',
          username: 'user1',
          role: 'admin',
          preSettlementAmount: '10000',
          postSettlementAmount: '10500',
          dailyEarnings: '500',
          settlementDate: '2025-01-01',
          settlementPlatform: 'Platform A',
        },
        {
          key: '2',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '3',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '4',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '5',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '6',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '7',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '8',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '9',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '10',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '11',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
        {
          key: '12',
          username: 'user2',
          role: 'user',
          preSettlementAmount: '15000',
          postSettlementAmount: '15750',
          dailyEarnings: '750',
          settlementDate: '2025-01-02',
          settlementPlatform: 'Platform B',
        },
      ]);

      const bottomTablePagination = ref({
        pageSize: 10,
        current: 1,
        total: bottomTableData.value.length,
        onChange(page: number) {
          bottomTablePagination.value.current = page;
        },
      });

      const paginatedBottomTableData = computed(() => {
        const start = (bottomTablePagination.value.current - 1) * bottomTablePagination.value.pageSize;
        const end = start + bottomTablePagination.value.pageSize;
        return bottomTableData.value.slice(start, end);
      });

      return {
        dates,
        pagination,
        paginatedDates,
        columns,
        tableData,
        tablePagination,
        paginatedTableData,
        bottomColumns,
        bottomTableData,
        bottomTablePagination,
        paginatedBottomTableData,
      };
    },
  });
</script>

<style scoped>
  .a-layout-sider {
    background-color: white;
    color: black;
    height: 100%;
  }
</style>
