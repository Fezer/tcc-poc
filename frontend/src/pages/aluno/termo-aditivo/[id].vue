<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import NovoEstagioService from "../../../../services/NovoEstagioService";
import aluno from "../../../components/common/aluno.vue";
import Contratante from "../../../components/common/contratante.vue";
import dadosAuxiliaresVue from "../../../components/common/dadosAuxiliares.vue";
import estagio from "../../../components/common/estagio.vue";
import planoAtividades from "../../../components/common/plano-atividades.vue";
import StatusTermo from "../../../components/common/statusTermo.vue";
import AlunoService from "~~/services/AlunoService";

type TipoUsuario = "ALUNO" | "COE" | "COAFE" | "COORD";

export default defineComponent({
  components: {
    Aluno: aluno,
    Estagio: estagio,
    PlanoAtividades: planoAtividades,
    Contratante,
    StatusTermo,
    DadosAuxiliares: dadosAuxiliaresVue,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const toast = useToast();

    const { id } = route.params;

    const grr = "GRR20200141";

    const alunoService = new AlunoService();

    const novoEstagioService = new NovoEstagioService();

    const { setTermo } = useTermo();

    const { data: termo, refresh } = useFetch<BaseTermo>(
      `http://localhost:5000/termo/${id}`
    );

    // const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);

    function refreshData() {
      refresh();
    }

    const state = reactive({
      tipoUsuario: "ALUNO" as TipoUsuario,

      indeferimentoConfirm: false,
      cancelationConfirm: false,
      justificativa: "",

      uploadModalVisible: false,
    });
    const checkIfTermoCompleto = () => {
      const necessaryValues = [
        "planoAtividades",
        "orientador",
        "jornadaSemanal",
        "jornadaDiaria",
        "dadaInicio",
        "dataTermino",
        "valorBolsa",
        "valorTransporte",
      ];

      for (const value of necessaryValues) {
        if (!termo.value[value]) {
          return false;
        }
      }
    };

    const responderTermo = async (resposta: any) => {
      state.indeferimentoConfirm = false;
      const respostaFormated =
        resposta === "aprovar" ? "aprovado" : "reprovado";

      await fetch(
        `http://localhost:5000/termo/${resposta}/coafe/${route.params.id}`,
        {
          method: "PUT",
          body: JSON.stringify({
            justificativa: state.justificativa,
          }),
          headers: {
            "Content-Type": "application/json",
          },
        }
      )
        .then(() => {
          console.log("Aprovado com sucesso");
          toast.add({
            severity: "success",
            summary: `${respostaFormated.toUpperCase()}`,
            detail: `Termo ${respostaFormated} com sucesso`,
            life: 3000,
          });
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Ops!",
            detail: "Tivemos um problema ao efetivar a análise do termo.",
            life: 3000,
          });
        });
    };

    const handleEditarTermo = () => {
      setTermo(termo.value);

      router.push({
        path: `/aluno/termo-aditivo/${termo.value.estagio.id}/gerar`,
      });
    };

    const handleCancelarTermo = async () => {
      try {
        await alunoService.cancelarTermoAditivo(
          grr,
          termo.value.id,
          termo.value?.estagio?.id
        );
        toast.add({
          severity: "success",
          summary: `Termo cancelado!`,
          detail: `O termo de compromisso foi cancelado com sucesso!`,
          life: 3000,
        });
        state.cancelationConfirm = false;

        setTimeout(() => {
          router.push({
            path: "/aluno",
          });
        }, 3000);
      } catch (err) {
        toast.add({
          severity: "error",
          summary: "Ops!",
          detail: "Tivemos um problema ao cancelar o termo.",
          life: 3000,
        });

        console.error(err);
      }
    };

    const handleSolicitarAprovacao = async () => {
      state.uploadModalVisible = false;
      // if (!checkIfTermoCompleto()) {
      //   return toast.add({
      //     severity: "error",
      //     summary: "Ops!",
      //     detail:
      //       "Termo incompleto. Por favor, visite a edição de termo, e termine o fluxo!",
      //     life: 3000,
      //   });
      // }
      await novoEstagioService
        .solicitarAprovacaoTermo(termo.value.id)
        .then(() => {
          refreshData();
        })
        .then(() => {
          toast.add({
            severity: "success",
            summary: `Aprovação solicitada!`,
            detail: `Aprovação do termo solicitada com sucesso! O termo de compromisso agora está sob Análise da COE. Acompanhe o status do termo na página de Processos.`,
            life: 3000,
          });
        });
    };

    const handleUploadTermo = async (event: any) => {
      console.log("upload");
      const file = event.files[0];

      const formData = new FormData();
      formData.append("file", file);

      await alunoService
        .uploadTermo("GRR20200141", formData)
        .then(() => {
          toast.add({
            severity: "success",
            summary: `Termo enviado!`,
            detail: `O termo de compromisso foi enviado com sucesso!`,
            life: 3000,
          });
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Ops!",
            detail: "Tivemos um problema ao enviar o termo.",
            life: 3000,
          });
        });
    };

    return {
      termo,
      refreshData,
      state,
      responderTermo,
      handleEditarTermo,
      handleSolicitarAprovacao,
      handleCancelarTermo,
      handleUploadTermo,
    };
  },
});
</script>

<template>
  <div class="relative">
    <Toast />
    <small>Processos > Termos Aditivos</small>
    <h2>Termo Aditivo</h2>

    <NuxtLink :to="`/aluno/estagio/${termo?.estagio?.id}`">
      <Button
        label="Ir para estágio"
        icon="pi pi-arrow-right"
        class="absolute right-0 top-10"
      />
    </NuxtLink>

    <StatusTermo
      :etapa="termo?.etapaFluxo"
      :status="termo?.statusTermo"
      :motivo="termo?.motivoIndeferimento"
      :termo="termo"
    />

    <Estagio :termo="termo" />

    <DadosAuxiliares :termo="termo" />

    <PlanoAtividades :termo="termo" :planoAtividades="termo?.planoAtividades" />

    <SuspensaoEstagio :termo="termo" />

    <div
      v-if="
        termo?.statusTermo === 'EmAprovacao' && state.tipoUsuario !== 'ALUNO'
      "
      class="flex align-items-end justify-content-end gap-2"
    >
      <Button
        class="p-button-success"
        @click="() => responderTermo('aprovar').then(() => refresh())"
      >
        Aprovar
      </Button>
      <Button
        class="p-button-danger"
        @click="() => (state.indeferimentoConfirm = true)"
      >
        Indeferir
      </Button>
    </div>

    <div class="flex align-items-end justify-content-end gap-2">
      <Button
        v-if="
          !['Reprovado', 'Aprovado', 'Cancelado'].includes(
            termo?.statusTermo || ''
          )
        "
        class="p-button-danger"
        @click="() => (state.cancelationConfirm = true)"
      >
        Cancelar termo
      </Button>

      <template
        v-if="
          ['EmPreenchimento', 'EmRevisao'].includes(termo?.statusTermo || '')
        "
      >
        <Button class="p-button-secondary" @click="handleEditarTermo">
          Editar termo
        </Button>

        <Button
          class="p-button-primary"
          @click="state.uploadModalVisible = true"
        >
          Solicitar Aprovação
        </Button>
      </template>
    </div>

    <Dialog
      :visible="state.uploadModalVisible"
      header="Upload do termo assinado."
      :closable="false"
      style="width: 600px"
      :modal="true"
    >
      <p>
        Para solicitar a aprovação do seu termo aditivo, por favor faça o upload
        do termo assinado pelo supervisor do estágio (na contratante), seu
        professor(a) orientador(a) e por você.
      </p>

      <!-- 10MB file size -->
      <FileUpload
        accept=".pdf"
        :multiple="false"
        :maxFileSize="10000000"
        chooseLabel="Adicionar termo"
        mode="basic"
        customUpload
        @uploader="handleUploadTermo"
      />
      <template #footer>
        <Button
          label="Fechar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="state.uploadModalVisible = false"
        />
        <Button
          label="Solicitar Aprovação"
          icon="pi pi-check"
          class="p-button-primary"
          @click="handleSolicitarAprovacao"
        />
      </template>
    </Dialog>

    <Dialog
      :visible="state.cancelationConfirm"
      header="Confirmar cancelamento"
      :closable="false"
      style="width: 500px"
      :modal="true"
    >
      <p>
        Tem certeza que deseja cancelar esse termo aditivo? Você poderá iniciar
        outro processo de termo aditivo.
      </p>
      <template #footer>
        <Button
          label="Voltar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="state.cancelationConfirm = false"
        />
        <Button
          label="Cancelar"
          icon="pi pi-check"
          class="p-button-danger"
          autofocus
          @click="handleCancelarTermo"
        />
      </template>
    </Dialog>

    <Dialog
      :visible="state.indeferimentoConfirm"
      header="Justificativa indeferimento"
      style="min-width: 500px"
      :modal="true"
    >
      <div class="flex align-items-center justify-content-center flex-column">
        <Textarea
          id="justificativa"
          v-model="state.justificativa"
          style="min-width: 100%"
          name="justificativa"
          cols="30"
          rows="10"
        />
      </div>
      <template #footer>
        <Button
          label="Cancelar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="state.indeferimentoConfirm = false"
        />
        <Button
          label="Indeferir"
          icon="pi pi-check"
          class="p-button-danger"
          autofocus
          @click="responderTermo('reprovar').then(() => refreshData())"
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