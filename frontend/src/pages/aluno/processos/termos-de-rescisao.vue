<script setup>
const { auth } = useAuth();

const grr = auth?.value?.identifier || "";

const { data: termos } = await useFetch(`/aluno/${grr}/termoDeRescisao/`);
</script>

<template>
  <div>
    <h1>Termos de Rescisão</h1>

    <DataTable :value="termos" rowHover stripedRows :show-gridlines="true">
      <Column field="termo" header="Número do Termo">
        <template #body="{ data }"> #{{ data.id }} </template>
      </Column>
      <Column field="tipo" header="Tipo" style="min-width: 12rem">
        <template #body="{ data }"> Termo de Rescisão </template>
      </Column>
      <Column field="estagio" header="Número do Estágio">
        <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
      </Column>
      <Column
        field="dataTermino"
        header="Data de Término"
        style="min-width: 12rem"
      >
        <template #body="{ data }">
          {{ parseDate(data?.dataTermino) }}
        </template>
      </Column>
      <Column
        field="etapa"
        header="Status"
        style="min-width: 12rem; font-weight: bold"
      >
        <template #body="{ data }">
          {{ data?.etapaFluxo ? "Ciência" : "Aprovado" }}
        </template>
      </Column>
      <Column field="acoes" header="Ações" style="min-width: 12rem">
        <template #body="{ data }">
          <NuxtLink :to="`/aluno/termo-rescisao/${data.id}`">
            <Button
              class="p-button-icon-only p-button-outlined"
              icon="pi pi-eye"
              type="primary"
            >
            </Button>
          </NuxtLink>
        </template>
      </Column>
    </DataTable>
  </div>
</template>
