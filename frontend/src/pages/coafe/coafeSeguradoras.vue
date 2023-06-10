<template>
  <div>
    <div class="flex justify-content-between items-center">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
      <NuxtLink to="/coafe/seguradora/novo">
        <Button label="Cadastrar"></Button>
      </NuxtLink>
    </div>
    <div>
      <DataTable
        v-model:filters="filtros"
        :value="seguradoras"
        rowHover
        stripedRows
        paginator
        :rows="5"
        :globalFilterFields="['nome']"
        :rowsPerPageOptions="[5, 10, 15, 20, 25, 30, 35, 40, 45, 50]"
        paginatorTemplate="RowsPerPageDropdown FirstPageLink PrevPageLink CurrentPageReport NextPageLink LastPageLink"
      >
        <template #header>
          <div class="flex justify-content-between">
            <span class="p-input-icon-left">
              <h4><b>Seguradoras</b></h4>
            </span>
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText
                v-model="filtros['nome'].value"
                placeholder="Pesquisar Seguradoras"
              />
            </span>
          </div>
        </template>
        <template #empty> Nenhuma seguradora encontrada. </template>
        <template #loading>
          Carregando seguradoras, por favor aguarde.
        </template>
        <Column field="nome" header="Nome da Seguradora" filterField="name">
          <template #body="{ data }">
            {{ data.nome }}
          </template>
        </Column>
        <Column field="apolice" header="Quantidade de Apólices">
          <template #body="{ data }">
            {{ data.apolice.length }}
          </template>
        </Column>
        <Column field="seguradoraUfpr" header="Seguradora UFPR">
          <template #body="{ data }">
            <Tag
              style="font-size: medium"
              :value="seguradoraService.pegarAtivaValue(data.seguradoraUfpr)"
              :severity="
                seguradoraService.pegarAtivaSeverity(data.seguradoraUfpr)
              "
            />
          </template>
        </Column>
        <Column field="ativa" header="Ativo">
          <template #body="{ data }">
            <Tag
              style="font-size: medium"
              :value="seguradoraService.pegarAtivaValue(data.ativa)"
              :severity="seguradoraService.pegarAtivaSeverity(data.ativa)"
            />
          </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink
              :to="`/coafe/seguradora/seguradoraVisualizar?id=${data.id}`"
            >
              <Button label="Ver" />
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import Button from "primevue/button";
import SeguradoraService from "~~/services/SeguradoraService";
import { FilterMatchMode } from "primevue/api";
const { data: seguradoras } = useFetch(`http://localhost:5000/seguradora/`);
const seguradoraService = new SeguradoraService();
const filtros = ref({
  nome: { value: null, matchMode: FilterMatchMode.CONTAINS },
});
</script>
