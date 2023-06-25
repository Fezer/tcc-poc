<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive, onMounted } from "vue";
import CoeService from "../../../../services/CoeService";
import aluno from "../../../components/common/aluno.vue";
import Contratante from "../../../components/common/contratante.vue";
import dadosAuxiliaresVue from "../../../components/common/dadosAuxiliares.vue";
import estagio from "../../../components/common/estagio.vue";
import planoAtividades from "../../../components/common/plano-atividades.vue";
import CoordService from "~~/services/CoordService";
import { BaseTermo } from "~~/src/types/Termos";

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

    const coordService = new CoordService();

    const { data: termo, refresh } = useFetch<BaseTermo>(`/termo/${id}`);

    function refreshData() {
      refresh();
    }

    const state = reactive({
      confirmAction: null as null | "APROVAR" | "AJUSTAR" | "INDEFERIR",

      indeferimentoConfirm: false,
      justificativa: "",

      uploadModalVisible: false,

      cienciaIRA: false,
      cienciaPlanoAtividades: false,
      cienciaFormacaoSupervisor: false,
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

      return await coordService
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
      return await coordService.aprovarTermo(termo.value.id).then(() => {
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

      return await coordService
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
            await aprovarTermo();
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

    const handleCienciaIRA = async () => {
      await coordService
        .darCienciaIRA(termo.value.id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Ciência de IRA registrada com sucesso!",
            life: 3000,
          });
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Erro",
            detail: "Erro ao registrar ciência de IRA!",
            life: 3000,
          });

          state.confirmAction = null;
        });
    };

    const handleCienciaPlanoAtividades = async () => {
      await coordService
        .darCienciaPlanoDeAtividades(termo.value.id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Ciência de Plano de Atividades registrada com sucesso!",
            life: 3000,
          });
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Erro",
            detail: "Erro ao registrar ciência de Plano de Atividades!",
            life: 3000,
          });

          state.confirmAction = null;
        });
    };

    const handleCienciaFormacaoSupervisor = async () => {
      await coordService
        .darCienciaFormacaoSupervisor(termo.value.id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Ciência de Formação de Supervisor registrada com sucesso!",
            life: 3000,
          });
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Erro",
            detail: "Erro ao registrar ciência de Formação de Supervisor!",
            life: 3000,
          });

          state.confirmAction = null;
        });
    };

    const getConfirmationHeader = (): string => {
      switch (state.confirmAction) {
        case "APROVAR":
          return (
            "Confirmar aprovação " +
            parseTipoProcesso(termo?.value?.tipoTermoDeEstagio)
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

    const canConfirmAction = () => {
      if (state.confirmAction === "APROVAR") {
        if (termo?.value?.tipoTermoDeEstagio !== "TermoDeCompromisso") {
          return true;
        }

        return (
          state.cienciaIRA &&
          state.cienciaFormacaoSupervisor &&
          state.cienciaPlanoAtividades
        );
      }
      return state.justificativa.trim().length > 0;
    };

    const handleDownloadTermo = async () => {
      const grrAluno = termo?.value?.grrAluno;
      let url = `/coordenacao/${grrAluno}/termo-de-compromisso/${termo?.value?.id}/download`;
      if (termo?.value?.tipoTermoDeEstagio === "TermoAditivo") {
        url = `/coordenacao/${grrAluno}/termo-aditivo/${termo?.value?.id}/download`;
      }

      const file = await $fetch(url, {
        method: "GET",
      }).catch((err) => {
        console.error(err);
        toast.add({
          severity: "error",
          summary: err?.response?._data?.error || "Erro inesperado",
          detail: "Erro ao baixar o termo!",
          life: 3000,
        });
      });

      const fileURL = URL.createObjectURL(file);

      return window.open(fileURL, "_blank");
    };

    const handleCienciaIndeferimento = async () => {
      try {
        await coordService.cienciaIndeferimentoTermo(termo?.value?.id);

        toast.add({
          severity: "success",
          summary: "Sucesso",
          detail: "Ciência de Indeferimento registrada com sucesso!",
          life: 3000,
        });
        refresh();
      } catch (err) {
        console.error(err);
        toast.add({
          severity: "error",
          summary: "Erro",
          detail:
            err?.response?._data?.error ||
            "Erro ao registrar ciência de Indeferimento!",
          life: 3000,
        });
      }
    };

    onMounted(() => {
      console.log(termo);
      if (termo) {
        state.cienciaIRA = termo?.value?.cienciaCoordenacao?.cienciaIRA;
        state.cienciaPlanoAtividades =
          termo?.value?.cienciaCoordenacao?.cienciaPlanoAtividades;
        state.cienciaFormacaoSupervisor =
          termo?.value?.cienciaCoordenacao?.cienciaFormacaoSupervisor;
      }
    });

    return {
      termo,
      refreshData,
      state,
      aprovarTermo,
      getConfirmationHeader,
      getConfirmationButtonClass,
      handleParecerTermo,
      handleCienciaIRA,
      handleCienciaPlanoAtividades,
      handleCienciaFormacaoSupervisor,
      canConfirmAction,
      handleDownloadTermo,
      handleCienciaIndeferimento,
    };
  },
});
</script>

<template>
  <div>
    <small>Processos > Ver processo</small>
    <h2>
      {{ parseTipoProcesso(termo?.tipoTermoDeEstagio) }}
    </h2>

    <div class="absolute right-8 top-36 flex items-center gap-2">
      <Button
        label="Ver documento"
        class="p-button-secondary"
        icon="pi pi-eye"
        @click="handleDownloadTermo"
      />

      <NuxtLink
        :to="`/estagio/${termo?.estagio?.id}?perfil=coord&termo=${termo?.id}`"
      >
        <Button
          label="Ver estágio"
          class="p-button-secondary"
          icon="pi pi-eye"
        />
      </NuxtLink>
    </div>

    <Aluno :grrAluno="termo?.grrAluno" v-if="termo?.grrAluno" />

    <Estagio :termo="termo" />

    <DadosAuxiliares :termo="termo" />

    <PlanoAtividades :termo="termo" :planoAtividades="termo?.planoAtividades" />

    <Contratante :termo="termo" />

    <SuspensaoEstagio :termo="termo" />

    <div
      class="card"
      v-if="
        termo?.etapaFluxo === 'Coordenacao' &&
        termo?.statusTermo === 'Reprovado'
      "
    >
      <strong>Justificativa indeferimento</strong>
      <p>{{ termo?.motivoIndeferimento }}</p>
    </div>

    <div
      v-if="
        termo?.etapaFluxo === 'Coordenacao' &&
        termo?.statusTermo === 'EmAprovacao'
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

    <div
      class="flex align-items-end justify-content-end gap-2"
      v-if="
        termo?.etapaFluxo === 'Coordenacao' &&
        termo?.statusTermo === 'Reprovado'
      "
    >
      <Button class="p-button-info" @click="handleCienciaIndeferimento">
        Ciência indeferimento
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
          v-else-if="termo?.tipoTermoDeEstagio === 'TermoDeCompromisso'"
          class="flex flex-col justify-center w-full gap-4"
        >
          <span>
            <Checkbox
              @click="handleCienciaIRA"
              v-model="state.cienciaIRA"
              :binary="true"
              :disabled="state.cienciaIRA"
            />
            Ciência IRA
          </span>
          <span>
            <Checkbox
              v-model="state.cienciaPlanoAtividades"
              :disabled="state.cienciaPlanoAtividades"
              :binary="true"
              @click="handleCienciaPlanoAtividades"
            />
            Ciência Plano de Atividades
          </span>

          <span>
            <Checkbox
              v-model="state.cienciaFormacaoSupervisor"
              :binary="true"
              :disabled="state.cienciaFormacaoSupervisor"
              @click="handleCienciaFormacaoSupervisor"
            />
            Ciência Formação do Supervisor
          </span>
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
          @click="() => handleParecerTermo()"
          :disabled="!canConfirmAction()"
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
