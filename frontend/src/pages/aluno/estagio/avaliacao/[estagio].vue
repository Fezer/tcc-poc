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

    const idFichaAvaliacao = ref("");

    const handleCancel = () => {
      router.push("/aluno/estagio/" + estagioID);
    };

    const handleGerarFichaAvaliacao = async () => {
      await alunoService
        .criarFichaDeAvaliacao(grr, estagioID)
        .then((res) => {
          idFichaAvaliacao.value = res.id;

          const url = `/aluno/${grr}/${estagioID}/gerar-ficha`;
          window.open(url, "_blank");
        })
        .catch((err) => {
          console.log(err);

          toast.add({
            severity: "error",
            summary: "Erro ao gerar ficha de avaliação",
            detail: "Erro ao gerar ficha de avaliação",
            life: 3000,
          });
        });
    };

    const handleDownloadBaseDocument = async () => {
      const url = `${config.BACKEND_URL}/aluno/${grr}/${estagioID}/gerar-ficha`;

      const file = await $fetch(url, {
        method: "GET",
      });

      const fileURL = URL.createObjectURL(file);

      return window.open(fileURL, "_blank");
    };

    const handleUploadFicha = async (event) => {
      console.log("upload");
      const file = event.files[0];

      const formData = new FormData();
      formData.append("file", file);

      await alunoService
        .uploadFichaDeAvaliacao(grr, formData)
        .then(() => {
          toast.add({
            severity: "success",
            summary: `Ficha de avaliação enviada!`,
            detail: `A ficha de avaliação foi enviada com sucesso!`,
            life: 3000,
          });
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
      console.log(estagio.value);
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
    };
  },
});
</script>

<template>
  <div>
    <div v-if="!idFichaAvaliacao">
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
        <p class="text-lg">
          Por favor, baixe o documento base da ficha de avaliação, e peça para
          que seu supervisor de estágio preencha. Após isso, o documento
          assinado.
        </p>

        <div class="flex gap-2">
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
      </div>
    </div>
  </div>
</template>
