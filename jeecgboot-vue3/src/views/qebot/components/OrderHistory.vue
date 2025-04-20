<template>
  <div class="search-container">
    <a-row>
      <!--      <a-col :span="8" style="margin-right: 10px">-->
      <!--        <span>网格参数</span>-->
      <!--        <a-table :columns="gridColumns" :data-source="currentBotGrid" :pagination="false" />-->
      <!--      </a-col>-->
      <a-col :span="24">
        <a-table :loading="loading" :columns="orderColumns" @change="handleChange" :pagination="pagination" :data-source="currentOrder">
          <template #bodyCell="{ column, record }">
            <template v-if="column.dataIndex === 'silder'">
              <a v-if="record[column.dataIndex] === 'SELL'" style="color: red"> 卖 </a>
              <a v-else-if="record[column.dataIndex] === 'BUY'" style="color: green"> 买 </a>
            </template>
          </template>
        </a-table>
      </a-col>
    </a-row>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref, h } from 'vue';
  import { TableColumnsType } from 'ant-design-vue';
  import { list as orderList } from '@/views/coinOrder/CoinOrder.api';
  import { watch, onMounted } from 'vue'; // 添加导入
  import { Tooltip as aTooltip } from 'ant-design-vue';
  export default defineComponent({
    name: 'OrderHistory',
    props: {
      bot: {
        type: Object,
        required: true,
        default: () => {},
      },
    },
    emits: ['controlFunction'],
    setup(props, { emit }) {
      watch(
        () => props.bot,
        (bot) => {
          initData(bot);
        },
        { deep: true, immediate: false }
      );
      onMounted(() => {
        // 在挂载时如果有初始bot值则加载数据
        if (props.bot?.instanceName) {
          initData(props.bot);
        }
      });
      const loading = ref(false); // 添加加载状态
      const gridColumns = ref<TableColumnsType>([
        {
          title: '买入订单',
          dataIndex: 'buy_order_id',
          key: 'buy_order_id',
          minWidth: 30,
          width: 30,
        },
        {
          title: '买入数量',
          dataIndex: 'buy_order_num',
          key: 'buy_order_num',
          minWidth: 30,
          maxWidth: 30,
        },
        {
          title: '买入价格',
          dataIndex: 'buy_price',
          key: 'buy_price',
          minWidth: 30,
          maxWidth: 30,
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
          maxWidth: 30,
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
          maxWidth: 30,
        },
      ]);
      const orderColumns = ref<TableColumnsType>([
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'createTime',
          customRender: ({ text }) => {
            if (text) {
              return text.substring(5);
            }
            return text;
          },
        },
        {
          title: '方向',
          align: 'center',
          dataIndex: 'silder',
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
        {
          title: '状态',
          align: 'center',
          dataIndex: 'status_dictText',
        },
        {
          title: '匹配对',
          align: 'center',
          dataIndex: 'matchId',
          customRender: ({ text }) => {
            return h(
              aTooltip,
              {
                title: text,
                overlayStyle: { maxWidth: '400px' },
              },
              {
                default: () =>
                  h(
                    'span',
                    {
                      style: {
                        display: 'inline-block',
                        maxWidth: '80px',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        whiteSpace: 'nowrap',
                      },
                    },
                    text
                  ),
              }
            );
          },
        },
      ]);
      const currentBotGrid = ref<Object>([]);
      const params = ref<Object>({
        column: 'createTime',
        order: 'desc',
        pageNo: 1,
        pageSize: 10,
        botId: '1895698783372677121_dogeusdt_BINANCE_spot_gride',
      });
      const currentOrder = ref<Object>([]);
      const pagination = ref<Object>({ total: 10, current: 1, pageSize: 10 });
      const handleChange = (data) => {
        params.value['pageNo'] = data.current;
        params.value['pageSize'] = data.pageSize;
        params.value['pageSize'] = data.pageSize;
        loading.value = true;
        orderList(params.value).then((res) => {
          pagination.value = {
            total: res.total,
            current: res.current,
            pageSize: res.size,
          };
          loading.value = false;
          currentOrder.value = res.records;
        });
      };
      const initData = (bot) => {
        params.value['botId'] = bot['instanceName'];
        if (!bot) {
          return;
        }
        currentBotGrid.value = JSON.parse(bot.gridConfig);
        //初始化订单查询参数
        loading.value = true;
        orderList(params.value).then((res) => {
          currentOrder.value = res.records;
          loading.value = false;
          pagination.value = {
            total: res.total,
            current: res.current,
            pageSize: res.size,
          };
        });
      };

      return {
        gridColumns,
        orderColumns,
        currentBotGrid,
        handleChange,
        pagination,
        currentOrder,
        initData,
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
  ::v-deep(.ant-table) {
    font-size: 11px;
  }
  ::v-deep(.ant-table-thead > tr > th) {
    font-size: 11px;
  }
</style>
