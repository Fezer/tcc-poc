<script lang="ts">
import { defineComponent } from "vue";
import { TermoRescisao } from "~~/src/types/Termos";
import periodoRecesso from "../../../components/termo-rescisao/periodo-recesso.vue";
import TermoDeRescisaoService from "~~/services/TermoDeRescisaoService";
import CancelationConfirm from "../../../components/common/cancelation-confirm.vue";
import AlunoService from "~~/services/AlunoService";
import { useToast } from "primevue/usetoast";

export default defineComponent({
  components: { periodoRecesso, CancelationConfirm },
  setup() {
    const toast = useToast();
    const route = useRoute();
    const router = useRouter();
    const { setTermoRescisao } = useTermoRescisao();
    const termoService = new TermoDeRescisaoService();
    const alunoService = new AlunoService();

    const { id } = route.params;

    const { data: termo } = useFetch<TermoRescisao>("/termoDeRescisao/" + id);

    const cancelVisible = ref(false);
    const uploadVisible = ref(false);

    const handleSolicitarAprovacao = async () => {
      uploadVisible.value = false;

      const estagioID = termo?.value?.estagio?.id;
      await alunoService
        .solicitarCienciaDeTermoDeRecisao("GRR20200141", estagioID, id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Termo de rescisão enviado para aprovação",
            detail: "O termo de rescisão foi enviado para aprovação",
          });
          router.push(`/aluno/estagio/${estagioID}`);
        })
        .catch((err) => {
          console.log(err);
          toast.add({
            severity: "error",
            summary: "Erro ao solicitar aprovação",
            detail: "Não foi possível solicitar aprovação do termo de rescisão",
          });
        });
    };

    const handleCancelarTermo = async () => {
      const estagioID = termo?.value?.estagio?.id;
      await termoService
        .cancelaTermoRecisao("GRR20200141", estagioID, id)
        .then(() => {
          router.push(`/aluno/estagio/${estagioID}`);
        })
        .catch((err) => {
          console.log(err);
          toast.add({
            severity: "error",
            summary: "Erro ao cancelar termo",
            detail: "Não foi possível cancelar o termo",
          });
        });
    };

    const handleEditarTermo = async () => {
      if (!!termo) {
        const estagioID = termo?.value?.estagio?.id;
        setTermoRescisao(termo);
        router.push(`/aluno/termo-rescisao/${estagioID}/gerar`);
      }
    };

    const handleUploadTermoRescisao = async (event: any) => {
      console.log("upload");
      const file = event.files[0];

      const formData = new FormData();
      formData.append("file", file);

      await termoService
        .uploadTermoDeRescisao("GRR20200141", formData)
        .then(() => {
          toast.add({
            severity: "success",
            summary: `Termo de rescisão enviado!`,
            detail: `O termo de rescisão foi enviada com sucesso!`,
            life: 3000,
          });
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Ops!",
            detail: "Tivemos um problema ao enviar o termo de rescisão",
            life: 3000,
          });
        });
    };

    const getTodasAsCiencias = () => {
      return (
        !!termo?.value &&
        !!termo?.value?.cienciaCOE &&
        !!termo?.value?.cienciaCOAFE &&
        !!termo?.value?.cienciaCoordenacao &&
        !!termo?.value?.cienciaOrientador
      );
    };

    const todasAsCiencias = getTodasAsCiencias();

    return {
      termo,
      handleSolicitarAprovacao,
      handleCancelarTermo,
      handleEditarTermo,
      cancelVisible,
      getTodasAsCiencias,
      todasAsCiencias,
      uploadVisible,
      handleUploadTermoRescisao,
    };
  },
});
</script>
<template>
  <div>
    <h3>Termo de Rescisão</h3>

    <div class="card">
      <h5>Dados Termo</h5>
      <div class="grid">
        <div class="col-4">
          <strong>Processo</strong>
          <p>#{{ termo?.id }}</p>
        </div>

        <div class="col-4">
          <strong>Data de Término</strong>
          <p>{{ parseDate(termo?.dataTermino) }}</p>
        </div>

        <div class="col-4">
          <strong>Período total de recesso</strong>
          <p>{{ termo?.periodoTotalRecesso }} dias</p>
        </div>

        <div class="col-4">
          <strong>Ciência COE</strong>
          <p>{{ termo?.cienciaCOE ? "Sim" : "Não" }}</p>
        </div>

        <div class="col-4">
          <strong>Ciência Coordenação</strong>
          <p>{{ termo?.cienciaCoordenacao ? "Sim" : "Não" }}</p>
        </div>
        <div class="col-4">
          <strong>Ciência COAFE</strong>
          <p>{{ termo?.cienciaCOAFE ? "Sim" : "Não" }}</p>
        </div>

        <div class="col-4">
          <strong>Ciência Orientador</strong>
          <p>{{ termo?.cienciaOrientador ? "Sim" : "Não" }}</p>
        </div>

        <div class="col-4">
          <strong>Termo Aprovado</strong>
          <p>{{ todasAsCiencias ? "Sim" : "Não" }}</p>
        </div>
      </div>
    </div>

    <h5>Períodos de Recesso</h5>
    <div v-if="!!termo?.periodoRecesso?.length">
      <div v-for="item in termo?.periodoRecesso" :key="item.dataInicio">
        <periodo-recesso :periodo="item" />
      </div>
    </div>

    <div class="w-full flex justify-end gap-2 mt-4" v-if="!todasAsCiencias">
      <Button
        label="Cancelar termo"
        class="p-button-danger"
        icon="pi pi-times"
        @click="cancelVisible = true"
      />
      <Button
        label="Editar termo"
        class="p-button-secondary"
        icon="pi pi-pencil"
        @click="handleEditarTermo"
        v-if="termo?.etapaFluxo === 'Aluno' && !todasAsCiencias"
      />
      <Button
        label="Solicitar Aprovação"
        class="p-button-primary"
        icon="pi pi-check"
        @click="uploadVisible = true"
        v-if="termo?.etapaFluxo === 'Aluno'"
      />
    </div>

    <div v-if="cancelVisible">
      <CancelationConfirm
        :onClose="() => (cancelVisible = false)"
        :onConfirm="handleCancelarTermo"
        description="Você realmente deseja cancelar esse termo? Essa ação não poderá ser desfeita."
      >
      </CancelationConfirm>
    </div>

    <div v-if="uploadVisible">
      <UploadConfirm
        :onClose="() => (uploadVisible = false)"
        :onConfirm="handleSolicitarAprovacao"
        description="Você precisa enviar esse termo de rescisão assinado para solicitar aprovação."
        :handleUpload="handleUploadTermoRescisao"
        :confirmBlocked="false"
      >
      </UploadConfirm>
    </div>
  </div>
</template>

<style></style>
