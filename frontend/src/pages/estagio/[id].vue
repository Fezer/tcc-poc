<script lang="ts">
import aluno from "../../components/common/aluno.vue";
import Contratante from "../../components/common/contratante.vue";
import estagio from "../../components/common/estagio.vue";
import planoAtividades from "../../components/common/plano-atividades.vue";
import historicoDeMudancasVue from "./historicoDeMudancas.vue";
import relatoriosVue from "./relatorios.vue";
import Status from "./statusEstagio.vue";

type TipoUsuario = "ALUNO" | "COE" | "COAFE" | "COORD";

export default {
  components: {
    Aluno: aluno,
    Estagio: estagio,
    PlanoAtividades: planoAtividades,
    Contratante,
    Status,
    HistoricoDeMudancas: historicoDeMudancasVue,
    Relatorios: relatoriosVue,
  },
  data() {
    return {
      tipoUsuario: "ALUNO" as TipoUsuario,

      cancelationConfirm: false,
    };
  },
  methods: {},
};
</script>

<script setup lang="ts">
const route = useRoute();

const { id } = route.params;

const { data: termo, refresh } = await useFetch(
  `http://localhost:5000/termo/${id}`
);

// const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);

function refreshData() {
  refresh();
}
</script>

<template>
  <div>
    <Toast />
    <small class="m-0">Estágios > Ver estágio</small>
    <h2 class="m-0 mb-4">Estágio</h2>

    <div class="card" v-if="tipoUsuario === 'ALUNO'">
      <h4>Ações</h4>
      <div class="flex gap-2">
        <Button
          label="Termo de Recisão"
          class="p-button-danger"
          icon="pi pi-times"
          @click="() => (cancelationConfirm = true)"
        />
        <Button
          label="Novo relatório de Estágio"
          class="p-button-danger self-center bg-orange-500"
          icon="pi pi-file"
        />
        <Button
          label="Termo aditivo"
          class="p-button-success"
          icon="pi pi-plus"
        />
      </div>
    </div>

    <Status
      :etapa="termo?.etapaFluxo"
      :status="termo?.statusTermo"
      :motivo="termo?.motivoIndeferimento"
    />

    <Estagio :termo="termo" />

    <PlanoAtividades :termo="termo" />

    <Contratante :termo="termo" />

    <HistoricoDeMudancas />

    <Relatorios />

    <Dialog
      :visible="cancelationConfirm"
      header="Confirmar Termo de Recisão"
      :closable="false"
      style="width: 500px"
      :modal="true"
    >
      <p>
        Tem certeza que deseja iniciar o processo de recisão do estágio? O
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
        <Button
          label="Cancelar"
          icon="pi pi-check"
          class="p-button-danger"
          autofocus
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
