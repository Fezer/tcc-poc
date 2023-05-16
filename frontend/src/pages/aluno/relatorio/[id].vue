<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent } from "vue";
import AlunoService from "~~/services/AlunoService";
import RelatorioEstagioService from "~~/services/RelatorioEstagioService";
import RelatorioEstagio from "~~/src/types/RelatorioEstagio";

export default defineComponent({
  setup() {
    const route = useRoute();
    const relatorioService = new RelatorioEstagioService();
    const alunoService = new AlunoService();
    const { id } = route.params;
    const { data: relatorio } = useFetch<RelatorioEstagio>(
      `http://localhost:5000/relatorioDeEstagio/${id}`
    );
    const router = useRouter();
    const toast = useToast();

    console.log(relatorio?.value);

    const handleCancelarRelatorio = async () => {
      const estagio = relatorio?.value?.estagio?.id;
      await relatorioService.cancelarRelatorioDeEstagio(id).then(() => {
        toast.add({
          severity: "success",
          summary: "Sucesso",
          detail: "Relatório cancelado com sucesso",
          life: 3000,
        });

        setTimeout(() => {
          router.push("/aluno/estagio/" + estagio);
        }, 3000);
      });
    };

    const handlePedirCienciaOrientador = async () => {
      const estagio = relatorio?.value?.estagio?.id;
      await alunoService
        .solicitarCienciaDeRelatorioDeEstagio("GRR20200141", estagio, id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail:
              "Solicitação de ciência enviada. Aguarde seu orientador responder.",
            life: 3000,
          });

          setTimeout(() => {
            router.push("/aluno/estagio/" + estagio);
          }, 3000);
        })
        .catch((err) => {
          console.log(err);
          toast.add({
            severity: "error",
            summary: "Erro",
            detail: "Não foi possível enviar a solicitação de ciência",
            life: 3000,
          });
        });
    };

    return {
      relatorio,
      handleCancelarRelatorio,
      handlePedirCienciaOrientador,
    };
  },
});
</script>
<template>
  <div>
    <Toast />
    <h2 class="m-0">Relatório de estágio</h2>
    <h4 class="m-0 mt-2 mb-2">Dados do relatório</h4>
    <div class="card grid mt-2">
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

    <div class="flex gap-2 justify-end" v-if="!relatorio?.cienciaOrientador">
      <Button
        label="Cancelar relatório"
        icon="pi pi-times"
        class="p-button-danger"
        @click="handleCancelarRelatorio"
      ></Button>

      <Button
        label="Pedir ciência orientador"
        icon="pi pi-check"
        class="p-button-success"
        @click="handlePedirCienciaOrientador"
      >
      </Button>
    </div>
  </div>
</template>

<style></style>