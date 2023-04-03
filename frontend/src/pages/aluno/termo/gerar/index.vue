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

      estagioUfpr: null as Boolean | null,

      dadosEstagio: null,
      dadosTipoEstagio: null,
      tipoContratante: null,
      dadosAuxiliares: null,
    };
  },
  methods: {
    handleGenerateTerm() {
      this.$toast.add({
        severity: "success",
        summary: "Termo gerado com sucesso",
        life: 3000,
      });

      const termo = {
        ...this.dadosAluno,
        ...this.dadosTipoEstagio,
        ...this.tipoContratante,
        ...this.dadosEstagio,
        ...this.dadosAuxiliares,
      };

      console.log(termo);
    },

    handleAdvanceStep(stepData: any) {
      switch (this.step) {
        case "DADOS_ALUNO":
          this.step = "TIPO_ESTAGIO";
          this.progressValue = 40;
          break;
        case "TIPO_ESTAGIO":
          this.estagioUfpr = stepData.localEstagio === "UFPR";
          this.dadosTipoEstagio = stepData;

          this.step = "TIPO_CONTRATANTE";
          this.progressValue = 60;
          break;
        case "TIPO_CONTRATANTE":
          this.step = "DADOS_ESTAGIO";
          this.progressValue = this.estagioUfpr ? 80 : 100;

          this.tipoContratante = stepData;
          break;
        case "DADOS_ESTAGIO":
          this.dadosEstagio = stepData;

          if (!this.estagioUfpr) {
            return this.handleGenerateTerm();
          }
          this.step = "DADOS_AUXILIARES";
          this.progressValue = 100;

          break;
        case "DADOS_AUXILIARES":
          this.dadosAuxiliares = stepData;
          return this.handleGenerateTerm();
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
    <Toast />
    <small>Processos > Iniciar novo estágio</small>
    <h3>Novo termo de compromisso</h3>
    <ProgressBar
      :value="progressValue"
      style="height: 10px"
      :show-value="false"
    />

    <DadosAluno
      v-if="step === 'DADOS_ALUNO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <TipoEstagio
      v-if="step === 'TIPO_ESTAGIO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <TipoContratante
      v-if="step === 'TIPO_CONTRATANTE'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <DadosEstagio
      v-if="step === 'DADOS_ESTAGIO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
      :finalStep="!estagioUfpr"
    />
    <DadosAuxiliares
      v-if="step === 'DADOS_AUXILIARES'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <!--
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
    </div> -->
  </div>
</template>

<style></style>
