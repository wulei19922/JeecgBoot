<template>
  <BasicModal
    ref="modalRef"
    destroyOnClose
    wrapClassName="j-cgform-tab-modal"
    v-bind="$attrs"
    @register="registerModal"
    :width="896"
    @ok="handleSubmit"
  >
    <!-- 标题区域 -->
    <template #title>
      <div class="titleArea">
        <div class="title">{{ title }}</div>
        <div class="right">
          <a-radio-group v-model:value="activeKey">
            <template v-for="(item, index) in tabNav" :key="index">
              <a-radio-button :value="item.tableName">{{ item.tableTxt }}</a-radio-button>
            </template>
          </a-radio-group>
        </div>
      </div>
    </template>
    <!--表单区域 -->
    <div class="contentArea">
      <!--主表区域 -->
      <BasicForm @register="registerForm" ref="formRef" v-show="activeKey == refKeys[0]" name="VouchersParamsForm" />
      <!--子表区域 -->
      <VouchersWechatForm ref="vouchersWechatForm" :disabled="formDisabled" v-show="activeKey == 'vouchersWechat'" />
      <VouchersAlipayForm ref="vouchersAlipayForm" :disabled="formDisabled" v-show="activeKey == 'vouchersAlipay'" />
      <VouchersJdForm ref="vouchersJdForm" :disabled="formDisabled" v-show="activeKey == 'vouchersJd'" />
      <VouchersMeituanForm ref="vouchersMeituanForm" :disabled="formDisabled" v-show="activeKey == 'vouchersMeituan'" />
    </div>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, computed, unref, reactive } from 'vue';
  import { BasicModal, useModalInner } from '/@/components/Modal';
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
  // Emits声明
  const emit = defineEmits(['register', 'success']);
  const isUpdate = ref(true);
  const formDisabled = ref(false);
  const modalRef = ref();
  const refKeys = ref(['vouchersParams', 'vouchersWechat', 'vouchersAlipay', 'vouchersJd', 'vouchersMeituan']);
  const tabNav = ref<any>([
    { tableName: 'vouchersParams', tableTxt: '优惠券参数配置，各大平台合集' },
    { tableName: 'vouchersWechat', tableTxt: '微信优惠券规则' },
    { tableName: 'vouchersAlipay', tableTxt: '阿里优惠券规则' },
    { tableName: 'vouchersJd', tableTxt: '京东优惠券规则' },
    { tableName: 'vouchersMeituan', tableTxt: '美团优惠券规则' },
  ]);
  const activeKey = ref('vouchersParams');
  const vouchersWechatForm = ref();
  const vouchersAlipayForm = ref();
  const vouchersJdForm = ref();
  const vouchersMeituanForm = ref();
  const tableRefs = {};
  //表单配置
  const [registerForm, { setProps, resetFields, setFieldsValue, validate }] = useForm({
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 12 },
  });
  //表单赋值
  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    //重置表单
    await reset();
    setModalProps({ confirmLoading: false, showCancelBtn: data?.showFooter, showOkBtn: data?.showFooter });
    isUpdate.value = !!data?.isUpdate;
    formDisabled.value = !data?.showFooter;
    if (unref(isUpdate)) {
      //表单赋值
      await setFieldsValue({
        ...data.record,
      });
      vouchersWechatForm.value.initFormData(vouchersWechatList, data?.record?.id);
      vouchersAlipayForm.value.initFormData(vouchersAlipayList, data?.record?.id);
      vouchersJdForm.value.initFormData(vouchersJdList, data?.record?.id);
      vouchersMeituanForm.value.initFormData(vouchersMeituanList, data?.record?.id);
    }
    // 隐藏底部时禁用整个表单
    setProps({ disabled: !data?.showFooter });
  });
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
    let maxHeight: Nullable<string> = '500px';
    // 弹窗wrapper
    let modalWrapperRef = modalRef.value?.modalWrapperRef;
    if (modalWrapperRef) {
      if (modalWrapperRef.fullScreen) {
        height = 'calc(' + modalWrapperRef.spinStyle.height + ' - 50px)';
        maxHeight = null;
      }
    }
    let overflow = 'auto';
    return { height, minHeight, maxHeight, overflow };
  });
  //设置标题
  const title = computed(() => (!unref(isUpdate) ? '新增' : !unref(formDisabled) ? '编辑' : '详情'));
  //重置
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
            if (e.errorFields) {
              const firstField = e.errorFields[0];
              if (firstField) {
                e.scrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
              }
            }
          } else {
            console.error(e);
          }
        });
    });
  }
  //表单提交事件
  async function requestAddOrEdit(values) {
    try {
      setModalProps({ confirmLoading: true });
      //提交表单
      await saveOrUpdate(values, isUpdate.value);
      //关闭弹窗
      closeModal();
      //刷新列表
      emit('success');
    } finally {
      setModalProps({ confirmLoading: false });
    }
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

  .titleArea {
    display: flex;
    align-content: center;
    padding-right: 70px;
    .title {
      margin-right: 16px;
      line-height: 32px;
    }
    .right {
      overflow-x: auto;
      overflow-y: hidden;
      flex: 1;
      white-space: nowrap;
      .ant-radio-group {
        font-weight: normal;
      }
    }
  }

  html[data-theme='light'] {
    .right {
      .ant-radio-group {
        :deep(.ant-radio-button-wrapper:not(.ant-radio-button-wrapper-checked)) {
          color: #555;
        }
      }
    }
  }
</style>

<style lang="less">
  // Online表单Tab风格专属样式
  .j-cgform-tab-modal {
    .contentArea {
      padding: 20px 1.5% 0;
    }

    //.ant-modal-header {
    //  padding-top: 8px;
    //  padding-bottom: 8px;
    //  border-bottom: none !important;
    //}

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
