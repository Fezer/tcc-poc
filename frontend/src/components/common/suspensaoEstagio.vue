<script lang="ts">
import { defineComponent } from "vue";
import { BaseTermo } from "~~/src/types/Termos";

export default defineComponent({
  props: {
    termo: {
      type: Object,
      required: true,
    },
  },
  setup({ termo }: { termo: BaseTermo }) {
    return {
      termo,
    };
  },
});
</script>

<template>
  <div
    class="card"
    v-if="!!termo?.dataFimSuspensao && termo?.dataInicioRetomada"
  >
    <h5>Suspensão de Estágio</h5>
    <div class="grid">
      <div class="col-4">
        <strong>Suspensão de Estágio</strong>
        <p>Sim</p>
      </div>

      <div class="col-4">
        <strong>Data de Suspensão</strong>
        <p>{{ parseDate(termo?.dataFimSuspensao) }}</p>
      </div>

      <div class="col-4">
        <strong>Data de Retomada</strong>
        <p>{{ parseDate(termo?.dataInicioRetomada) }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
