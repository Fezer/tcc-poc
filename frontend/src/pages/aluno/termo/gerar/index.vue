<script lang="ts">
import DadosAluno from "./dadosAluno.vue";
import DadosEstagio from "./dadosEstagio.vue";
import DadosAuxiliares from "./dadosAuxiliares.vue";
import TipoContratante from "./tipoContratante.vue";
import TipoEstagio from "./tipoEstagio.vue";

type Steps =
  | "DADOS_ALUNO"
  | "TIPO_ESTAGIO"
  | "TIPO_CONTRATANTE"
  | "DADOS_ESTAGIO"
  | "DADOS_AUXILIARES";

export default {
  components: {
    DadosAluno,
    TipoEstagio,
    TipoContratante,
    DadosAuxiliares,
    DadosEstagio,
  },
  data() {
    return {
      step: "DADOS_ALUNO" as Steps,
      progressValue: 20,
    };
  },
  methods: {
    handleAdvanceStep() {
      switch (this.step) {
        case "DADOS_ALUNO":
          this.step = "TIPO_ESTAGIO";
          this.progressValue = 40;
          break;
        case "TIPO_ESTAGIO":
          this.step = "TIPO_CONTRATANTE";
          this.progressValue = 60;
          break;
        case "TIPO_CONTRATANTE":
          this.step = "DADOS_ESTAGIO";
          this.progressValue = 80;
          break;
        case "DADOS_ESTAGIO":
          this.step = "DADOS_AUXILIARES";
          this.progressValue = 100;
          break;
      }
    },
    handleBackStep() {
      switch (this.step) {
        case "DADOS_AUXILIARES":
          this.step = "DADOS_ESTAGIO";
          this.progressValue = 100;
          break;
        case "DADOS_ESTAGIO":
          this.step = "TIPO_CONTRATANTE";
          this.progressValue = 60;
          break;
        case "TIPO_CONTRATANTE":
          this.step = "TIPO_ESTAGIO";
          this.progressValue = 40;
          break;
        case "TIPO_ESTAGIO":
          this.step = "DADOS_ALUNO";
          this.progressValue = 20;
          break;
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
    <DadosEstagio v-if="step === 'DADOS_ESTAGIO'" />
    <DadosAuxiliares v-if="step === 'DADOS_AUXILIARES'" />

    <div class="w-full flex justify-end gap-2">
      <a
        icon="pi pi-external-link"
        class="p-button-secondary p-button font-bold"
        v-if="step === 'DADOS_ALUNO'"
        href="https://www.prppg.ufpr.br/siga/"
        target="_blank"
      >
        Acessar SIGA
      </a>
      <Button
        @click="handleBackStep"
        label="Voltar"
        class="p-button-secondary"
        icon="pi pi-arrow-left"
        v-else
      />
      <Button
        @click="handleAdvanceStep"
        :label="step === 'DADOS_AUXILIARES' ? 'Gerar termo' : 'Avançar'"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<style></style>
