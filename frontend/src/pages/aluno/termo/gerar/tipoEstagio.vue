<script lang="ts">
export default {
  data() {
    return {
      tipoEstagio: null,
      tipos: [
        { label: "Obrigatório", value: "OBRIGATORIO" },
        { label: "Não obrigatório", value: "NAO_OBRIGATORIO" },
      ],
      localEstagio: null,
      locais: [
        { label: "Empresa Externa", value: "EXTERNO" },
        { label: "UFPR", value: "UFPR" },
      ],
    };
  },
};
</script>

<template>
  <div class="grid mt-2">
    <h5 class="mb-0 mt-4 ml-2">Tipo do Estágio</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Obrigatoriedade do estágio</h5>
        <SelectButton
          v-model="tipoEstagio"
          :options="tipos"
          optionLabel="label"
          optionValue="value"
        />
      </div>
      <div class="card p-fluid col-12">
        <h5>Local do Estágio</h5>
        <SelectButton
          v-model="localEstagio"
          :options="locais"
          optionLabel="label"
          optionValue="value"
        />
      </div>
    </div>
  </div>
</template>

<style></style>
