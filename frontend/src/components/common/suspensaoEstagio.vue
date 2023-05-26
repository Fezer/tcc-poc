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
    console.log(termo);
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
        <strong>Suspensão de estágio</strong>
        <p>Sim</p>
      </div>

      <div class="col-4">
        <strong>Data de suspensão</strong>
        <p>{{ parseDate(termo?.dataFimSuspensao) }}</p>
      </div>

      <div class="col-4">
        <strong>Data retomada</strong>
        <p>{{ parseDate(termo?.dataInicioRetomada) }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
