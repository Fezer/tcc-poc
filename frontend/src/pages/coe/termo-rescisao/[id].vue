<script lang="ts">
import { defineComponent } from "vue";
import { TermoRescisao } from "~~/src/types/Termos";
import periodoRecesso from "../../../components/termo-rescisao/periodo-recesso.vue";
import TermoDeRescisaoService from "~~/services/TermoDeRescisaoService";
import CancelationConfirm from "../../../components/common/cancelation-confirm.vue";
import AlunoService from "~~/services/AlunoService";
import { useToast } from "primevue/usetoast";
import CoeService from "../../../../services/CoeService";

export default defineComponent({
  components: { periodoRecesso, CancelationConfirm },
  setup() {
    const toast = useToast();
    const route = useRoute();
    const router = useRouter();

    const coeService = new CoeService();

    const { id } = route.params;

    const { data: termo } = useFetch<TermoRescisao>("/termoDeRescisao/" + id);

    const cancelVisible = ref(false);

    const handleDarCiencia = async () => {
      await coeService
        .cienciaTermoRescisao(id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Ciência dada com sucesso",
            detail: "Ciência dada com sucesso",
          });
          router.push("/coe/termo-rescisao");
        })
        .catch((err) => {
          console.error(err);
          toast.add({
            severity: "error",
            summary: "Erro ao dar ciência",
            detail: "Não foi possível dar ciência ao termo de rescisão",
          });
        });
    };

    const handleDownloadTermoAssinado = async () => {
      const url = `/coe/${termo?.value?.grrAluno}/termo-de-rescisao/${id}/download`;

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
      termo,
      handleDarCiencia,
      handleDownloadTermoAssinado,
    };
  },
});
</script>
<template>
  <div class="relative">
    <h3>Termo de Rescisão</h3>

    <div class="absolute right-0 -top-2 gap-2 flex">
      <Button
        label="Ver documento"
        class="p-button-secondary"
        icon="pi pi-file"
        @click="handleDownloadTermoAssinado"
      />
    </div>

    <div class="card">
      <h5>Dados do Termo</h5>
      <div class="grid">
        <div class="col-4">
          <strong>Número do Processo</strong>
          <p>#{{ termo?.id }}</p>
        </div>

        <div class="col-4">
          <strong>Data de Término</strong>
          <p>{{ parseDate(termo?.dataTermino) }}</p>
        </div>

        <div class="col-4">
          <strong>Período Total de Recesso</strong>
          <p>{{ termo?.periodoTotalRecesso }} dias</p>
        </div>
      </div>
    </div>

    <h5>Períodos de Recesso</h5>
    <div v-if="!!termo?.periodoRecesso?.length">
      <div v-for="item in termo?.periodoRecesso" :key="item.dataInicio">
        <periodo-recesso :periodo="item" />
      </div>
    </div>

    <div class="w-full flex justify-end gap-2 mt-4">
      <NuxtLink
        :to="`/estagio/${termo?.estagio?.id}?perfil=coe&termoDeRescisao=5`"
      >
        <Button
          label="Ver estágio"
          class="p-button-secondary"
          icon="pi pi-eye"
        />
      </NuxtLink>
      <Button
        label="Ciência de Termo de Rescisão"
        class="p-button-primary"
        icon="pi pi-check"
        @click="handleDarCiencia"
        v-if="termo?.cienciaCOE === false"
      />
    </div>
  </div>
</template>

<style></style>
