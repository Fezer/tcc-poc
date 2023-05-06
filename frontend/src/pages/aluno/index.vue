<script lang="ts">
import { defineComponent, reactive } from "vue";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import AlunoService from "~~/services/AlunoService";
import NovoEstagioService from "../../../services/NovoEstagioService";

export default defineComponent({
  components: { DataTable, Column },
  async setup() {
    const grr = "GRR20200141";
    const alunoService = new AlunoService();
    const novoEstagioService = new NovoEstagioService();
    const router = useRouter();

    const { setAluno } = useAluno();
    const { setTermo } = useTermo();

    const { data: aluno, error: errAluno } = useAsyncData("aluno", async () => {
      const response = await alunoService.getAlunoFromSiga(grr);
      setAluno(response);
      return response;
    });

    const { data: termo, error: errTermo } = useAsyncData("termo", async () => {
      const response = await novoEstagioService.getTermoEmPreenchimento(grr);
      console.log(response);

      if (response && response.length > 0) {
        router.push({
          path: "/termo/" + response[0].id,
        });
      }

      return response;
    });

    return {
      aluno,
      termo,
    };
  },
});
</script>

<template>
  <div>
    <div class="flex flex-col">
      <h2 class="m-0">{{ aluno?.nome }}</h2>
      <p class="m-0">{{ aluno?.matricula }}</p>
      <p>{{ aluno?.email }}</p>
    </div>
    <div
      class="h-full w-full flex items-center justify-center flex-col pt-3"
      v-if="termo && termo[0]"
    >
      <p>
        Você já tem um termo de compromisso em preenchimento! Deseja continuar?
      </p>
      <NuxtLink to="/aluno/termo/gerar">
        <Button label="Continuar preenchimento" icon="pi pi-pencil"></Button>
      </NuxtLink>
    </div>

    <div
      class="h-full w-full flex items-center justify-center flex-col pt-3"
      v-else
    >
      <p>
        Você não tem nenhum estágio ou termo ativo. Deseja iniciar um novo
        termo?
      </p>
      <NuxtLink to="/aluno/termo/gerar">
        <Button
          label="Gerar novo termo de compromisso"
          icon="pi pi-plus"
        ></Button>
      </NuxtLink>
    </div>
  </div>
</template>

<style></style>
