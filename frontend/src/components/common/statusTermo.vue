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
    const { auth } = useAuth();

    const grr = auth?.value?.identifier || "";
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

    const handleDownloadTermoBase = async () => {
      let url = `/aluno/${grr}/gerar-termo`;
      if (termo?.tipoTermoDeEstagio === "TermoAditivo") {
        url = `/aluno/${grr}/termo-aditivo/${termo?.id}/gerar-termo-aditivo`;
      }

      const file = await $fetch(url, {
        method: "GET",
      });

      const fileURL = URL.createObjectURL(file);

      return window.open(fileURL, "_blank");
    };

    return {
      percentage,
      parseDate,
      motivo,
      termo,
      handleDownloadTermoBase,
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
