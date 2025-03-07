<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="title"
    :width="400"
    :showOkBtn="true"
    :showCancelBtn="true"
    @ok="handleOk"
    @cancel="handleCancel"
  >
    <a-form :model="formState" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <a-form-item label="资金密码">
        <a-input-password v-model:value="formState.fundPassword" placeholder="请输入资金密码" />
      </a-form-item>
    </a-form>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { BasicModal, useModal } from '/@/components/Modal';
  import { useMessage } from '/@/hooks/web/useMessage';

  const { createMessage } = useMessage();
  const [registerModal, { openModal, closeModal }] = useModal();
  const props = defineProps({
    onCallback: {
      type: Function,
      required: true,
    },
  });
  const formState = reactive({
    fundPassword: '',
  });
  const title = ref('验证资金密码');

  const showConfirm = () => {
    openModal(true);
  };

  const handleOk = () => {
    if (!formState.fundPassword) {
      createMessage.error('请输入资金密码');
      return;
    }
    // 这里可以添加资金密码验证逻辑
    createMessage.success('验证成功');
    closeModal();
    props.onCallback();
  };

  const handleCancel = () => {
    closeModal();
  };

  defineExpose({
    showConfirm,
  });
</script>
