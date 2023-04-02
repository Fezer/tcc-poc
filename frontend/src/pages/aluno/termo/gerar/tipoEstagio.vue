<script lang="ts">
export default {
  props: {
    advanceStep: {
      type: Function,
      required: true,
    },
  },

  methods: {
    handleValidateAndAdvanceStep() {
      if (this.tipoEstagio && this.localEstagio) {
        this.advanceStep("TIPO_ESTAGIO", {
          tipoEstagio: this.tipoEstagio,
          localEstagio: this.localEstagio,
        });
      } else {
        this.error = "Este campo é obrigatório";
      }
    },
  },
  data() {
    return {
      error: null as string | null,
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
          :class="{ 'p-invalid': error }"
        />

        <small class="text-rose-600">{{ error }}</small>
      </div>
      <div class="card p-fluid col-12">
        <h5>Local do Estágio</h5>
        <SelectButton
          v-model="localEstagio"
          :options="locais"
          optionLabel="label"
          optionValue="value"
          :class="{ 'p-invalid': error }"
        />
        <small class="text-rose-600">{{ error }}</small>
      </div>
    </div>
    <Button
      @click="handleValidateAndAdvanceStep"
      label="Avançar"
      class="p-button-success"
      icon="pi pi-arrow-right"
    />
  </div>
</template>

<style></style>
