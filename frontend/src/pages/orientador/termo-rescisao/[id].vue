<script lang="ts">
import { defineComponent } from "vue";
import { TermoRescisao } from "~~/src/types/Termos";
import periodoRecesso from "../../../components/termo-rescisao/periodo-recesso.vue";
import CancelationConfirm from "../../../components/common/cancelation-confirm.vue";
import { useToast } from "primevue/usetoast";
import OrientadorService from "~~/services/OrientadorService";

export default defineComponent({
  components: { periodoRecesso, CancelationConfirm },
  setup() {
    const toast = useToast();
    const route = useRoute();
    const router = useRouter();

    const orientadorService = new OrientadorService();

    const { auth } = useAuth();

    const { id } = route.params;

    const { data: termo } = useFetch<TermoRescisao>("/termoDeRescisao/" + id);

    const cancelVisible = ref(false);

    const handleDarCiencia = async () => {
      await orientadorService
        .cienciaTermoRescisao(auth?.value?.identifier, id)
        .then(() => {
          toast.add({
            severity: "success",
            summary: "Ciência dada com sucesso",
            detail: "Ciência dada com sucesso",
          });
          router.push("/orientador/termo-rescisao");
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

    return {
      termo,
      handleDarCiencia,
    };
  },
});
</script>
<template>
  <div>
    <h3>Termo de Rescisão</h3>

    <div class="card">
      <h5>Dados Termo</h5>
      <div class="grid">
        <div class="col-4">
          <strong>Processo</strong>
          <p>#{{ termo?.id }}</p>
        </div>

        <div class="col-4">
          <strong>Data de Término</strong>
          <p>{{ parseDate(termo?.dataTermino) }}</p>
        </div>

        <div class="col-4">
          <strong>Período total de recesso</strong>
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
        :to="`/estagio/${termo?.estagio?.id}?perfil=orientador&termoDeRescisao=5`"
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
        v-if="termo?.cienciaOrientador === false"
      />
    </div>
  </div>
</template>

<style></style>
