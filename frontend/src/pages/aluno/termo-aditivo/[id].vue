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
    const { auth } = useAuth();

    const grr = auth?.value?.id || "";

    const alunoService = new AlunoService();

    const novoEstagioService = new NovoEstagioService();

    const { setTermo } = useTermo();

    const { data: termo, refresh } = useFetch<BaseTermo>(`/termo/${id}`);

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

    const handleEditarTermo = () => {
      // seta o termo atual para edição
      // no caso do termo aditivo, é necessário para comparar com dados do estágio ativo
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

    const handleUploadTermo = async (event: any) => {
      console.log("upload");
      const file = event.files[0];

      const formData = new FormData();
      formData.append("file", file);

      await alunoService
        .uploadTermoAditivo(grr, formData)
        .then(() => {
          toast.add({
            severity: "success",
            summary: `Termo enviado!`,
            detail: `O termo aditivo foi enviado com sucesso!`,
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

    const handleDownloadTermo = async () => {
      try {
        const file = await alunoService.downloadTermoAditivo(
          grr,
          termo.value.id
        );

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
      handleDownloadTermo,
    };
  },
});
</script>

<template>
  <div class="relative">
    <small>Processos > Termos Aditivos</small>
    <h2>Termo Aditivo</h2>

    <div class="absolute right-0 top-10 gap-2 flex">
      <Button
        label="Ver documento"
        class="p-button-secondary"
        icon="pi pi-file"
        @click="handleDownloadTermo"
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
