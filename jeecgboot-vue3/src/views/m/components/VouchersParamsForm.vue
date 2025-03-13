<template>
  <div>
    <!-- 子表单区域 -->
    <a-tabs v-model:activeKey="activeKey" animated @change="handleChangeTabs">
      <!--主表区域 -->
      <a-tab-pane tab="优惠券参数配置，各大平台合集" :key="refKeys[0]" :forceRender="true" :style="tabsStyle">
        <BasicForm @register="registerForm" ref="formRef" />
      </a-tab-pane>
      <!--子表单区域 -->
      <a-tab-pane tab="微信优惠券规则" key="vouchersWechat" :forceRender="true" :style="tabsStyle">
        <VouchersWechatForm ref="vouchersWechatForm" :disabled="formDisabled" />
      </a-tab-pane>

      <a-tab-pane tab="阿里优惠券规则" key="vouchersAlipay" :forceRender="true" :style="tabsStyle">
        <VouchersAlipayForm ref="vouchersAlipayForm" :disabled="formDisabled" />
      </a-tab-pane>

      <a-tab-pane tab="京东优惠券规则" key="vouchersJd" :forceRender="true" :style="tabsStyle">
        <VouchersJdForm ref="vouchersJdForm" :disabled="formDisabled" />
      </a-tab-pane>

      <a-tab-pane tab="美团优惠券规则" key="vouchersMeituan" :forceRender="true" :style="tabsStyle">
        <VouchersMeituanForm ref="vouchersMeituanForm" :disabled="formDisabled" />
      </a-tab-pane>
    </a-tabs>

    <div style="width: 100%; text-align: center; margin-top: 10px" v-if="showFlowSubmitButton">
      <a-button preIcon="ant-design:check-outlined" style="width: 126px" type="primary" @click="handleSubmit">提 交</a-button>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import { defHttp } from '/@/utils/http/axios';
  import { ref, computed, unref, reactive, onMounted, defineProps } from 'vue';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { JVxeTable } from '/@/components/jeecg/JVxeTable';
  import { useJvxeMethod } from '/@/hooks/system/useJvxeMethods.ts';
  import VouchersWechatForm from './VouchersWechatForm.vue';
  import VouchersAlipayForm from './VouchersAlipayForm.vue';
  import VouchersJdForm from './VouchersJdForm.vue';
  import VouchersMeituanForm from './VouchersMeituanForm.vue';
  import { formSchema } from '../VouchersParams.data';
  import { saveOrUpdate, vouchersWechatList, vouchersAlipayList, vouchersJdList, vouchersMeituanList } from '../VouchersParams.api';
  import { VALIDATE_FAILED } from '/@/utils/common/vxeUtils';
  const refKeys = ref(['vouchersParams', 'vouchersWechat', 'vouchersAlipay', 'vouchersJd', 'vouchersMeituan']);
  const activeKey = ref('vouchersParams');
  const vouchersWechatForm = ref();
  const vouchersAlipayForm = ref();
  const vouchersJdForm = ref();
  const vouchersMeituanForm = ref();
  const tableRefs = {};

  const props = defineProps({
    formData: { type: Object, default: () => {} },
    formBpm: { type: Boolean, default: true },
  });
  const formDisabled = computed(() => {
    if (props.formBpm === true) {
      if (props.formData.disabled === false) {
        return false;
      }
    }
    return true;
  });
  // 是否显示提交按钮
  const showFlowSubmitButton = computed(() => {
    if (props.formBpm === true) {
      if (props.formData.disabled === false) {
        return true;
      }
    }
    return false;
  });

  //表单配置
  const [registerForm, { setProps, resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 150,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 12 },
  });

  onMounted(() => {
    initFormData();
  });
  //渲染流程表单数据
  const queryByIdUrl = '/m/vouchersParams/queryById';
  async function initFormData() {
    if (props.formBpm === true) {
      await reset();
      let params = { id: props.formData.dataId };
      const data = await defHttp.get({ url: queryByIdUrl, params });
      //表单赋值
      await setFieldsValue({
        ...data,
      });
      vouchersWechatForm.value.initFormData(vouchersWechatList, data.id);
      vouchersAlipayForm.value.initFormData(vouchersAlipayList, data.id);
      vouchersJdForm.value.initFormData(vouchersJdList, data.id);
      vouchersMeituanForm.value.initFormData(vouchersMeituanList, data.id);
      // 隐藏底部时禁用整个表单
      setProps({ disabled: formDisabled.value });
    }
  }

  //方法配置
  const [handleChangeTabs, handleSubmit, requestSubTableData, formRef] = useJvxeMethod(
    requestAddOrEdit,
    classifyIntoFormData,
    tableRefs,
    activeKey,
    refKeys,
    validateSubForm
  );
  // 弹窗tabs滚动区域的高度
  const tabsStyle = computed(() => {
    let height: Nullable<string> = null;
    let minHeight = '100px';
    // 弹窗wrapper
    let overflow = 'auto';
    return { height, minHeight, overflow };
  });

  async function reset() {
    await resetFields();
    activeKey.value = 'vouchersParams';
    vouchersWechatForm.value.resetFields();
    vouchersAlipayForm.value.resetFields();
    vouchersJdForm.value.resetFields();
    vouchersMeituanForm.value.resetFields();
  }
  function classifyIntoFormData(allValues) {
    let main = Object.assign({}, allValues.formValue);
    return {
      ...main, // 展开
      vouchersWechatList: vouchersWechatForm.value.getFormData(),
      vouchersAlipayList: vouchersAlipayForm.value.getFormData(),
      vouchersJdList: vouchersJdForm.value.getFormData(),
      vouchersMeituanList: vouchersMeituanForm.value.getFormData(),
    };
  }
  //校验所有一对一子表表单
  function validateSubForm(allValues) {
    return new Promise((resolve, reject) => {
      Promise.all([
        vouchersWechatForm.value.validateForm(1),
        vouchersAlipayForm.value.validateForm(2),
        vouchersJdForm.value.validateForm(3),
        vouchersMeituanForm.value.validateForm(4),
      ])
        .then(() => {
          resolve(allValues);
        })
        .catch((e) => {
          if (e.error === VALIDATE_FAILED) {
            // 如果有未通过表单验证的子表，就自动跳转到它所在的tab
            activeKey.value = e.index == null ? unref(activeKey) : refKeys.value[e.index];
          } else {
            console.error(e);
          }
        });
    });
  }
  //表单提交事件
  async function requestAddOrEdit(values) {
    //提交表单
    await saveOrUpdate(values, true);
  }
</script>

<style lang="less" scoped>
  /** 时间和数字输入框样式 */
  :deep(.ant-input-number) {
    width: 100%;
  }

  :deep(.ant-calendar-picker) {
    width: 100%;
  }
</style>

<style lang="less">
  // Online表单Tab风格专属样式
  .j-cgform-tab-modal {
    .ant-modal-header {
      padding-top: 8px;
      padding-bottom: 8px;
      border-bottom: none !important;
    }

    .ant-modal .ant-modal-body > .scrollbar,
    .ant-tabs-nav .ant-tabs-tab {
      padding-top: 0;
    }

    .ant-tabs-top-bar {
      width: calc(100% - 55px);
      position: relative;
      left: -14px;
    }

    .ant-tabs .ant-tabs-top-content > .ant-tabs-tabpane {
      overflow: hidden auto;
    }
  }
</style>
