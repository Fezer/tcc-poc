<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    pdf: {
      type: Function,
      required: true,
    },
    cancelar: {
      type: Function,
      required: true,
    },
    excel: {
      type: Function,
      required: true,
    },
    description: {
      type: String,
      default: "Escolha o tipo de relatório",
    },
  },
  setup({
    pdf,
    cancelar,
    excel,
    description,
  }: {
    visible: boolean;
    cancelar: () => void;
    pdf: () => void;
    excel: () => void;
    description: string;
  }) {
    return {
      pdf,
      cancelar,
      description,
      excel,
    };
  },
});
</script>

<template>
  <Dialog
    :visible="true"
    header="Gerar Relatório"
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
        @click="cancelar"
      />
      <Button
        label="Excel"
        icon="pi pi-file-excel"
        class="p-button-success"
        @click="excel"
      />
      <Button
        label="PDF"
        icon="pi pi-file-pdf"
        class="p-button-danger"
        autofocus
        @click="pdf"
      />
    </template>
  </Dialog>
</template>

<style></style>
