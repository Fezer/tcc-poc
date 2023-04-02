<script lang="ts">
export default {
  props: {
    advanceStep: {
      type: Function,
      required: true,
    },
    backStep: {
      type: Function,
      required: true,
    },
  },
  methods: {
    handleAdvanceStep() {
      this.advanceStep();
    },
  },
  data() {
    return {
      tipoContratante: null,
      tipos: [
        { label: "Pessoa Jurídica", value: "PJ" },
        { label: "Pessoa Física", value: "PF" },
      ],
    };
  },
};
</script>

<template>
  <div class="grid mt-2">
    <h5 class="mb-0 mt-4 ml-2">Tipo Contratante</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Tipo da Contratante</h5>
        <SelectButton
          v-model="tipoContratante"
          :options="tipos"
          optionLabel="label"
          optionValue="value"
        />
      </div>
    </div>

    <div class="w-full flex justify-end gap-2">
      <Button
        @click="backStep()"
        label="Voltar"
        class="p-button-secondary"
        icon="pi pi-arrow-left"
      />
      <Button
        @click="handleAdvanceStep"
        label="Avançar"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<style></style>
