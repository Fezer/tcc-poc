<script lang="ts">
import { defineComponent } from "vue";
import NovoEstagio from "../../types/NovoEstagio";
import parseDate from "~/utils/parseDate";

export default defineComponent({
  props: {
    etapa: {
      type: String,
    },
    status: {
      type: String,
    },
    motivo: {
      type: String,
    },
    termo: {
      type: Object,
    },
  },
  setup({
    etapa,
    status,
    motivo,
    termo,
  }: {
    etapa: string;
    status: string;
    motivo: string;
    termo: NovoEstagio;
  }) {
    const getPercentageByEtapa = () => {
      if (status === "EmAprovacao") {
        if (etapa === "COE") return 40;
        if (etapa === "COORD") return 80;
        if (etapa === "COAFE") return 90;
      }

      return 100;
    };

    const percentage = getPercentageByEtapa();

    const parseStatus = (status: string) => {
      if (status === "EmPreenchimento") return "Em preenchimento";
      if (status === "Aprovado") return "Aprovado";
      if (status === "Reprovado") return "Reprovado";
      return "Teste";
    };

    const handleDownloadTermo = async () => {
      await $fetch("http://localhost:5000/aluno/gerar-termo").then((res) => {
        console.log(res);
      });
    };

    return {
      parseStatus,
      percentage,
      parseDate,
      motivo,
      termo,
    };
  },
});
</script>

<template>
  <div class="card">
    <h4>Andamento do processo</h4>

    <ProgressBar
      :value="percentage"
      :show-value="false"
      style="height: 15px"
      class="mb-3"
    />

    <div>
      <div class="grid">
        <div class="text-box col-4">
          <strong>Status</strong>
          <p v-if="status === 'EmAprovacao'" class="text-blue-500">
            ANÁLISE {{ etapa || "" }}
          </p>
          <p
            v-else
            :class="
              status === 'Aprovado'
                ? 'text-green-500 font-bold'
                : 'text-red-500 font-bold'
            "
          >
            {{ parseStatus(status) || "" }}
          </p>
        </div>
        <div class="text-box col-4 flex flex-col">
          <strong>Data de Início do processo</strong>
          <span>{{ parseDate(termo?.dataCriacao) }}</span>
        </div>
        <div class="col-4 flex items-center justify-end">
          <a
            href="http://localhost:5000/aluno/GRR20200141/gerar-termo"
            target="_blank"
          >
            <Button
              label="Baixar documento"
              class="p-button-secondary self-center"
              icon="pi pi-file"
            />
          </a>
        </div>
      </div>
      <div v-if="motivo">
        <strong>Detalhes</strong>
        <p>
          {{
            motivo ||
            "Ut hendrerit nisl a varius euismod. Morbi tincidunt sodales augue, vel ullamcorper diam tincidunt ut. Quisque convallis dolor elit, eu porta odio iaculis et. Vivamus tincidunt fermentum egestas. Nam eu imperdiet elit. Sed lorem massa, rutrum quis feugiat ut, porta quis nibh. Morbi tempus magna at risus pellentesque sodales. Donec quis neque ac tellus mollis eleifend. Mauris nisi eros, congue nec imperdiet porta, accumsan vel orci. Curabitur elementum eleifend felis nec egestas. Vivamus sed dolor vitae turpis aliquet placerat. Vestibulum bibendum tellus eros, sed elementum neque venenatis et. Quisque sit amet venenatis ante, eu fringilla tellus. "
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
