<script lang="ts">
import DadosAluno from "./dadosAluno.vue";
import DadosEstagio from "./dadosEstagio.vue";
import CompletarDadosAuxiliares from "./completarDadosAuxiliares.vue";
import TipoContratante from "./tipoContratante.vue";
import TipoEstagio from "./tipoEstagio.vue";

import { defineComponent, reactive } from "vue";
import { useToast } from "primevue/usetoast";
import { z } from "zod";

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
    CompletarDadosAuxiliares,
    DadosEstagio,
  },
  setup() {
    const router = useRouter();
    const toast = useToast();
    const { termo } = useTermo();

    const state = reactive({
      step: "DADOS_ALUNO" as Steps,
      progressValue: 20,
    });

    const handleGenerateTerm = () => {
      toast.add({
        severity: "success",
        summary: "Termo gerado com sucesso",
        life: 3000,
      });
      router.push({
        path: "/aluno/termo/" + termo.value.id,
      });
    };

    const handleAdvanceStep = () => {
      switch (state.step) {
        case "DADOS_ALUNO":
          state.step = "TIPO_ESTAGIO";
          state.progressValue = 40;
          break;
        case "TIPO_ESTAGIO":
          console.log(state);
          if (termo.value.estagioUfpr) {
            state.step = "DADOS_ESTAGIO";
            state.progressValue =
              termo.value.tipoEstagio === "NaoObrigatorio" ? 80 : 100;
            return;
          }

          state.step = "TIPO_CONTRATANTE";
          state.progressValue = 60;
          break;
        case "TIPO_CONTRATANTE":
          state.step = "DADOS_ESTAGIO";
          state.progressValue = termo.value.estagioUfpr ? 80 : 100;

          break;
        case "DADOS_ESTAGIO":
          if (
            termo.value.estagioUfpr &&
            termo.value.tipoEstagio === "NaoObrigatorio"
          ) {
            console.log("DADOS AUXILIARES");
            state.step = "DADOS_AUXILIARES";
            state.progressValue = 100;

            console.log(state.step);
            return;
          }

          handleGenerateTerm();
          return;
        case "DADOS_AUXILIARES":
          handleGenerateTerm();
          return;
      }
    };
    const handleBackStep = () => {
      switch (state.step) {
        case "DADOS_AUXILIARES":
          state.step = "DADOS_ESTAGIO";
          state.progressValue = 80;

          break;
        case "DADOS_ESTAGIO":
          if (termo.value.estagioUfpr) {
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

          break;
        case "TIPO_ESTAGIO":
          state.step = "DADOS_ALUNO";
          state.progressValue = 20;
          break;
      }
    };

    return {
      state,
      handleBackStep,
      handleAdvanceStep,
      termo: termo,
    };
  },
});
</script>

<template>
  <div>
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
      v-else-if="state.step === 'TIPO_ESTAGIO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <TipoContratante
      v-else-if="state.step === 'TIPO_CONTRATANTE'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <DadosEstagio
      v-else-if="state.step === 'DADOS_ESTAGIO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
      :finalStep="!termo?.estagioUfpr"
    />

    <CompletarDadosAuxiliares
      v-else-if="state.step === 'DADOS_AUXILIARES'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
  </div>
</template>

<style></style>
