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

      if (status === "EmRevisao") return 0;

      return 100;
    };

    const percentage = getPercentageByEtapa();

    const handleDownloadTermo = async () => {
      await $fetch("http://localhost:5000/aluno/gerar-termo").then((res) => {
        console.log(res);
      });
    };

    return {
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
        <div class="flex flex-col items-start col-4">
          <strong>Status</strong>

          <Tag
            v-if="status === 'EmAprovacao'"
            :value="`ANÁLISE ${etapa || ''}`"
            class="mr-2 p-2 w-6 text-sm"
            severity="info"
          />
          <StatusTag v-else :status="termo?.statusTermo" />
        </div>
        <div class="text-box col-4 flex flex-col">
          <strong>Data de Início do processo</strong>
          <span>{{ parseDate(termo?.dataCriacao) }}</span>
        </div>
        <div
          class="col-4 flex items-center justify-end"
          v-if="
            ['EmPreenchimento', 'Aprovado'].includes(termo?.statusTermo || '')
          "
        >
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
      <div v-if="!!termo?.descricaoAjustes">
        <strong>Ajustes necessários</strong>
        <p>
          {{ termo?.descricaoAjustes }}
        </p>
      </div>
      <div v-if="!!termo?.motivoIndeferimento">
        <strong>Ajustes necessários</strong>
        <p>
          {{ termo?.motivoIndeferimento }}
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
