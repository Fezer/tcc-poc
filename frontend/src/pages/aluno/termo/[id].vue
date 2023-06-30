<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive, ref } from "vue";
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

    const { auth } = useAuth();

    const grr: string = auth?.value?.identifier || "";

    const alunoService = new AlunoService();

    const novoEstagioService = new NovoEstagioService();

    const { setTermo } = useTermo();

    const { data: termo, refresh } = useFetch(`/termo/${id}`);

    const uploaded = ref(false);

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

    const handleEditarTermo = () => {
      setTermo({
        ...termo.value.estagio,
        ...termo.value.planoAtividades,
        ...termo.value,
        estagio: termo?.value?.estagio,
      });

      router.push({
        path: "/aluno/termo/gerar",
      });
    };

    const handleCancelarTermo = async () => {
      try {
        await novoEstagioService.cancelarTermo(termo.value.estagio?.id, grr);
        toast.add({
          severity: "success",
          summary: `Termo cancelado!`,
          detail: `O termo de compromisso foi cancelado com sucesso!`,
          life: 3000,
        });
        state.cancelationConfirm = false;

        router.push({
          path: "/aluno",
        });
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

      await novoEstagioService
        .solicitarAprovacaoTermo(grr, termo.value.id)
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

    const handleUploadTermo = async (event) => {
      const file = event.files[0];

      const formData = new FormData();
      formData.append("file", file);

      await alunoService
        .uploadTermo(grr, termo?.value?.id, formData)
        .then(() => {
          uploaded.value = true;
          toast.add({
            severity: "success",
            summary: `Termo enviado!`,
            detail: `O termo de compromisso foi enviado com sucesso!`,
            life: 3000,
          });
          state.uploadModalVisible = false;
          refresh().then(() => (state.uploadModalVisible = true));
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Tivemos um problema ao enviar o termo!",
            detail: err?.response?.data?.error || "Erro inesperado.",
            life: 3000,
          });
        });
    };

    const handleDownloadTermoBase = async () => {
      try {
        const url = `/aluno/${grr}/gerar-termo`;

        const file = await $fetch(url, {
          method: "GET",
        });

        const fileURL = URL.createObjectURL(file);

        return window.open(fileURL, "_blank");
      } catch (err) {
        console.error(err);
        return toast.add({
          severity: "error",
          summary: "Ops!",
          detail: "Tivemos um problema ao baixar o termo.",
          life: 3000,
        });
      }
    };
    const handleDownloadTermoAssinado = async () => {
      try {
        const url = `/aluno/${grr}/termo-de-compromisso/${termo?.value?.id}/download`;

        const file = await $fetch(url, {
          method: "GET",
        });

        const fileURL = URL.createObjectURL(file);

        return window.open(fileURL, "_blank");
      } catch (err) {
        console.error(err);
        return toast.add({
          severity: "error",
          summary: "Ops!",
          detail: "Tivemos um problema ao baixar o termo.",
          life: 3000,
        });
      }
    };
    return {
      termo,
      refreshData,
      state,
      handleEditarTermo,
      handleSolicitarAprovacao,
      handleCancelarTermo,
      handleUploadTermo,
      uploaded,
      handleDownloadTermoBase,
      handleDownloadTermoAssinado,
    };
  },
});
</script>

<template>
  <div class="relative">
    <small>Processos > Ver processo</small>
    <h2>Termo de Compromisso</h2>

    <div class="absolute right-0 top-10 gap-2 flex">
      <Button
        label="Baixar documento base"
        class="p-button-secondary"
        icon="pi pi-file"
        @click="handleDownloadTermoBase"
        v-if="['EmPreenchimento', 'EmRevisao'].includes(termo?.statusTermo)"
      />
      <Button
        label="Baixar documento assinado"
        class="p-button-secondary"
        icon="pi pi-file"
        @click="handleDownloadTermoAssinado"
        v-if="termo?.uploadCompromisso"
      />
      <NuxtLink :to="`/aluno/estagio/${termo?.estagio?.id}`">
        <Button
          label="Ver estágio"
          icon="pi pi-eye"
          class="p-button-secondary"
        />
      </NuxtLink>
    </div>

    <StatusTermo
      :etapa="termo?.etapaFluxo"
      :status="termo?.statusTermo"
      :motivo="termo?.motivoIndeferimento"
      :termo="termo"
      v-if="termo?.statusTermo"
    />

    <Aluno
      v-if="!!termo"
      :showDadosAuxiliares="
        termo?.estagio?.estagioUfpr &&
        termo?.estagio?.tipoEstagio === 'NaoObrigatorio'
      "
    />

    <Estagio :termo="termo" />

    <!-- <DadosAuxiliares :termo="termo" /> -->

    <PlanoAtividades :termo="termo" :planoAtividades="termo?.planoAtividades" />

    <Contratante :termo="termo" />

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

    <div v-if="state.uploadModalVisible">
      <UploadConfirm
        header="Upload do termo assinado."
        :onConfirm="handleSolicitarAprovacao"
        :handleUpload="handleUploadTermo"
        :confirmBlocked="
          !termo?.estagio?.estagioSeed && !termo?.uploadCompromisso
        "
        :onClose="() => (state.uploadModalVisible = false)"
        description="Para solicitar a aprovação do seu termo de compromisso, por favor faça o upload do termo assinado pelo supervisor do estágio (na contratante), seu professor(a) orientador(a) e por você."
      />
    </div>

    <div v-if="state.cancelationConfirm">
      <CancelationConfirm
        :onConfirm="handleCancelarTermo"
        description="Tem certeza que deseja cancelar esse termo de compromisso? Para começar
        em um estágio, será necessário iniciar todo o processo novamente."
        :onClose="() => (state.cancelationConfirm = false)"
      />
    </div>
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
