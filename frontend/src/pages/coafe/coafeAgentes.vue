<template>
  <div>
    <div class="flex justify-content-between">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
      <span class="p-input-icon-left">
        <NuxtLink to="/coafe/agentes-integracao/novo">
          <Button label="Cadastrar"></Button>
        </NuxtLink>
      </span>
    </div>
    <div>
      <DataTable :value="agentes" rowHover stripedRows>
        <template #header>
          <div class="flex justify-content-between">
            <span class="p-input-icon-left">
              <h4><b>Agentes de Integração</b></h4>
            </span>
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText placeholder="Keyword Search" />
            </span>
          </div>
        </template>
        <template #empty> No customers found. </template>
        <template #loading> Loading customers data. Please wait. </template>
        <Column field="nome" header="Nome">
          <template #body="{ data }">
            {{ data.nome }}
          </template>
        </Column>
        <Column field="numeroConvenio" header="Número de Convênios">
          <template #body="{ data }">
            {{ data.convenio }}
          </template>
        </Column>
        <Column field="button">
          <template #body>
            <Button label="Ver"></Button>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>

<script>
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import Button from "primevue/button";
</script>
<script scoped></script>
<script setup>
const { data: agentes } = useFetch(`http://localhost:5000/agente-integrador`);
console.log(agentes.value);
</script>
