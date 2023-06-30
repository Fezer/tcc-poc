<script setup>
const { auth } = useAuth();

const grr = auth?.value?.identifier || "";
const { data: termos } = useFetch(`/aluno/${grr}/termoDeCompromisso/`);
</script>

<template>
  <div>
    <h1>Termos de Compromisso</h1>

    <DataTable :value="termos" rowHover stripedRows :show-gridlines="true">
      <Column field="termo" header="Número do Termo">
        <template #body="{ data }">
          {{ data.id }}
        </template>
      </Column>
      <Column field="tipo" header="Tipo" style="min-width: 12rem">
        <template #body="{ data }"> Termo de Compromisso </template>
      </Column>
      <Column field="contratante" header="Contratante" style="min-width: 12rem">
        <template #body="{ data }">
          {{ data?.contratante?.nome }} -
          {{ data?.contratante?.cnpj || data?.contratante?.cpf }}
        </template>
      </Column>
      <Column
        field="etapa"
        header="Status"
        style="min-width: 12rem; font-weight: bold"
      >
        <template #body="{ data }">
          <StatusTag :status="data.statusTermo" />
        </template>
      </Column>
      <Column field="acoes" header="Ações" style="min-width: 12rem">
        <template #body="{ data }">
          <NuxtLink :to="`/aluno/termo/${data.id}`">
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
