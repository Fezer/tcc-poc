<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, ref } from "vue";
import CoeService from "../../../../../services/CoeService";

export default defineComponent({
  setup() {
    const route = useRoute();
    const coeService = new CoeService();

    const { id } = route.params;
    const toast = useToast();

    const estagio = ref({});

    const reprovarConfirmVisible = ref(false);
    const justificativa = ref("");

    const { data: certificado } = useAsyncData("aluno", async () => {
      const response = await $fetch(
        `http://localhost:5000/certificadoDeEstagio/${id}`
      );

      await handleGetEstagio(response?.estagio?.id);
      return response;
    });

    const handleGetEstagio = async (estagioID: string) => {
      const response = await $fetch(
        `http://localhost:5000/estagio/${estagioID}`
      );

      estagio.value = response;
      return response;
    };

    const handleApproveCertficado = async () => {
      await coeService
        .aprovarCertificado(id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Certificado aprovado com sucesso",
            detail: "Certificado aprovado com sucesso",
            life: 3000,
          });
        })
        .catch((err) => {
          toast.add({
            severity: "error",
            summary: "Erro ao aprovar certificado",
            detail: "Erro ao aprovar certificado",
            life: 3000,
          });
        });
    };

    const handleReproveCertificado = async () => {
      await coeService
        .reprovarCertificado(id, justificativa.value)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Certificado reprovado com sucesso",
            detail: "Certificado reprovado com sucesso",
            life: 3000,
          });
        })
        .catch((err) => {
          toast.add({
            severity: "error",
            summary: "Erro ao reprovar certificado",
            detail: "Erro ao reprovar certificado",
            life: 3000,
          });
        })
        .finally(() => {
          reprovarConfirmVisible.value = false;
        });
    };

    return {
      certificado,
      estagio,
      reprovarConfirmVisible,
      justificativa,
      handleApproveCertficado,
      handleReproveCertificado,
    };
  },
});
</script>
<template>
  <div class="m-1">
    <Toast />
    <h2>Parecer certificado de estágio</h2>
    <div class="card grid">
      <div class="col-4">
        <strong>Processo certificado</strong>
        <p>#{{ certificado?.id }}</p>
      </div>
      <div class="col-4">
        <strong>Processo Estágio</strong>
        <p>#{{ estagio?.id }}</p>
      </div>

      <div class="col-4">
        <strong>Etapa fluxo</strong>
        <p>Parecer COE</p>
      </div>
    </div>

    <Estagio :termo="estagio" />

    <div class="w-full flex justify-end gap-2">
      <NuxtLink :to="`/estagio/${estagio?.id}`">
        <Button
          label="Ver mais informações do estágio"
          icon="pi pi-arrow-right"
        ></Button>
      </NuxtLink>
      <Button
        label="Reprovar certificado"
        icon="pi pi-times"
        class="p-button-danger"
        @click="reprovarConfirmVisible = true"
      ></Button>
      <Button
        label="Aprovar certificado"
        icon="pi pi-check"
        class="p-button-success"
        @click="handleApproveCertficado"
      ></Button>
    </div>

    <Dialog
      :visible="!!reprovarConfirmVisible"
      header="Justificativa reprovação certificado"
      style="min-width: 500px"
      :modal="true"
      :closable="false"
    >
      <div class="flex align-items-center justify-content-center flex-column">
        <Textarea
          id="justificativa"
          v-model="justificativa"
          style="min-width: 100%"
          name="justificativa"
          cols="30"
          rows="10"
        />
      </div>
      <template #footer>
        <Button
          label="Cancelar"
          icon="pi pi-arrow-left"
          class="p-button-secondary"
          @click="reprovarConfirmVisible = false"
        />
        <Button
          label="Reprovar"
          icon="pi pi-times"
          class="p-button-danger"
          autofocus
          @click="() => handleReproveCertificado()"
        />
      </template>
    </Dialog>
  </div>
</template>
