<template>
  <div>
    <div class="flex justify-content-between items-center">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
      <span class="p-input-icon-left">
        <NuxtLink to="/coafe/agentes-integracao/novo">
          <Button
            label="Cadastrar"
            class="p-button-success"
            icon="pi pi-plus"
          ></Button>
        </NuxtLink>
      </span>
    </div>
    <div>
      <DataTable
        v-model:filters="filtros"
        :value="agentes"
        paginator
        :rows="5"
        :globalFilterFields="['nome']"
        :rowsPerPageOptions="[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]"
        paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
        rowHover
        stripedRows
        :show-gridlines="true"
      >
        <template #header>
          <div class="flex justify-content-between">
            <span class="p-input-icon-left">
              <h4><b>Agentes de Integração</b></h4>
            </span>
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText
                v-model="filtros['nome'].value"
                placeholder="Pesquisar Agentes"
              />
            </span>
          </div>
        </template>
        <template #empty> Nenhum Agente Integrador encontrado. </template>
        <template #loading> Carregando Agentes, por favor aguarde. </template>
        <Column field="nome" header="Nome">
          <template #body="{ data }">
            {{ data.nome }}
          </template>
        </Column>
        <Column field="convenio" header="Número de Convênios">
          <template #body="{ data }">
            {{ data.convenio.length }}
          </template>
        </Column>
        <Column field="cnpj" header="CNPJ">
          <template #body="{ data }">
            {{ data.cnpj }}
          </template>
        </Column>
        <Column field="button" header="Ver">
          <template #body="{ data }">
            <NuxtLink
              :to="`/coafe/agentes-integracao/agenteVisualizar?id=${data.id}`"
            >
              <Button label="Ver" icon="pi pi-search" />
            </NuxtLink>
          </template>
        </Column>
        <Column field="relatorio" header="Relatório">
          <template #body="{ data }">
            <Button
              @click="escolhaDeRelatorio = data.id"
              :label="'Relatório'"
              icon="pi pi-file"
              class="p-button-success"
            />
            <div v-if="escolhaDeRelatorio == data.id">
              <EscolhaRelatorio
                :excel="() => relatorioExcel(data.id)"
                :pdf="() => relatorioPDF(data.id)"
                :cancelar="() => (escolhaDeRelatorio = 0)"
                :description="`Gerar relatório do Agente ${data.nome} em que formato?`"
              >
              </EscolhaRelatorio>
            </div>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
<script lang="ts">
export default defineComponent({
  components: { EscolhaRelatorio },
});
</script>
<script setup lang="ts">
import Column from "primevue/column";
import EscolhaRelatorio from "~~/src/components/common/escolha-relatorios.vue";
import { defineComponent } from "vue";
import { useToast } from "primevue/usetoast";
import DataTable from "primevue/datatable";
import Button from "primevue/button";
import { FilterMatchMode } from "primevue/api";
import { ref } from "vue";
import AgenteService from "~~/services/AgenteService";
const { data: agentes } = useFetch(`http://localhost:5000/agente-integrador/`);
const filtros = ref({
  nome: { value: null, matchMode: FilterMatchMode.CONTAINS },
});
let escolhaDeRelatorio = ref(0);
const toast = useToast();
const agenteService = new AgenteService();
const relatorioExcel = async (id: number) => {
  escolhaDeRelatorio.value = 0;
  try {
    const file = await agenteService.baixarRelatorioEstagioExcelEspecifico(id);
    var blob = new Blob([file], {
      type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
    });

    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = `relatorio-de-Agente-de-Integracao-excel.xlsx`;
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
const relatorioPDF = async (id: number) => {
  escolhaDeRelatorio.value = 0;
  try {
    const file = await agenteService.baixarRelatorioEstagioPdfEspecifico(id);
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
