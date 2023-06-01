<script lang="ts">
import { defineComponent, ref } from "vue";
import aluno from "../../../components/common/aluno.vue";
import Contratante from "../../../components/common/contratante.vue";
import planoAtividades from "../../../components/common/plano-atividades.vue";
import historicoDeMudancasVue from "./historicoDeMudancas.vue";
import relatoriosVue from "./relatorios.vue";
import Status from "../../../components/common/statusEstagio.vue";
import AlunoService from "~~/services/AlunoService";
import { useToast } from "primevue/usetoast";

type TipoUsuario = "ALUNO" | "COE" | "COAFE" | "COORD";

export default defineComponent({
  components: {
    Aluno: aluno,
    PlanoAtividades: planoAtividades,
    Contratante,
    Status,
    HistoricoDeMudancas: historicoDeMudancasVue,
    Relatorios: relatoriosVue,
  },
  async setup() {
    const { setTermo } = useTermo();
    const route = useRoute();
    const router = useRouter();
    const alunoService = new AlunoService();
    const toast = useToast();

    const { id } = route.params;

    const { data: estagio, refresh } = await useFetch(
      `http://localhost:5000/estagio/${id}`
    );

    const handleNovoTermoAditivo = async () => {
      const grr = "GRR20200141";

      const termoEmProcessoExiste = await alunoService.getTermoAditivoAtivo(
        grr
      );

      if (termoEmProcessoExiste) {
        toast.add({
          severity: "error",
          summary: "Erro ao gerar termo aditivo",
          detail: "Você já possui um termo aditivo em processo de aprovação",
          life: 3000,
        });
        return;
      }

      setTermo(null);
      router.push(`/aluno/termo-aditivo/${estagio?.value?.id}/gerar`);
    };

    const handleRedirectToTermoAditivo = (id: string) => {
      setTermo(null);
      router.push(`/aluno/termo-aditivo/${id}`);
    };

    // const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);

    const tipoUsuario = ref("ALUNO" as TipoUsuario);

    const cancelationConfirm = ref(false);

    const handleSolicitarCertificado = async () => {
      const grr = "GRR20200141";
      await alunoService
        .solicitarCertificadoEstagio(grr, id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Certificado solicitado com sucesso",
            detail:
              "Aguarde aprovação da COE para baixar seu certificado. Você pode acompanhar o processo na aba Certificados.",
            life: 3000,
          });
        })
        .catch((err) => {
          toast.add({
            severity: "error",
            summary: "Erro ao solicitar certificado",
            detail: "Erro ao solicitar certificado",
            life: 3000,
          });
          console.error(err);
        });
    };

    return {
      tipoUsuario,
      cancelationConfirm,
      estagio,
      id,
      handleSolicitarCertificado,
      handleNovoTermoAditivo,
      handleRedirectToTermoAditivo,
    };
  },
});
</script>

<template>
  <div>
    <Toast />
    <small class="m-0">Estágios > Ver estágio</small>
    <h2 class="m-0 mb-4">Estágio</h2>

    <div class="card" v-if="estagio?.statusEstagio === 'Aprovado'">
      <h4>Ações</h4>
      <div class="flex gap-2">
        <Button
          label="Termo de Rescisão"
          class="p-button-danger"
          icon="pi pi-times"
          @click="() => (cancelationConfirm = true)"
        />
        <NuxtLink :to="`/aluno/relatorio/gerar/${id}`">
          <Button
            label="Novo relatório de Estágio"
            class="p-button-danger self-center bg-orange-500"
            icon="pi pi-file"
          />
        </NuxtLink>
        <NuxtLink :to="`/aluno/avaliacao/gerar/${id}`">
          <Button
            label="Ficha de Avaliação"
            class="p-button"
            icon="pi pi-file"
          />
        </NuxtLink>
        <Button
          label="Termo aditivo"
          class="p-button-success"
          icon="pi pi-plus"
          @click="handleNovoTermoAditivo"
        />
        <!-- TODO: só poder solicitar certificado após ficha de avaliação -->
        <Button
          label="Solicitar certificado"
          class="p-button-success"
          icon="pi pi-file"
          @click="handleSolicitarCertificado"
        />
      </div>
    </div>

    <Status :estagio="estagio" tipoUsuario="ALUNO" />

    <Estagio :termo="estagio" />

    <PlanoAtividades
      :termo="estagio"
      :planoAtividades="estagio?.planoDeAtividades"
    />

    <Contratante :termo="estagio" />

    <SuspensaoEstagio :termo="termo" />

    <h3 v-if="estagio?.relatorioDeEstagio?.length">Relatórios de Estágio</h3>

    <div
      class="card flex items-center justify-between"
      v-for="idRelatorio in estagio?.relatorioDeEstagio"
      :key="idRelatorio"
    >
      <h5>Relatório #{{ idRelatorio }}</h5>
      <NuxtLink :to="`/aluno/relatorio/${idRelatorio}`">
        <Button label="Ver relatório" class="p-button-secondary"></Button>
      </NuxtLink>
    </div>

    <h3 v-if="estagio?.termoAdivito?.length">Termos aditivos</h3>

    <div
      class="card flex items-center justify-between"
      v-for="idTermoAditivo in estagio?.termoAdivito"
      :key="idTermoAditivo"
    >
      <h5>Termo Aditivo #{{ idTermoAditivo }}</h5>
      <Button
        label="Ver termo aditivo"
        class="p-button-secondary"
        @click="() => handleRedirectToTermoAditivo(idTermoAditivo)"
      ></Button>
    </div>

    <Dialog
      :visible="cancelationConfirm"
      header="Confirmar Termo de Rescisão"
      :closable="false"
      style="width: 500px"
      :modal="true"
    >
      <p>
        Tem certeza que deseja iniciar o processo de Rescisão do estágio? O
        processo será cancelado e o aluno não poderá mais realizar atividades
        referentes a esse estágio.
      </p>
      <template #footer>
        <Button
          label="Voltar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="cancelationConfirm = false"
        />
        <NuxtLink :to="`/aluno/termo-rescisao/${id}/gerar`">
          <Button
            label="Inicar termo de recisão"
            icon="pi pi-check"
            class="p-button-danger"
            autofocus
          />
        </NuxtLink>
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
