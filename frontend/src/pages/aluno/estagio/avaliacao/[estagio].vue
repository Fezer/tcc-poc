<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, ref, onMounted } from "vue";
import AlunoService from "~~/services/AlunoService";

export default defineComponent({
  setup() {
    const config = useRuntimeConfig();
    const route = useRoute();
    const router = useRouter();
    const { estagio: estagioID } = route.params;
    const alunoService = new AlunoService();
    const toast = useToast();

    const { auth } = useAuth();

    const grr = auth?.value?.identifier || "";

    const { data: estagio, refresh } = useFetch(`/estagio/${estagioID}`);

    const { data: ficha, refresh: refreshFicha } = useAsyncData(
      "ficha",
      () => $fetch(`/fichaDeAvaliacao/${estagio?.value?.fichaDeAvaliacao}`),
      {
        watch: [estagio],
      }
    );

    const idFichaAvaliacao = ref("");

    const handleCancel = () => {
      router.push("/aluno/estagio/" + estagioID);
    };

    const handleGerarFichaAvaliacao = async () => {
      await alunoService
        .criarFichaDeAvaliacao(grr, estagioID)
        .then((res) => {
          refresh();
          refreshFicha();
        })
        .catch((err) => {
          console.error(err);

          toast.add({
            severity: "error",
            summary: "Erro ao gerar ficha de avaliação",
            detail: "Erro ao gerar ficha de avaliação",
            life: 3000,
          });
        });
    };

    const handleDownloadBaseDocument = async () => {
      const url = `${config.BACKEND_URL}/aluno/${grr}/${ficha?.value?.id}/gerar-ficha`;

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
          summary: "Erro ao baixar ficha de avaliação",
          detail:
            err?.response?._data?.error || "Erro ao baixar ficha de avaliação",
          life: 3000,
        });
      }
    };

    const handleDownloadUploadedDocument = async () => {
      const url = `/aluno/${grr}/ficha-de-avaliacao/${idFichaAvaliacao.value}/download`;

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
          summary: "Erro ao baixar ficha de avaliação",
          detail:
            err?.response?._data?.error || "Erro ao baixar ficha de avaliação",
          life: 3000,
        });
      }
    };

    const handleUploadFicha = async (event) => {
      const file = event.files[0];

      const formData = new FormData();
      formData.append("file", file);

      await alunoService
        .uploadFichaDeAvaliacao(grr, ficha?.value?.id, formData)
        .then(() => {
          toast.add({
            severity: "success",
            summary: `Ficha de avaliação enviada!`,
            detail: `A ficha de avaliação foi enviada com sucesso!`,
            life: 3000,
          });

          refreshFicha();
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Ops!",
            detail: "Tivemos um problema ao enviar a ficha de avaliação.",
            life: 3000,
          });
        });
    };

    onMounted(() => {
      if (estagio?.value?.fichaDeAvaliacao) {
        idFichaAvaliacao.value = estagio.value.fichaDeAvaliacao;
      }
    });

    return {
      handleCancel,
      handleGerarFichaAvaliacao,
      idFichaAvaliacao,
      handleDownloadBaseDocument,
      handleUploadFicha,
      ficha,
      handleDownloadUploadedDocument,
    };
  },
});
</script>

<template>
  <div>
    <div v-if="!ficha">
      <h1>Gerar Ficha de Avaliação</h1>
      <div class="card">
        <p class="text-lg">
          Deseja gerar uma ficha de avaliação para sua contratante completar?
          Após a criação e aprovação da ficha de avaliação, a emissão do
          certificado de estágio estará disponível.
        </p>
        <div class="gap-2 flex">
          <Button
            label="Cancelar"
            icon="pi pi-times"
            class="p-button-secondary"
            @click="handleCancel"
          ></Button>
          <Button
            label="Gerar ficha de avaliação"
            icon="pi pi-file"
            class="p-button-success"
            @click="handleGerarFichaAvaliacao"
          ></Button>
        </div>
      </div>
    </div>
    <div v-else>
      <h1>Preenchimento ficha de avaliação</h1>
      <div class="card">
        <p class="text-lg" v-if="!ficha?.upload">
          Por favor, baixe o documento base da ficha de avaliação, e peça para
          que seu supervisor de estágio preencha. Após isso, o documento
          assinado.
        </p>

        <div class="flex gap-2" v-if="!ficha?.upload">
          <Button
            label="Baixar documento"
            icon="pi pi-download"
            class="p-button-secondary"
            @click="handleDownloadBaseDocument"
          ></Button>

          <FileUpload
            accept=".pdf"
            :multiple="false"
            :maxFileSize="10000000"
            chooseLabel="Adicionar ficha assinada"
            mode="basic"
            customUpload
            @uploader="handleUploadFicha"
          />
        </div>
        <div v-else>
          <Button
            label="Baixar ficha de avaliação"
            icon="pi pi-download"
            class="p-button-secondary"
            @click="handleDownloadUploadedDocument"
          ></Button>
        </div>
      </div>
    </div>
  </div>
</template>
