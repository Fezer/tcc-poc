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
          :label="'Seguradoras'"
          icon="pi pi-file"
          class="p-button-success"
        />
        <Button
          @click="relatorioCertificado = true"
          :label="'Certificados'"
          icon="pi pi-file"
          class="p-button-success"
        />
        <Button
          @click="relatorioEstagio = true"
          :label="'Estágios'"
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
//import XLSX from 'xlsx';
//import ExcelJS from 'exceljs';
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
    console.log(file);
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
/*
const relatorioExcelEstagios = async () => {
  relatorioEstagio.value = false;
  try {
    const file = await relatorioService.baixarRelatorioEstagioExcel();

    // Processar o arquivo Excel usando a biblioteca 'xlsx'
    const workbook = XLSX.read(await file.arrayBuffer(), { type: "array" });
    const sheetName = workbook.SheetNames[0];
    const worksheet = workbook.Sheets[sheetName];
    const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });

    // Criar um novo arquivo Excel usando a biblioteca 'exceljs'
    const workbookNew = new ExcelJS.Workbook();
    const worksheetNew = workbookNew.addWorksheet("Relatório Estágio");

    // Preencher o novo arquivo Excel com os dados do arquivo original
    for (let i = 0; i < jsonData.length; i++) {
      const row = jsonData[i];
      for (let j = 0; j < row.length; j++) {
        worksheetNew.getCell(
          `${XLSX.utils.encode_cell({ r: i + 1, c: j + 1 })}`
        ).value = row[j];
      }
    }

    // Salvar o novo arquivo Excel em formato blob
    const buffer = await workbookNew.xlsx.writeBuffer();
    const blob = new Blob([buffer], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });
    const fileURL = URL.createObjectURL(blob);

    // Abrir o arquivo em uma nova janela do navegador
    window.open(fileURL, "_blank");
  }  catch (Error) {
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
*/
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
