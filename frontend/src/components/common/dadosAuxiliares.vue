<script lang="ts">
import { defineComponent } from "vue";
import NovoEstagio, { TipoEstagio } from "../../types/NovoEstagio";
import parseDate from "~/utils/parseDate";

export default defineComponent({
  props: {
    termo: {
      type: Object,
      required: true,
    },
  },
  setup({ termo }: { termo: NovoEstagio }) {
    const formatMoney = (value: number) => {
      return Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL",
      }).format(value);
    };

    const parseTipoEstagio = (tipo: TipoEstagio) =>
      tipo === "Obrigatorio" ? "Obrigatório" : "Não Obrigatório";

    return {
      formatMoney,
      parseDate,
      parseTipoEstagio,
      termo,
    };
  },
});
</script>

<template>
  <div
    class="card"
    v-if="termo?.estagioUfpr && termo?.tipoEstagio === 'Obrigatorio'"
  >
    <h5>Dados Auxiliares</h5>

    <div class="grid">
      <div class="col-4">
        <strong>Tipo do Estágio</strong>
        <p>{{ parseTipoEstagio(termo?.estagio?.tipoEstagio) }}</p>
      </div>
      <div class="col-4">
        <strong>Estágio na UFPR</strong>
        <p>{{ termo?.estagio?.estagioUfpr ? "Sim" : "Não" }}</p>
      </div>
      <div class="col-4">
        <strong>Jornada Diária</strong>
        <p>{{ termo?.jornadaDiaria }} horas</p>
      </div>
      <div class="col-4">
        <strong>Jornada Semanal</strong>
        <p>{{ termo?.jornadaSemanal }} horas</p>
      </div>

      <div class="col-4">
        <strong>Data de Inicio</strong>
        <p>{{ parseDate(termo?.dataInicio) }}</p>
      </div>
      <div class="col-4">
        <strong>Data prevista de Fim</strong>
        <p>{{ parseDate(termo?.dataTermino) }}</p>
      </div>

      <div class="col-4">
        <strong>Valor da Bolsa Auxílio (mensal)</strong>
        <p>
          {{ formatMoney(termo?.valorBolsa || 0) }}
        </p>
      </div>
      <div class="col-4">
        <strong>Valor do Auxílio Transporte</strong>
        <p>
          {{ formatMoney(termo?.valorTransporte || 0) }}
        </p>
      </div>
    </div>
  </div>
</template>

<style></style>
