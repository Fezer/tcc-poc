<script lang="ts">
import DadosAluno from "./dadosAluno.vue";
import TipoContratante from "./tipoContratante.vue";
import TipoEstagio from "./tipoEstagio.vue";

type Steps = "DADOS_ALUNO" | "TIPO_ESTAGIO" | "TIPO_CONTRATANTE";

export default {
  components: { DadosAluno, TipoEstagio, TipoContratante },
  data() {
    return {
      step: "DADOS_ALUNO" as Steps,
      progressValue: 20,
    };
  },
  methods: {
    handleAdvanceStep() {
      if (this.step === "DADOS_ALUNO") {
        this.step = "TIPO_ESTAGIO";
        this.progressValue = 40;
      } else if (this.step === "TIPO_ESTAGIO") {
        this.step = "TIPO_CONTRATANTE";
        this.progressValue = 60;
      }
    },
    handleBackStep() {
      if (this.step === "TIPO_CONTRATANTE") {
        this.step = "TIPO_ESTAGIO";
        this.progressValue = 40;
      } else if (this.step === "TIPO_ESTAGIO") {
        this.step = "DADOS_ALUNO";
        this.progressValue = 20;
      }
    },
  },
};
</script>

<template>
  <div>
    <small>Processos > Iniciar novo estágio</small>
    <h3>Novo termo de compromisso</h3>
    <ProgressBar
      :value="progressValue"
      style="height: 10px"
      :show-value="false"
    />

    <DadosAluno v-if="step === 'DADOS_ALUNO'" />
    <TipoEstagio v-if="step === 'TIPO_ESTAGIO'" />
    <TipoContratante v-if="step === 'TIPO_CONTRATANTE'" />

    <div class="w-full flex justify-end gap-2">
      <Button
        label="Acessar SIGA"
        icon="pi pi-external-link"
        class="p-button-secondary"
        v-if="step === 'DADOS_ALUNO'"
      />
      <Button
        @click="handleBackStep"
        label="Voltar"
        class="p-button-secondary"
        icon="pi pi-arrow-left"
        v-else
      />
      <Button
        @click="handleAdvanceStep"
        label="Confirmar dados"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<style></style>
