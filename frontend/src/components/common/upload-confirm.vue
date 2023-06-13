<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    header: {
      type: String,
      default: "Upload de arquivo",
    },
    onConfirm: {
      type: Function,
      required: true,
    },
    handleUpload: {
      type: Function,
      required: true,
    },
    confirmBlocked: {
      type: Boolean,
      required: true,
    },
    onClose: {
      type: Function,
      required: true,
    },
    description: {
      type: String,
      default: "Tem certeza que deseja cancelar esse termo?",
    },
  },
  setup({
    header,
    onConfirm,
    handleUpload,
    confirmBlocked,
    onClose,
    description,
  }: {
    visible: boolean;
    onConfirm: () => void;
    onClose: () => void;
    description: string;
    header: string;
    handleUpload: () => void;
    confirmBlocked: boolean;
  }) {
    return {
      onClose,
      description,
      onConfirm,
      header,
      handleUpload,
      confirmBlocked,
    };
  },
});
</script>

<template>
  <Dialog
    :visible="true"
    :header="header"
    :closable="false"
    style="width: 500px"
    :modal="true"
  >
    <p>
      {{ description }}
    </p>

    <FileUpload
      accept=".pdf"
      :multiple="false"
      :maxFileSize="10000000"
      chooseLabel="Adicionar arquivo"
      mode="basic"
      customUpload
      @uploader="handleUpload"
    />

    <template #footer>
      <Button
        label="Voltar"
        icon="pi pi-times"
        class="p-button-secondary"
        @click="onClose"
      />
      <Button
        label="Confirmar solicitação"
        icon="pi pi-check"
        class="p-button-primary"
        autofocus
        :disabled="confirmBlocked"
        @click="onConfirm"
      />
    </template>
  </Dialog>
</template>

<style></style>
