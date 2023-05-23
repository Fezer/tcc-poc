<template>
  <div>
    <div class="flex justify-content-between">
      <h6>
        Agente
        <h3>{{ agente.nome }}</h3>
        <h5>Dados do Agente de Integração</h5>
      </h6>
    </div>
    <div class="card flex-column">
      <div class="flex flex-row justify-content-between gap-10">
        <div class="flex align-items-left flex-column pb-4">
          <b>Telefone</b>
          <p>{{ agente.telefone }}</p>
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>CNPJ</b>
          <p>{{ agente.cnpj }}</p>
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>Numero de Convênios</b>
          <p>{{ agente.convenio.length }}</p>
        </div>
      </div>
    </div>
    <DataTable class="flex-column" :value="agente.convenio" rowHover>
      <template #header>
        <div>
          <span>
            <h4><b>Cônvênios</b></h4>
          </span>
        </div>
      </template>
      <template #empty>
        o Agente {{ agente.nome }} não possui nenhum convênio
      </template>
      <Column field="numero" header="Número">
        <template #body="{ data }">
          {{ data.numero }}
        </template>
      </Column>
      <Column field="descricao" header="Descrição">
        <template #body="{ data }">
          {{ data.descricao }}
        </template>
      </Column>
      <Column field="dataInicio" header="Data de Inicio">
        <template #body="{ data }">
          {{ data.dataInicio }}
        </template>
      </Column>
      <Column field="dataFim" header="Data de Fim">
        <template #body="{ data }">
          {{ data.dataFim }}
        </template>
      </Column>
    </DataTable>
    <div class="flex flex-row justify-content-end flex-wrap pb-2 gap-2">
      <NuxtLink to="/coafe/coafeAgentes">
        <Button
          label="Voltar"
          class="p-button-secondary"
          icon="pi pi-arrow-left"
        />
      </NuxtLink>
      <Button label="Editar" severity="info" />
    </div>
  </div>
</template>
<script setup>
import Button from "primevue/button";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import { useRoute } from "vue-router";
const route = useRoute();
const id = route.query.id;
const { data: agente } = await useFetch(
  `http://localhost:5000/agente-integrador/${id}`
);
</script>
