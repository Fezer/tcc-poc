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
      <DataTable :value="Seguradoras" rowHover stripedRows>
        <template #header>
          <div class="flex justify-content-between">
            <span class="p-input-icon-left">
              <h4><b>Seguradoras</b></h4>
            </span>
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText placeholder="Keyword Search" />
            </span>
          </div>
        </template>
        <template #empty> No customers found. </template>
        <template #loading> Loading customers data. Please wait. </template>
        <Column field="seguradora" header="Nome da Seguradora">
          <template #body="{ data }">
            {{ data.seguradora }}
          </template>
        </Column>
        <Column field="numeroApolice" header="Número da Apólice">
          <template #body="{ data }">
            {{ data.numeroApolice }}
          </template>
        </Column>
        <Column field="cnpj" header="CNPJ">
          <template #body="{ data }">
            {{ data.cnpj }}
          </template>
        </Column>
        <Column field="situação" header="Ativo">
          <template #body="{ data }">
            {{ data.situação }}
          </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`seguradora/seguradoraVisualizar/`">
              <NuxtLink
                :to="`seguradora/seguradoraVisualizar/${data.id}`"
              ></NuxtLink>
              <Button label="Ver" />
            </NuxtLink>
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
export default {
  data() {
    return {
      Seguradoras: [
        {
          id: 1,
          seguradora: "Seguradora de Seguros",
          numeroApolice: "12421421214",
          cnpj: "124412142",
          situação: "ATIVO",
        },
        {
          id: 2,
          seguradora: "Seguros Seguros",
          numeroApolice: "124900824",
          cnpj: "89024908",
          situação: "INATIVO",
        },
      ],
    };
  },
};
</script>
