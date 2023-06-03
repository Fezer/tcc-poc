<script lang="ts">
import { defineComponent, ref } from "vue";
import aluno from "../../components/common/aluno.vue";
import Contratante from "../../components/common/contratante.vue";
import ObjetosDeEstagio from "../../components/common/objetos-de-estagio.vue";
import planoAtividades from "../../components/common/plano-atividades.vue";
import Status from "../../components/common/statusEstagio.vue";

type TipoUsuario = "ALUNO" | "COE" | "COAFE" | "COORD";

export default defineComponent({
  components: {
    Aluno: aluno,
    PlanoAtividades: planoAtividades,
    Contratante,
    Status,
    ObjetosDeEstagio,
  },
  async setup() {
    const route = useRoute();

    const { id } = route.params;

    const { data: estagio, refresh } = await useFetch(
      `http://localhost:5000/estagio/${id}`
    );

    const { perfil, termoDeRescisao, termo } = route.query;
    console.log(estagio);

    // const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);

    const tipoUsuario = ref("ALUNO" as TipoUsuario);

    const cancelationConfirm = ref(false);

    const tipoTermo = termoDeRescisao ? "termo-rescisao" : "termo";

    // ()=> {
    //   if(!!termoDeRescisao) return "termo-rescisao";
    //   return "termo"
    // }

    return {
      tipoUsuario,
      cancelationConfirm,
      estagio,
      perfil,
      termoDeRescisao,
      termo,
      tipoTermo,
    };
  },
  methods: {},
});
</script>

<template>
  <div>
    <NuxtLink
      :to="`/${perfil}/${tipoTermo}/${termoDeRescisao || termo}`"
      v-if="perfil && tipoTermo"
    >
      <Button
        label="Voltar ao Termo"
        icon="pi pi-arrow-left"
        class="p-button-primary absolute right-8"
      >
      </Button>
    </NuxtLink>

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

    <SuspensaoEstagio :termo="estagio" />

    <objetos-de-estagio :estagio="estagio" perfil="coe" />
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
