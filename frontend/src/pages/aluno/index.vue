<script lang="ts">
import { defineComponent, reactive } from "vue";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import AlunoService from "~~/services/AlunoService";
import NovoEstagioService from "../../../services/NovoEstagioService";
import { ofetch } from "ofetch";

export default defineComponent({
  components: { DataTable, Column },
  setup() {
    const { auth } = useAuth();

    const grr = auth?.id || "";
    const config = useRuntimeConfig();
    const alunoService = new AlunoService();
    const novoEstagioService = new NovoEstagioService();
    const router = useRouter();

    const { setAluno, alunoData } = useAluno();
    const { setTermo } = useTermo();

    // busca os dados do aluno ao carregar a página
    const { data: aluno, error: errAluno } = useAsyncData("aluno", async () => {
      if (alunoData?.value) return alunoData.value;

      const response = await alunoService
        .getAlunoFromSiga(grr)
        .then(async (res) => {
          setAluno(res);

          // busca por estágio em revisão, estágios ativos, estágio
          // em processo de aprovação e termo de compromisso em termo de aprovação
          // respectivamente
          // caso exista, redireciona para página referente ao processo
          const emRevisao = await alunoService
            .getEstagioEmRevisao(grr)
            .then((res) => {
              if (res && res.length > 0) {
                console.log("AJUSTE");
                router.push({
                  path: "/aluno/termo/" + res[0]?.termoDeCompromisso,
                });
                return true;
              }
              return false;
            });

          if (emRevisao) return;

          const emAndamento = await alunoService
            .getEstagioEmAndamento(grr)
            .then((res) => {
              if (res && res.length > 0) {
                router.push({
                  path: "/aluno/estagio/" + res[0].id,
                });
                return true;
              }
              return false;
            });

          if (emAndamento) return;

          const emAprovacao = await novoEstagioService
            .getTermoEmAprovacao(grr)
            .then((res) => {
              if (res && res.length > 0) {
                setTermo(res[0]);
                router.push({
                  path: "/aluno/termo/" + res[0].id,
                });
                return true;
              }
              return false;
            });

          if (emAprovacao) return;

          await novoEstagioService.getTermoEmPreenchimento(grr).then((res) => {
            if (res && res.length > 0) {
              setTermo(res[0]);
              router.push({
                path: "/aluno/termo/" + res[0].id,
              });
              return true;
            }
            return false;
          });

          return res;
        });
      return response;
    });

    return {
      aluno,
      termo: [],
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
