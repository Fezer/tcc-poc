<script lang="ts">
import { defineComponent } from "vue";
import TermoDeRescisaoService from "~~/services/TermoDeRescisaoService";
import { PeriodoRecesso } from "~~/src/types/Termos";

export default defineComponent({
  props: {
    estagio: {
      type: Number,
      required: true,
    },
    termo: {
      type: Number,
    },
    periodo: {
      type: Object,
    },
    onRemove: {
      type: Function,
      required: true,
    },
  },
  setup({
    termo,
    estagio,
    periodo,
    onRemove,
  }: {
    termo: number;
    estagio: number;
    periodo?: PeriodoRecesso;
    onRemove?: (periodo: string) => void;
  }) {
    const { auth } = useAuth();

    const grr = auth?.value?.identifier || "";
    const termoService = new TermoDeRescisaoService();

    const handleRemoveRecessPeriod = async () => {
      // caso não exista termo ou estágio, esse período ainda não foi cadastrado no back
      if (!periodo?.id) return onRemove(periodo.dataInicio, "dataInicio");

      await termoService
        .removePeriodoDeRecesso(grr, termo, estagio, periodo.id)
        .then(() => {
          onRemove(periodo.id, "id");
        })
        .catch((err) => {
          console.error(err);
        });
    };

    return {
      periodo,
      handleRemoveRecessPeriod,
      onRemove,
    };
  },
});
</script>
<template>
  <div class="card mb-2">
    <div class="grid">
      <div class="col-5">
        <strong>Data de Início</strong>
        <p>{{ parseDate(periodo?.dataInicio) }}</p>
      </div>

      <div class="col-5">
        <strong>Data de Fim</strong>
        <p>{{ parseDate(periodo?.dataFim) }}</p>
      </div>

      <div class="flex items-center justify-end col-2" v-if="!!estagio">
        <Button
          label="Excluir"
          icon="pi pi-times"
          class="p-button-danger"
          @click="handleRemoveRecessPeriod"
        />
      </div>
    </div>
  </div>
</template>

<style></style>
