<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive, ref } from "vue";
import CoeService from "../../../../services/CoeService";
import aluno from "../../../components/common/aluno.vue";
import Contratante from "../../../components/common/contratante.vue";
import dadosAuxiliaresVue from "../../../components/common/dadosAuxiliares.vue";
import estagio from "../../../components/common/estagio.vue";
import planoAtividades from "../../../components/common/plano-atividades.vue";
import CoafeService from "~~/services/CoafeService";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";

type TipoUsuario = "ALUNO" | "COE" | "COAFE" | "COORD";

export default defineComponent({
  components: {
    Aluno: aluno,
    Estagio: estagio,
    PlanoAtividades: planoAtividades,
    Contratante,
    DadosAuxiliares: dadosAuxiliaresVue,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const toast = useToast();

    const { id } = route.params;

    const coafeService = new CoafeService();

    const { data: termo, refresh } = useFetch(`/termo/${id}`);

    const { data: agentesIntegracao } = useFetch(`/agente-integrador/`);

    function refreshData() {
      refresh();
    }

    const state = reactive({
      confirmAction: null as null | "APROVAR" | "AJUSTAR" | "INDEFERIR",

      agenteIntegracao: null as null | string,

      indeferimentoConfirm: false,
      justificativa: "",

      uploadModalVisible: false,
    });

    const indeferirTermo = async () => {
      if (!state.justificativa || state.justificativa.trim().length === 0) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Justificativa é obrigatória",
          life: 3000,
        });
      }

      return await coafeService
        .reprovarTermo(termo.value.id, state.justificativa)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Termo de compromisso indeferido!",
            life: 3000,
          });
        });
    };

    const aprovarTermo = async () => {
      return await coafeService.aprovarTermo(termo.value.id).then(() => {
        toast.add({
          severity: "success",
          summary: "Sucesso",
          detail: "Termo de compromisso aprovado com sucesso",
          life: 3000,
        });
      });
    };
    const ajustarTermo = async () => {
      if (!state.justificativa || state.justificativa.trim().length === 0) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Justificativa é obrigatória",
          life: 3000,
        });
      }

      return await coafeService
        .solicitarAjustesTermo(termo.value.id, state.justificativa)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Foram solicitados ajustes para esse termo de compromisso!",
            life: 3000,
          });
        });
    };

    const handleParecerTermo = async () => {
      try {
        switch (state.confirmAction) {
          case "APROVAR":
            if (
              state.agenteIntegracao === null &&
              termo?.value?.tipoTermoDeEstagio === "TermoDeCompromisso"
            ) {
              return toast.add({
                severity: "error",
                summary: "Erro",
                detail:
                  "Por favor, selecione um agente de integração para esse estágio.",
                life: 3000,
              });
            }

            if (termo?.value?.tipoTermoDeEstagio === "TermoAditivo") {
              return await aprovarTermo().then(() => {
                toast.add({
                  severity: "success",
                  summary: "Sucesso",
                  detail: "Termo de compromisso aprovado com sucesso",
                  life: 3000,
                });
              });
            }

            try {
              await coafeService
                .associarAgenteIntegradorAoEstagio(
                  termo.value.id,
                  state.agenteIntegracao
                )
                .then(async () => {
                  await aprovarTermo();
                  toast.add({
                    severity: "success",
                    summary: "Sucesso",
                    detail: "Termo de compromisso aprovado com sucesso",
                    life: 3000,
                  });
                });
            } catch (err) {
              toast.add({
                severity: "error",
                summary: "Erro",
                detail: "Erro ao associar agente de integração ao estágio.",
                life: 3000,
              });

              console.error(err);
            }

            break;
          case "AJUSTAR":
            await ajustarTermo();
            break;
          case "INDEFERIR":
            await indeferirTermo();
            break;
        }
      } catch (err) {
        console.error(err);
        toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Erro ao fornecer parecer para este termo!",
          life: 3000,
        });
      } finally {
        refresh();
        state.confirmAction = null;
      }
    };

    const getConfirmationHeader = (): string => {
      switch (state.confirmAction) {
        case "APROVAR":
          return (
            "Confirmar aprovação " +
            parseTipoTermo(termo?.value?.tipoTermoDeEstagio)
          );
        case "AJUSTAR":
          return "Descrição Pedido de Ajuste";
        case "INDEFERIR":
          return "Justificativa Indeferimento";
        default:
          return "Confirmar ação";
      }
    };

    const getConfirmationButtonClass = (): string => {
      switch (state.confirmAction) {
        case "APROVAR":
          return "p-button-success";
        case "AJUSTAR":
          return "p-button-warning";
        case "INDEFERIR":
          return "p-button-danger";
        default:
          return "p-button-secondary";
      }
    };

    return {
      termo,
      refreshData,
      state,
      aprovarTermo,
      getConfirmationHeader,
      getConfirmationButtonClass,
      handleParecerTermo,
      agentesIntegracao,
    };
  },
});
</script>

<template>
  <div>
    <small>Processos > Ver processo</small>
    <h2>{{ parseTipoProcesso(termo?.tipoTermoDeEstagio) }}</h2>

    <NuxtLink
      :to="`/estagio/${termo?.estagio?.id}?perfil=coafe&termo=${termo?.id}`"
    >
      <Button
        label="Ver estágio"
        class="p-button-secondary absolute right-8 top-36"
        icon="pi pi-eye"
      />
    </NuxtLink>

    <Aluno />

    <Estagio :termo="termo" />

    <DadosAuxiliares :termo="termo" />

    <PlanoAtividades :termo="termo" :planoAtividades="termo?.planoAtividades" />

    <Contratante :termo="termo" />

    <SuspensaoEstagio :termo="termo" />

    <div
      v-if="
        termo?.statusTermo === 'EmAprovacao' && state.tipoUsuario !== 'ALUNO'
      "
      class="flex align-items-end justify-content-end gap-2"
    >
      <Button
        class="p-button-success"
        @click="() => (state.confirmAction = 'APROVAR')"
      >
        Aprovar
      </Button>
      <Button
        class="p-button-warning"
        @click="() => (state.confirmAction = 'AJUSTAR')"
      >
        Pedir ajuste
      </Button>
      <Button
        class="p-button-danger"
        @click="() => (state.confirmAction = 'INDEFERIR')"
      >
        Indeferir
      </Button>
    </div>

    <Dialog
      :visible="!!state.confirmAction"
      :header="getConfirmationHeader()"
      style="min-width: 500px"
      :modal="true"
      :closable="false"
    >
      <div class="flex align-items-center justify-content-center flex-column">
        <Textarea
          v-if="state.confirmAction !== 'APROVAR'"
          id="justificativa"
          v-model="state.justificativa"
          style="min-width: 100%"
          name="justificativa"
          cols="30"
          rows="10"
        />
        <div
          v-if="
            state.confirmAction === 'APROVAR' &&
            termo?.tipoTermoDeEstagio === 'TermoDeCompromisso'
          "
          class="w-full"
        >
          <p>Selecionar Agente de integração</p>
          <Dropdown
            class="w-full"
            :options="agentesIntegracao"
            :optionLabel="(a) => `${a.nome} - ${a.cnpj}`"
            optionValue="id"
            v-model="state.agenteIntegracao"
          >
          </Dropdown>
        </div>
      </div>
      <template #footer>
        <Button
          label="Cancelar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="state.confirmAction = null"
        />
        <Button
          label="Confirmar"
          icon="pi pi-check"
          :class="getConfirmationButtonClass()"
          autofocus
          :disabled="
            state.confirmAction === 'APROVAR' &&
            !state.agenteIntegracao &&
            termo?.tipoTermoDeEstagio === 'TermoDeCompromisso'
          "
          @click="() => handleParecerTermo()"
        />
      </template>
    </Dialog>
  </div>
</template>

<style scoped>
.col-4 {
  margin-bottom: 1rem;
}

.text-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px;
  margin-bottom: 1rem;
}
</style>
