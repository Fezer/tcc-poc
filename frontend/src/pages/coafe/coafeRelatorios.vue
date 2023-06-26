<template>
  <div>
    <div class="flex justify-content-between">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
    </div>
    <div class="flex justify-content-between">
      <span class="p-input-icon-left">
        <h3><b>Relatórios</b></h3>
      </span>
    </div>
    <div class="card">
      <h4 class="flex justify-content-center">Gerar Relatórios</h4>
      <div class="w-full flex justify-content-around p-3">
        <Button
          @click="relatorioSeguradora = true"
          :label="'Relatório de Seguradoras'"
          icon="pi pi-file"
          class="p-button-success"
        />
        <Button
          @click="relatorioCertificado = true"
          :label="'Relatório de Certificados'"
          icon="pi pi-file"
          class="p-button-success"
        />
        <Button
          @click="relatorioEstagio = true"
          :label="'Relatório de Estágios'"
          icon="pi pi-file"
          class="p-button-success"
        />
      </div>
    </div>
    <div v-if="relatorioSeguradora">
      <EscolhaRelatorio
        :excel="() => relatorioExcelSeguradora()"
        :pdf="() => relatorioPDFSeguradora()"
        :cancelar="() => (relatorioSeguradora = false)"
        :description="`Gerar relatório das Seguradoras em que formato?`"
      >
      </EscolhaRelatorio>
    </div>
    <div v-if="relatorioCertificado">
      <EscolhaRelatorio
        :excel="() => relatorioExcelCertificados()"
        :pdf="() => relatorioPDFCertificados()"
        :cancelar="() => (relatorioCertificado = false)"
        :description="`Gerar relatório dos Certificados em que formato?`"
      >
      </EscolhaRelatorio>
    </div>
    <div v-if="relatorioEstagio">
      <EscolhaRelatorio
        :excel="() => relatorioExcelEstagios()"
        :pdf="() => relatorioPDFEstagios()"
        :cancelar="() => (relatorioEstagio = false)"
        :description="`Gerar relatório dos Estágios em que formato?`"
      >
      </EscolhaRelatorio>
    </div>
  </div>
</template>
<script lang="ts">
export default defineComponent({
  components: { EscolhaRelatorio },
});
</script>
<script setup lang="ts">
import Button from "primevue/button";
import { ref } from "vue";
import { defineComponent } from "vue";
import { useToast } from "primevue/usetoast";
import SeguradoraService from "~~/services/SeguradoraService";
import EscolhaRelatorio from "~~/src/components/common/escolha-relatorios.vue";
import CoafeService from "~~/services/CoafeService";
import RelatorioEstagioService from "~~/services/RelatorioEstagioService";
let relatorioSeguradora = ref(false);
let relatorioCertificado = ref(false);
let relatorioEstagio = ref(false);
const toast = useToast();
const seguradoraService = new SeguradoraService();
const coafeService = new CoafeService();
const relatorioService = new RelatorioEstagioService();
const relatorioExcelSeguradora = async () => {
  relatorioSeguradora.value = false;
  try {
    const file = await seguradoraService.baixarRelatorioExcel();
    var blob = new Blob([file], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });

    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = `relatorio-de-seguradoras-excel.xlsx`;
    link.click();
  } catch (Error) {
    if (Error?.response?._data?.error) {
      return toast.add({
        severity: "error",
        summary: "Erro",
        detail: "" + Error?.response?._data?.error,
        life: 3000,
      });
    }
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Não foi possível baixar o Relatório",
      life: 3000,
    });
  }
};
const relatorioPDFSeguradora = async () => {
  relatorioSeguradora.value = false;
  try {
    const file = await seguradoraService.baixarRelatorioPdf();
    const fileURL = URL.createObjectURL(file);
    return window.open(fileURL, "_blank");
  } catch (Error) {
    if (Error?.response?._data?.error) {
      return toast.add({
        severity: "error",
        summary: "Erro",
        detail: "" + Error?.response?._data?.error,
        life: 3000,
      });
    }
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Não foi possível baixar o Relatório",
      life: 3000,
    });
  }
};
const relatorioExcelCertificados = async () => {
  relatorioCertificado.value = false;
  try {
    const file = await coafeService.baixarRelatorioCertificadoExcel();
    var blob = new Blob([file], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });

    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = `relatorio-de-certificados-excel.xlsx`;
    link.click();
  } catch (Error) {
    if (Error?.response?._data?.error) {
      return toast.add({
        severity: "error",
        summary: "Erro",
        detail: "" + Error?.response?._data?.error,
        life: 3000,
      });
    }
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Não foi possível baixar o Relatório",
      life: 3000,
    });
  }
};
const relatorioPDFCertificados = async () => {
  relatorioCertificado.value = false;
  try {
    const file = await coafeService.baixarRelatorioCertificadoPdf();
    const fileURL = URL.createObjectURL(file);
    return window.open(fileURL, "_blank");
  } catch (Error) {
    if (Error?.response?._data?.error) {
      return toast.add({
        severity: "error",
        summary: "Erro",
        detail: "" + Error?.response?._data?.error,
        life: 3000,
      });
    }
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Não foi possível baixar o Relatório",
      life: 3000,
    });
  }
};

const relatorioExcelEstagios = async () => {
  relatorioEstagio.value = false;
  try {
    const file = await relatorioService.baixarRelatorioEstagioExcel();
    var blob = new Blob([file], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });

    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = `relatorio-de-estagios-excel.xlsx`;
    link.click();
  } catch (error) {
    if (error?.response?._data?.error) {
      toast.add({
        severity: "error",
        summary: "Erro",
        detail: "" + error?.response?._data?.error,
        life: 3000,
      });
    } else {
      toast.add({
        severity: "error",
        summary: "Erro",
        detail: "Não foi possível baixar o Relatório",
        life: 3000,
      });
    }
  }
};
const relatorioPDFEstagios = async () => {
  relatorioEstagio.value = false;
  try {
    const file = await relatorioService.baixarRelatorioEstagioPdf();
    const fileURL = URL.createObjectURL(file);
    return window.open(fileURL, "_blank");
  } catch (Error) {
    if (Error?.response?._data?.error) {
      return toast.add({
        severity: "error",
        summary: "Erro",
        detail: "" + Error?.response?._data?.error,
        life: 3000,
      });
    }
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Não foi possível baixar o Relatório",
      life: 3000,
    });
  }
};
</script>
