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
    const { data: relatorio, refresh } = useFetch<RelatorioEstagio>(
      `/relatorioDeEstagio/${id}`
    );
    const router = useRouter();
    const toast = useToast();
    const { auth } = useAuth();

    const grr = auth?.value?.identifier || "";

    const uploadVisible = ref(false);

    const cancelVisible = ref(false);

    const handleCancelarRelatorio = async () => {
      const estagio = relatorio?.value?.estagio?.id;
      await relatorioService.cancelarRelatorioDeEstagio(id).then(() => {
        toast.add({
          severity: "success",
          summary: "Sucesso",
          detail: "Relatório cancelado com sucesso",
          life: 3000,
        });

        router.push("/aluno/estagio/" + estagio);
      });
    };

    const parseTipoRelatorio = (
      tipo: "RelatorioParcial" | "RelatorioFinal"
    ) => {
      const tipos = {
        RelatorioParcial: "Relatório Parcial",
        RelatorioFinal: "Relatório Final",
      };

      return tipos[tipo] || "Relatório";
    };

    const handlePedirCienciaOrientador = async () => {
      const estagio = relatorio?.value?.estagio?.id;
      await alunoService
        .solicitarCienciaDeRelatorioDeEstagio(grr, estagio, id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail:
              "Solicitação de ciência enviada. Aguarde seu orientador responder.",
            life: 3000,
          });

          router.push("/aluno/estagio/" + estagio);
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

    const handleDownloadRelatorioBase = async () => {
      try {
        const file = await relatorioService.baixarRelatorioBase(grr, id);

        const fileURL = URL.createObjectURL(file);

        return window.open(fileURL, "_blank");
      } catch (err) {
        console.log(err);
        toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Não foi possível baixar o relatório",
          life: 3000,
        });
      }
    };

    const handleUploadRelatorio = async (event: any) => {
      console.log("upload");
      const file = event.files[0];

      const formData = new FormData();
      formData.append("file", file);

      await relatorioService
        .uploadRelatorio(grr, id, formData)
        .then(() => {
          toast.add({
            severity: "success",
            summary: `Relatório de Estágio enviado!`,
            detail: `O relatório de estágio foi enviado com sucesso!`,
            life: 3000,
          });

          uploadVisible.value = false;
          refresh().then(() => {
            uploadVisible.value = true;
          });
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Ops!",
            detail: "Tivemos um problema ao enviar o relatório.",
            life: 3000,
          });
        });
    };

    const handleDownloadRelatorioAssinado = async () => {
      const url = `/aluno/${grr}/relatorio-de-estagio/${id}/download`;

      try {
        const file = await $fetch(url, {
          method: "GET",
        });

        const fileURL = URL.createObjectURL(file);

        return window.open(fileURL, "_blank");
      } catch (err) {
        console.error(err);
        toast.add({
          severity: "error",
          summary: "Ops!",
          detail:
            err?.response?._data?.error ||
            "Tivemos um problema ao baixar o termo.",
          life: 3000,
        });
      }
    };

    return {
      relatorio,
      handleCancelarRelatorio,
      parseTipoRelatorio,
      handlePedirCienciaOrientador,
      handleDownloadRelatorioBase,
      uploadVisible,
      handleUploadRelatorio,
      cancelVisible,
      handleDownloadRelatorioAssinado,
    };
  },
});
</script>
<template>
  <div class="relative">
    <h2 class="m-0">Relatório de estágio</h2>
    <h4 class="m-0 mt-2 mb-2">Dados do relatório</h4>

    <div class="absolute right-0 top-4 gap-2 flex">
      <Button
        label="Baixar relatório base"
        class="p-button-secondary"
        icon="pi pi-file"
        v-if="
          relatorio?.etapaFluxo === 'Aluno' && !relatorio?.cienciaOrientador
        "
        @click="handleDownloadRelatorioBase"
      />
      <Button
        label="Baixar relatório assinado"
        class="p-button-secondary"
        icon="pi pi-file"
        @click="handleDownloadRelatorioAssinado"
        v-if="relatorio?.uploadFinal || relatorio?.uploadParcial"
      />
    </div>

    <div class="card grid mt-2">
      <div class="col-6">
        <strong class="text-md mb-2">Tipo do relatório</strong>
        <p>{{ parseTipoRelatorio(relatorio?.tipoRelatorio) }}</p>
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

    <div class="flex gap-2 justify-end">
      <Button
        v-if="relatorio?.etapaFluxo === 'Aluno'"
        label="Cancelar relatório"
        icon="pi pi-times"
        class="p-button-danger"
        @click="cancelVisible = true"
      ></Button>

      <Button
        v-if="relatorio?.etapaFluxo === 'Aluno'"
        label="Pedir ciência orientador"
        icon="pi pi-check"
        class="p-button-success"
        @click="uploadVisible = true"
      >
      </Button>
    </div>

    <div v-if="!!cancelVisible">
      <CancelationConfirm
        :onClose="() => (cancelVisible = false)"
        :onConfirm="handleCancelarRelatorio"
        description="Você realmente deseja cancelar esse relatório de estágio? Essa ação não poderá ser desfeita."
      >
      </CancelationConfirm>
    </div>

    <div v-if="uploadVisible">
      <UploadConfirm
        :onClose="() => (uploadVisible = false)"
        :onConfirm="handlePedirCienciaOrientador"
        description="Você precisa enviar esse relatório de estágio assinado pela contratante para pedir ciência."
        :handleUpload="handleUploadRelatorio"
        :confirmBlocked="!relatorio?.uploadFinal && !relatorio?.uploadParcial"
      >
      </UploadConfirm>
    </div>
  </div>
</template>

<style></style>
