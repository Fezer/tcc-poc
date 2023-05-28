<script lang="ts">
import dayjs from "dayjs";
import { defineComponent } from "vue";
import parseDate from "../../utils/parseDate";
export default defineComponent({
  props: {
    estagio: Object,
    tipoUsuario: String,
  },
  setup({
    estagio,
    tipoUsuario,
  }: {
    estagio: any;
    tipoUsuario: "ALUNO" | "OUTRO";
  }) {
    const getStatusColor = () => {
      const status = estagio?.statusEstagio;

      if (status === "Aprovado") return "text-green-500";
      if (status === "Reprovado") return "text-red-500";
      return "text-orange-500";
    };

    return {
      parseDate,
      tipoUsuario,
      getStatusColor,
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
        <div class="text-box col-3 flex flex-col" v-if="estagio?.dataFinal">
          <strong>Data de Início do Estágio</strong>
          <span>{{ parseDate(estagio?.dataFinal) }}</span>
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
