<script lang="ts">
export default {
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
  </div>
</template>

<style></style>
