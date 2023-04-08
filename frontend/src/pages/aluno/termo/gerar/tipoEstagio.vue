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
    dados: {
      type: Object,
    },
  },

  methods: {
    handleValidateAndAdvanceStep() {
      if (this.tipoEstagio && this.localEstagio) {
        this.advanceStep({
          tipoEstagio: this.tipoEstagio,
          localEstagio: this.localEstagio,
        });
      } else {
        this.error = "Este campo é obrigatório";
      }
    },
  },
  mounted() {
    if (this.dados) {
      this.tipoEstagio = this.dados.tipoEstagio;
      this.localEstagio = this.dados.localEstagio;
    }
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
    <div class="w-full flex justify-end gap-2">
      <Button
        @click="backStep({ ...$data })"
        label="Voltar"
        class="p-button-secondary"
        icon="pi pi-arrow-left"
      />
      <Button
        @click="handleValidateAndAdvanceStep"
        label="Avançar"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<style></style>
