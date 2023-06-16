<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    pdf: {
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
    excel,
    description,
  }: {
    visible: boolean;
    pdf: () => void;
    excel: () => void;
    description: string;
  }) {
    return {
      pdf,
      description,
      excel,
    };
  },
});
</script>

<template>
  <Dialog
    :visible="true"
    header="Tipo de arquivo"
    :closable="false"
    style="width: 300px"
    :modal="true"
  >
    <p>
      {{ description }}
    </p>
    <template #footer>
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
