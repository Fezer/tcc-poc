<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent } from "vue";
import AlunoService from "~~/services/AlunoService";
import OrientadorService from "~~/services/OrientadorService";
import RelatorioEstagioService from "~~/services/RelatorioEstagioService";
import RelatorioEstagio from "~~/src/types/RelatorioEstagio";

export default defineComponent({
  setup() {
    const route = useRoute();
    const orientadorService = new OrientadorService();
    const { id } = route.params;
    const { data: relatorio } = useFetch<RelatorioEstagio>(
      `/relatorioDeEstagio/${id}`
    );
    const router = useRouter();
    const toast = useToast();

    const handleCienciaRelatorio = async () => {
      await orientadorService
        .cienciaRelatorioEstagio(6, id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Ciência de relatório de estágio realizada com sucesso",
          });
          router.push("/orientador/relatorio");
        })
        .catch((err) => {
          console.log(err);
          toast.add({
            severity: "error",
            summary: "Erro",
            detail: "Erro ao cienciar relatório de estágio",
          });
        });
    };

    return {
      relatorio,
      handleCienciaRelatorio,
    };
  },
});
</script>
<template>
  <div>
    <h1>Relatório de estágio</h1>
    <h4>Dados do relatório</h4>
    <div class="card grid">
      <div class="col-6">
        <strong class="text-md mb-2">Tipo do relatório</strong>
        <p>{{ relatorio?.tipoRelatorio }}</p>
      </div>

      <div class="col-6">
        <strong class="text-md mb-2"> Ciência orientador </strong>

        <p class="font-md">
          {{ relatorio?.cienciaOrientador ? "Sim" : "Não" }}
        </p>
      </div>

      <div class="col-6">
        <strong class="text-md"
          >As atividades programadas que constam no termo de compromisso foram
          realizadas:</strong
        >
        <p class="text-md m-0 mb-2">{{ relatorio?.avalAtividades }}</p>
      </div>

      <div class="col-6">
        <strong class="text-md"
          >De que forma você considera que a realização deste estágio contribuiu
          para sua formação profissional?
        </strong>
        <p class="text-md m-0 mb-2">
          {{ relatorio?.avalFormacaoProfissional }}
        </p>
      </div>

      <div class="col-6">
        <strong class="text-md mb-2 mt-2">
          Como foram as relações interpessoais no local de estágio e o convívio
          no ambiente de trabalho?
        </strong>

        <p class="text-md m-0 mb-2">
          {{ relatorio?.avalRelacoesInterpessoais }}
        </p>
      </div>
      <div class="col-6">
        <strong class="text-md mb-2 mt-2">
          De que forma foram desenvolvidas as atividades em equipe e/ou reuniões
          de planejamento?
        </strong>

        <p class="text-md m-0 mb-2">
          {{ relatorio?.avalDesenvolvimentoAtividades }}
        </p>
      </div>
      <div class="col-6">
        <strong class="text-md mb-2 mt-2">
          Como você considera suas contribuições como estagiário para a unidade
          concedente?
        </strong>

        <p class="text-md m-0 mb-2">{{ relatorio?.avalContribuicaoEstagio }}</p>
      </div>

      <div class="col-6">
        <strong class="text-md mb-2 mt-2">
          Qual a possibilidade da sua efetivação junto à unidade concedente do
          estágio?
        </strong>

        <p class="text-md m-0 mb-2">{{ relatorio?.avalEfetivacao }}</p>
      </div>

      <div class="col-12">
        <strong class="text-md mb-2 mt-2"> Considerações </strong>

        <p class="font-md">{{ relatorio?.consideracoes }}</p>
      </div>
    </div>

    <div class="flex justify-end w-full">
      <Button
        label="Ciência relatório"
        icon="pi pi-check"
        class="p-button-success"
        @click="handleCienciaRelatorio"
      >
      </Button>
    </div>
  </div>
</template>

<style></style>
