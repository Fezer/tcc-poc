<script lang="ts">
import { defineComponent } from "vue";
import { ref } from "vue";
import { FilterMatchMode } from "primevue/api";
import ContratanteService from "~~/services/ContratanteService";
import { useToast } from "primevue/usetoast";
import EscolhaRelatorio from "~~/src/components/common/escolha-relatorios.vue";
export default defineComponent({
  components: { EscolhaRelatorio },
  async setup() {
    let escolhaDeRelatorio = ref(0);
    const relatorioExcel = (id: number) => {
      escolhaDeRelatorio.value = 0;
      return toast.add({
        severity: "success",
        summary: "Excel escolhido com id: " + id,
        life: 3000,
      });
    };
    const relatorioPDF = (id: number) => {
      escolhaDeRelatorio.value = 0;
      return toast.add({
        severity: "error",
        summary: "PDF escolhido com id: " + id,
        life: 3000,
      });
    };
    const toast = useToast();
    const contratanteService = new ContratanteService();
    const filtros = ref({
      nome: { value: null, matchMode: FilterMatchMode.CONTAINS },
    });
    const { data: contratantes } = useFetch("/contratante/");

    return {
      contratantes,
      filtros,
      toast,
      contratanteService,
      relatorioExcel,
      relatorioPDF,
      escolhaDeRelatorio,
    };
  },
});
</script>
<Toast />
<template>
  <div>
    <div class="flex justify-content-between">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
      <!-- <NuxtLink to="/contratantes/novo">
        <Button label="Cadastrar"></Button>
      </NuxtLink> -->
    </div>
    <div>
      <DataTable
        v-model:filters="filtros"
        :value="contratantes"
        paginator
        :rows="5"
        :globalFilterFields="['Empresas']"
        :rowsPerPageOptions="[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]"
        paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
        rowHover
        stripedRows
        :show-gridlines="true"
      >
        <template #header>
          <div class="flex justify-content-between">
            <span class="p-input-icon-left">
              <h4><b>Empresas</b></h4>
            </span>
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText
                v-model="filtros['nome'].value"
                placeholder="Pesquisar Empresas"
              />
            </span>
          </div>
        </template>
        <template #empty> Nenhuma Empresa encontrada. </template>
        <template #loading> Carregando dados. Por favor espere. </template>
        <Column field="Id" header="id">
          <template #body="{ data }">
            {{ data?.id }}
          </template>
        </Column>
        <Column field="Empresas" header="Nome da Empresa">
          <template #body="{ data }">
            {{ data?.nome }}
          </template>
        </Column>
        <Column field="cnpj" header="CNPJ">
          <template #body="{ data }">
            {{ data?.cnpj }}
          </template>
        </Column>
        <Column field="ver" header="Ver">
          <template #body="{ data }">
            <NuxtLink :to="`/coafe/empresa/${data.id}`">
              <Button
                :label="'Ver'"
                class="p-button-primary"
                icon="pi pi-search"
              />
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
              >
              </EscolhaRelatorio>
            </div>
          </template>
        </Column>
        <!-- <Column field="situação" header="Ativo">
          <template #body="{ c }">
            {{ c.situação }}
          </template>
        </Column> -->
      </DataTable>
    </div>
  </div>
</template>
