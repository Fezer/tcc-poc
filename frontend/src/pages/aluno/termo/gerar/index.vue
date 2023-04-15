<script lang="ts">
import DadosAluno from "./dadosAluno.vue";
import DadosEstagio from "./dadosEstagio.vue";
import DadosAuxiliares from "./dadosAuxiliares.vue";
import TipoContratante from "./tipoContratante.vue";
import TipoEstagio from "./tipoEstagio.vue";

import { defineComponent, reactive } from "vue";
import { useToast } from "primevue/usetoast";

type Steps =
  | "DADOS_ALUNO"
  | "TIPO_ESTAGIO"
  | "TIPO_CONTRATANTE"
  | "DADOS_ESTAGIO"
  | "DADOS_AUXILIARES";

export default defineComponent({
  components: {
    DadosAluno,
    TipoEstagio,
    TipoContratante,
    DadosAuxiliares,
    DadosEstagio,
  },
  setup() {
    const toast = useToast();
    const state = reactive({
      step: "DADOS_ALUNO" as Steps,
      progressValue: 20,

      estagioUfpr: null as Boolean | null,

      dadosEstagio: null,
      dadosTipoEstagio: null,
      tipoContratante: null,
      dadosAuxiliares: null,
    });

    const handleGenerateTerm = () => {
      toast.add({
        severity: "success",
        summary: "Termo gerado com sucesso",
        life: 3000,
      });

      const termo = {
        ...state.dadosTipoEstagio,
        ...state.tipoContratante,
        ...state.dadosEstagio,
        ...state.dadosAuxiliares,
      };

      console.log(termo);
    };

    const handleAdvanceStep = (stepData: any) => {
      switch (state.step) {
        case "DADOS_ALUNO":
          state.step = "TIPO_ESTAGIO";
          state.progressValue = 40;
          break;
        case "TIPO_ESTAGIO":
          state.estagioUfpr = stepData.localEstagio === "UFPR";
          state.dadosTipoEstagio = stepData;

          if (state.estagioUfpr) {
            state.step = "DADOS_ESTAGIO";
            state.progressValue = 80;
            return;
          }

          state.step = "TIPO_CONTRATANTE";
          state.progressValue = 60;
          break;
        case "TIPO_CONTRATANTE":
          state.step = "DADOS_ESTAGIO";
          state.progressValue = state.estagioUfpr ? 80 : 100;

          state.tipoContratante = stepData;
          break;
        case "DADOS_ESTAGIO":
          state.dadosEstagio = stepData;

          if (!state.estagioUfpr) {
            return handleGenerateTerm();
          }
          state.step = "DADOS_AUXILIARES";
          state.progressValue = 100;

          break;
        case "DADOS_AUXILIARES":
          state.dadosAuxiliares = stepData;
          return handleGenerateTerm();
      }
    };
    const handleBackStep = (stepData: any) => {
      switch (state.step) {
        case "DADOS_AUXILIARES":
          state.step = "DADOS_ESTAGIO";
          state.progressValue = 100;

          state.dadosAuxiliares = stepData;
          break;
        case "DADOS_ESTAGIO":
          state.dadosEstagio = stepData;
          if (state.estagioUfpr) {
            state.step = "TIPO_ESTAGIO";
            state.progressValue = 40;
            return;
          }
          state.step = "TIPO_CONTRATANTE";
          state.progressValue = 60;

          break;
        case "TIPO_CONTRATANTE":
          state.step = "TIPO_ESTAGIO";
          state.progressValue = 40;

          state.tipoContratante = stepData;
          break;
        case "TIPO_ESTAGIO":
          state.step = "DADOS_ALUNO";
          state.progressValue = 20;

          state.dadosTipoEstagio = stepData;
          break;
      }
    };

    return {
      state,
      handleBackStep,
      handleAdvanceStep,
    };
  },
});
</script>

<template>
  <div>
    <Toast />
    <small>Processos > Iniciar novo estágio</small>
    <h3>Novo termo de compromisso</h3>
    <ProgressBar
      :value="state.progressValue"
      style="height: 10px"
      :show-value="false"
    />

    <DadosAluno
      v-if="state.step === 'DADOS_ALUNO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <TipoEstagio
      v-if="state.step === 'TIPO_ESTAGIO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
      :dados="state.dadosTipoEstagio"
    />
    <TipoContratante
      v-if="state.step === 'TIPO_CONTRATANTE'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
      :dados="state.tipoContratante"
    />
    <DadosEstagio
      v-if="state.step === 'DADOS_ESTAGIO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
      :finalStep="!state.estagioUfpr"
      :dados="state.dadosEstagio"
    />
    <DadosAuxiliares
      v-if="state.step === 'DADOS_AUXILIARES'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
      :dados="state.dadosAuxiliares"
    />
    <!--
    <div class="w-full flex justify-end gap-2">
      <a
        icon="pi pi-external-link"
        class="p-button-secondary p-button font-bold"
        v-if="state.step === 'DADOS_ALUNO'"
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
