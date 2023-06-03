<script setup>
const grr = "GRR20200141";

const { data: termos } = await useFetch(
  `http://localhost:5000/aluno/${grr}/termoDeRescisao/`
);

// const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);
</script>

<template>
  <div>
    <h1>Termos de Recisão</h1>

    <DataTable :value="termos" rowHover stripedRows :show-gridlines="true">
      <Column field="termo" header="Termo">
        <template #body="{ data }"> #{{ data.id }} </template>
      </Column>
      <Column field="tipo" header="Tipo" style="min-width: 12rem">
        <template #body="{ data }">
          <!-- {{ data.tipoTermoDeEstagio }} -->
          Termo de Rescisão
        </template>
      </Column>
      <Column field="estagio" header="Estágio">
        <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
      </Column>
      <Column
        field="dataTermino"
        header="Data Término"
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
          {{ data.etapaFluxo }}
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
