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
        :description="`Gerar relatório das Seguradoras em que formato?`"
      >
      </EscolhaRelatorio>
    </div>
    <div v-if="relatorioEstagio">
      <EscolhaRelatorio
        :excel="() => relatorioExcelEstagios()"
        :pdf="() => relatorioPDFEstagios()"
        :cancelar="() => (relatorioEstagio = false)"
        :description="`Gerar relatório das Seguradoras em que formato?`"
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
import EscolhaRelatorio from "~~/src/components/common/escolha-relatorios.vue";
let relatorioSeguradora = ref(false);
let relatorioCertificado = ref(false);
let relatorioEstagio = ref(false);
const toast = useToast();
const relatorioExcelSeguradora = () => {
  relatorioSeguradora.value = false;
  return toast.add({
    severity: "success",
    summary: "Relatório de Seguradoras Gerado em Excel",
    life: 3000,
  });
};
const relatorioPDFSeguradora = () => {
  relatorioSeguradora.value = false;
  return toast.add({
    severity: "success",
    summary: "Relatório de Seguradoras Gerado em PDF",
    life: 3000,
  });
};
const relatorioExcelCertificados = () => {
  relatorioCertificado.value = false;
  return toast.add({
    severity: "success",
    summary: "Relatório de Certificados Gerado em Excel",
    life: 3000,
  });
};
const relatorioPDFCertificados = () => {
  relatorioCertificado.value = false;
  return toast.add({
    severity: "success",
    summary: "Relatório de Certificados Gerado em PDF",
    life: 3000,
  });
};
const relatorioExcelEstagios = () => {
  relatorioEstagio.value = false;
  return toast.add({
    severity: "success",
    summary: "Relatório de Estágios Gerado em Excel",
    life: 3000,
  });
};
const relatorioPDFEstagios = () => {
  relatorioEstagio.value = false;
  return toast.add({
    severity: "success",
    summary: "Relatório de Estágios Gerado em PDF",
    life: 3000,
  });
};
</script>
