<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    onConfirm: {
      type: Function,
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
    onConfirm,
    onClose,
    description,
  }: {
    visible: boolean;
    onConfirm: () => void;
    onClose: () => void;
    description: string;
  }) {
    return {
      onClose,
      description,
      onConfirm,
    };
  },
});
</script>

<template>
  <Dialog
    :visible="true"
    header="Confirmar cancelamento"
    :closable="false"
    style="width: 500px"
    :modal="true"
  >
    <p>
      {{ description }}
    </p>
    <template #footer>
      <Button
        label="Voltar"
        icon="pi pi-times"
        class="p-button-secondary"
        @click="onClose"
      />
      <Button
        label="Cancelar"
        icon="pi pi-check"
        class="p-button-danger"
        autofocus
        @click="onConfirm"
      />
    </template>
  </Dialog>
</template>

<style></style>
