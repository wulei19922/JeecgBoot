<template>
  <!-- 添加保存按钮 -->
  <div class="form-footer" v-if="!isDetail">
    <a-button type="default" @click="handleSubmit">保存</a-button>
  </div>
  <BasicForm @register="registerForm" name="CoinBotForm" />
</template>

<script lang="ts" setup>
import { ref, computed, unref, onMounted } from "vue";
  import { BasicModal, useModalInner } from '/@/components/Modal';
  import { BasicForm, useForm } from '/@/components/Form/index';
  import { formSchema } from '../CoinBot.data';
  import { saveOrUpdate } from '../CoinBot.api';
  // Emits声明
  const emit = defineEmits(['register', 'success']);
  const isUpdate = ref(true);
  const isDetail = ref(false);

  const props = defineProps({
    bot: {
      type: Object as PropType<any>, // 根据实际类型调整
      default: () => ({}),
      required: false,
    },
    isUpdate: {
      type: Boolean, // 根据实际类型调整
      default: false,
      required: false,
    },
    isDetail: {
      type: Boolean, // 根据实际类型调整
      default: false,
      required: false,
    },
    isAdd: {
      type: Boolean, // 根据实际类型调整
      default: false,
      required: false,
    },
    showFooter: {
      type: Boolean, // 根据实际类型调整
      default: false,
      required: false,
    },
  });
  console.log('currentBot',props.bot)
  //表单配置
  const [registerForm, { setProps, resetFields, setFieldsValue, validate, scrollToField }] = useForm({
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 12 },
  });

  onMounted(async () => {
    await resetFields();
    //重置表单

    isUpdate.value = props.isUpdate;
    isDetail.value = props.showFooter;
    console.log("~~~~~~~~~~~~~~~~",unref(isUpdate));
    if (unref(isUpdate)) {
      //表单赋值
      await setFieldsValue({
        ...props.bot,
      });
    }
    // 隐藏底部时禁用整个表单
    setProps({ disabled: props.showFooter });
  });

  //设置标题
  const title = computed(() => (!unref(isUpdate) ? '新增' : !unref(isDetail) ? '详情' : '编辑'));
  const submitting = ref(false);

  //表单提交事件
  async function handleSubmit(v) {
    try {
      submitting.value = true;
      let values = await validate();
      //提交表单
      await saveOrUpdate(values, isUpdate.value);
      //关闭弹窗
      //刷新列表
      emit('success');
    } catch ({ errorFields }) {
      if (errorFields) {
        const firstField = errorFields[0];
        if (firstField) {
          scrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
        }
      }
      return Promise.reject(errorFields);
    } finally {
      submitting.value = false;
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
  .form-footer {
    margin-top: 10px;
    margin-bottom: 10px;
    margin-left: 30px;
    .ant-btn {
      width: 120px;
      margin-left: 8px;
    }
  }
</style>
