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

    const handleSolicitarAprovacao = async () => {
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

    console.log(todasAsCiencias);

    return {
      termo,
      handleSolicitarAprovacao,
      handleCancelarTermo,
      handleEditarTermo,
      cancelVisible,
      getTodasAsCiencias,
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
          <p>{{ getTodasAsCiencias ? "Sim" : "Não" }}</p>
        </div>
      </div>
    </div>

    <h5>Períodos de Recesso</h5>
    <div v-if="!!termo?.periodoRecesso?.length">
      <div v-for="item in termo?.periodoRecesso" :key="item.dataInicio">
        <periodo-recesso :periodo="item" />
      </div>
    </div>

    <div class="w-full flex justify-end gap-2 mt-4" v-if="!getTodasAsCiencias">
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
        v-if="termo?.etapaFluxo === 'Aluno' && !getTodasAsCiencias"
      />
      <Button
        label="Solicitar Aprovação"
        class="p-button-primary"
        icon="pi pi-check"
        @click="handleSolicitarAprovacao"
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
  </div>
</template>

<style></style>
