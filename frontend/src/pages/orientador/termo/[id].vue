<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import aluno from "../../../components/common/aluno.vue";
import Contratante from "../../../components/common/contratante.vue";
import dadosAuxiliaresVue from "../../../components/common/dadosAuxiliares.vue";
import estagio from "../../../components/common/estagio.vue";
import planoAtividades from "../../../components/common/plano-atividades.vue";
import { BaseTermo } from "../../../types/Termos";

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
    const route = useRoute();

    const { id } = route.params;

    const { data: termo, refresh } = useFetch<BaseTermo>(`/termo/${id}`);

    function refreshData() {
      refresh();
    }

    return {
      termo,
    };
  },
});
</script>

<template>
  <div>
    <small>Processos > Ver processo</small>
    <h2>{{ parseTipoProcesso(termo?.tipoTermoDeEstagio) }}</h2>

    <Aluno :grrAluno="termo?.grrAluno" v-if="termo?.grrAluno" />

    <Estagio :termo="termo" />

    <DadosAuxiliares :termo="termo" />

    <PlanoAtividades :termo="termo" :planoAtividades="termo?.planoAtividades" />

    <Contratante :termo="termo" />

    <SuspensaoEstagio :termo="termo" />
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
