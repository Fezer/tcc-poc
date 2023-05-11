<script lang="ts">
import { defineComponent, ref } from "vue";
import aluno from "../../components/common/aluno.vue";
import Contratante from "../../components/common/contratante.vue";
import planoAtividades from "../../components/common/plano-atividades.vue";
import historicoDeMudancasVue from "./historicoDeMudancas.vue";
import relatoriosVue from "./relatorios.vue";
import Status from "../../components/common/statusEstagio.vue";

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
    const route = useRoute();

    const { id } = route.params;

    const { data: estagio, refresh } = await useFetch(
      `http://localhost:5000/estagio/${id}`
    );

    console.log(estagio);

    // const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);

    const tipoUsuario = ref("ALUNO" as TipoUsuario);

    const cancelationConfirm = ref(false);

    return {
      tipoUsuario,
      cancelationConfirm,
      estagio,
    };
  },
  methods: {},
});
</script>

<template>
  <div>
    <Toast />
    <small class="m-0">Estágios > Ver estágio</small>
    <h2 class="m-0 mb-4">Estágio</h2>

    <Status :estagio="estagio" tipoUsuario="OUTRO" />

    <Aluno />

    <Estagio :termo="estagio" />

    <PlanoAtividades
      :termo="estagio"
      :planoAtividades="estagio?.planoDeAtividades"
    />

    <Contratante :termo="estagio" />

    <!-- v-for relatorio in estagio?.relatorios -->
    <!-- <div v-for="relatorio in estagio?.relatorioDeEstagio" :key="relatorio">
      <h3>Relatório {{ relatorio }}</h3>
      <NuxtLink :to="`/relatorio/${relatorio}`">
        <Button label="Ver relatório" class="p-button-secondary"></Button>
      </NuxtLink>
    </div> -->
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
