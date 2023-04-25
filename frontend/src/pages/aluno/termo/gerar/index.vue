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
      setTimeout(() => {
        router.push({
          path: "/termo/" + termo.value.id,
        });
      }, 3000);
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
              termo.value.tipoEstagio === "Obrigatorio" ? 80 : 100;
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
            termo.value.tipoEstagio === "Obrigatorio"
          ) {
            state.step = "DADOS_AUXILIARES";
            state.progressValue = 100;
            break;
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
          state.progressValue = 100;

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
      termo: termo.value,
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
    />
    <TipoContratante
      v-if="state.step === 'TIPO_CONTRATANTE'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
    <DadosEstagio
      v-if="state.step === 'DADOS_ESTAGIO'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
      :finalStep="!termo?.estagioUfpr"
    />
    <DadosAuxiliares
      v-if="state.step === 'DADOS_AUXILIARES'"
      :backStep="handleBackStep"
      :advanceStep="handleAdvanceStep"
    />
  </div>
</template>

<style></style>
