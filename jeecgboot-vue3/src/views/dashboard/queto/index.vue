<template>
  <a-space direction="vertical" :style="{ width: '100%', height: '100%' }" :size="[0, 48]">
    <a-layout :style="{ width: '100%', height: '100%', display: 'flex', flexDirection: 'column' }">
      <!--      <a-layout-header :style="headerStyle"> DOGE/USDT </a-layout-header>-->

      <a-layout :style="{ flex: 2, display: 'flex' }">
        <a-layout-sider :style="siderStyle">
          <a-table :show-header="false" :columns="columnsCoinList" :custom-row="customRow" :data-source="coinList" size="small">
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'icourl'">
                <a-avatar :src="'http://localhost:8080/jeecg-boot/sys/common/static/' + record.icourl" />
              </template>
            </template>
          </a-table>
        </a-layout-sider>

        <a-layout-content :style="{ ...contentStyle, flex: 3 }">
          <iframe :src="externalUrl" width="100%" height="100%" border="none" style="overflow: hidden" frameborder="0" allowfullscreen></iframe>
        </a-layout-content>

        <a-layout-sider :style="siderStyle">
          <a-table :show-header="false" :columns="columnsCoinList" :custom-row="customRow" :data-source="coinList" size="small">
            <template #bodyCell="{ column, record }">
              <template v-if="column.key === 'icourl'">
                <a-avatar :src="'http://localhost:8080/jeecg-boot/sys/common/static/' + record.icourl" />
              </template>
            </template>
          </a-table>
        </a-layout-sider>
      </a-layout>

      <a-layout-footer :style="{ ...footerStyle, flex: 1 }">
        <a-menu @click="menuClick()" v-model:selectedKeys="current" mode="horizontal" :items="items" />
        <div>
          <!--定义表格-->
          <BasicTable @register="registerTable" style="padding: 0px">
            <!--操作栏-->
            <template #action="{ record }">
              <TableAction :actions="getTableAction(record)" />
            </template>

            <!--插槽:table标题-->
            <template #tableTitle>
              <a-button type="small" @click="handleAdd" preIcon="ant-design:plus-outlined">新增机器人</a-button>
            </template>
          </BasicTable>
        </div>
      </a-layout-footer>
    </a-layout>
  </a-space>
</template>
<script lang="ts" setup>
  import { CSSProperties, h, ref, onMounted, nextTick } from 'vue';
  import { MailOutlined, AppstoreOutlined } from '@ant-design/icons-vue';
  import { MenuProps, Avatar } from 'ant-design-vue';
  import { ActionItem, BasicColumn, BasicTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { defHttp } from '/@/utils/http/axios';
  import { useRouter } from 'vue-router';
  const current = ref<string[]>(['mail']);
  const currentSymbol = ref<string[]>(['btcusdt']);
  const headerStyle: CSSProperties = {
    textAlign: 'center',
    color: '#fffff',
    height: 64,
    paddingInline: 50,
    lineHeight: '64px',
    backgroundColor: '#fffff',
    background: '#d6d6d7',
  };

  const contentStyle: CSSProperties = {
    textAlign: 'center',
    minHeight: 120,
    lineHeight: '120px',
    color: '#fff',
    backgroundColor: '#108ee9',
  };

  const siderStyle: CSSProperties = {
    textAlign: 'center',
    lineHeight: '120px',
    color: '#fff',
    backgroundColor: '#ffffff',
  };

  const footerStyle: CSSProperties = {
    textAlign: 'center',
    color: '#fff',
    backgroundColor: '#7dbcea',
    padding: 0,
  };

  const columns: BasicColumn[] = [
    {
      title: '用户',
      dataIndex: 'memberId_dictText',
      width: 170,
      align: 'left',
      resizable: true,
      sorter: {
        multiple: 1,
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 130,
      resizable: true,
    },
    {
      title: '总投资额',
      dataIndex: 'totalInvest',
      width: 140,
      resizable: true,
    },
    {
      title: '总收益',
      dataIndex: 'profit',
      width: 140,
      resizable: true,
      sorter: {
        multiple: 2,
      },
    },
    {
      title: '交易对',
      dataIndex: 'symbol',
      width: 140,
      resizable: true,
      sorter: {
        multiple: 2,
      },
    },
    {
      title: '持仓',
      dataIndex: 'positions',
      width: 140,
      resizable: true,
      sorter: {
        multiple: 2,
      },
    },
    {
      title: '网格利润',
      dataIndex: 'incomeGride',
      width: 140,
      resizable: true,
    },
    {
      title: '浮动盈亏',
      dataIndex: 'profit',
      sorter: {
        multiple: 3,
      },
      width: 120,
      resizable: true,
    },
    {
      title: '价格区间',
      dataIndex: 'minPrice',
      width: 120,
      resizable: true,
    },
    {
      title: '网格数量',
      dataIndex: 'grideNum',
      width: 120,
      resizable: true,
    },
    {
      title: '运行时间',
      dataIndex: 'grideNum',
      width: 120,
      resizable: true,
    },
    {
      title: '网格状态',
      dataIndex: 'status_dictText',
      width: 120,
      resizable: true,
    },
  ];
  const columnsCoinList: BasicColumn[] = [
    {
      title: '货币',
      dataIndex: 'symbol',
      width: 170,
      align: 'left',
      resizable: true,
      sorter: {
        multiple: 1,
      },
    },
    {
      title: '图标',
      dataIndex: 'icourl',
      width: 130,
      key: 'icourl',
    },
  ];

  const externalUrl = ref<string[]>(['http://localhost:5173/?symbol=' + currentSymbol.value + '&t=' + Math.random()]);
  let router = useRouter();
  const handleAdd = () => {
    // 在此处编写新增逻辑
    router.push('/online/cgformList/9a9a9a8194ef31b20194ef31b2dd0000');

    // /online/cgformList/9a9a9a8194ef31b20194ef31b2dd0000
    // 可能包括打开一个模态框让用户输入数据，或者导航到一个新的页面等
  };

  const handleRowClick = (record: DataItem, index: number) => {
    currentSymbol.value = record['symbol'];
    externalUrl.value = 'http://localhost:5173/?symbol=' + currentSymbol.value + '&t=' + Math.random();
    console.log(currentSymbol);
  };

  const customRow = (record: DataItem, index: number) => {
    return {
      onClick: () => handleRowClick(record, index), // 点击行时触发
    };
  };

  const items = ref<MenuProps['items']>([
    {
      key: 'mail',
      icon: () => h(MailOutlined),
      label: '运行中的机器人',
      title: 'Navigation One',
    },
    {
      key: 'app',
      icon: () => h(AppstoreOutlined),
      label: '历史成交',
      title: 'Navigation Two',
    },
  ]);

  const menuClick = () => {
    console.log('menuClick');
  };

  //ajax请求api接口
  const demoListApi = (params) => {
    return defHttp.get({ url: '/qe/coinBot/list', params });
  };
  // 列表页面公共参数、方法
  const { tableContext } = useListPage({
    designScope: 'basic-table-demo-ajax',
    tableProps: {
      title: '机器人列表',
      api: demoListApi,
      columns: columns,
      actionColumn: {
        width: 120,
      },
    },
  });
  //BasicTable绑定注册
  const [registerTable] = tableContext;
  /**
   * 操作栏
   */
  function getTableAction(record): ActionItem[] {
    return [
      {
        label: '详情',
        onClick: handleEdit.bind(null, record),
      },
    ];
  }

  function handleEdit(record) {
    router.push('/coinorder');
    console.log('查看量化订单详情');
  }

  const count = 3;
  const fakeDataUrl = `/qe/coinSupport`;

  const initLoading = ref(true);
  const loading = ref(false);
  const data = ref([]);
  const list = ref([]);
  let coinList = ref([]);
  onMounted(() => {
    defHttp.get({ url: '/qe/coinSupport/list' }).then((res) => {
      coinList.value = res.records;
      initLoading.value = false;
    });
  });

  const onLoadMore = () => {
    loading.value = true;
    list.value = data.value.concat([...new Array(count)].map(() => ({ loading: true, name: {}, picture: {} })));
    fetch(fakeDataUrl)
      .then((res) => res.json())
      .then((res) => {
        const newData = data.value.concat(res.results);
        loading.value = false;
        data.value = newData;
        list.value = newData;
        nextTick(() => {
          // Resetting window's offsetTop so as to display react-virtualized demo underfloor.
          // In real scene, you can using public method of react-virtualized:
          // https://stackoverflow.com/questions/46700726/how-to-use-public-method-updateposition-of-react-virtualized
          window.dispatchEvent(new Event('resize'));
        });
      });
  };
</script>

<style lang="less" scoped>
  /* 使用 :deep() 穿透 Scoped CSS */
  :deep(.ant-space-item) {
    height: 100%;
  }
</style>
