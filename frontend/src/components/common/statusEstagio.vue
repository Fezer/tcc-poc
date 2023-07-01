<script lang="ts">
import dayjs from "dayjs";
import { defineComponent } from "vue";
import parseDate from "../../utils/parseDate";
import { Estagio } from "~~/src/types/NovoEstagio";
export default defineComponent({
  props: {
    estagio: Object,
  },
  setup({ estagio }: { estagio: Estagio }) {
    return {
      parseDate,
      estagio,
      dayjs,
    };
  },
});
</script>

<template>
  <div class="card">
    <h4>Andamento do Estágio</h4>

    <div>
      <div class="grid">
        <div class="flex flex-col items-start col-3">
          <strong>Status</strong>
          <StatusTag :status="estagio?.statusEstagio" />
        </div>
        <div class="text-box col-3 flex flex-col">
          <strong>Data de Início do Estágio</strong>
          <span>{{ parseDate(estagio?.dataInicio) }}</span>
        </div>
        <div class="col-4" v-if="!!estagio?.dataTermino">
          <strong>Data de Término do Estágio</strong>
          <p>{{ parseDate(estagio?.dataTermino) }}</p>
        </div>
        <div class="col-3" v-else></div>
      </div>
      <div>
        <strong>Detalhes</strong>
        <p>
          {{
            estagio?.motivo ||
            "Um relatório de estágio precisa ser enviado a cada 6 meses."
          }}
        </p>
      </div>
    </div>
  </div>
</template>

<style>
.text-red-500 {
  color: red !important;
}
</style>
