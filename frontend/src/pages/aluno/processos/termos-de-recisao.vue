<script setup>
const { auth } = useAuth();

const grr = auth?.value?.identifier || "";

const { data: termos } = await useFetch(`/aluno/${grr}/termosAditivos/`);
</script>

<template>
  <div>
    <h1>Termos de Rescisão</h1>

    <DataTable :value="termos">
      <Column field="termo" header="Termo">
        <template #body="{ data }">
          {{ data.id }}
        </template>
      </Column>
      <Column field="tipo" header="Tipo" style="min-width: 12rem">
        <template #body="{ data }"> Termo de Rescisão </template>
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
          {{ data.statusTermo }}
        </template>
      </Column>
      <Column field="acoes" header="Ações" style="min-width: 12rem">
        <template #body="{ data }">
          <NuxtLink :to="`/aluno/termo/${data.id}`">
            <Button type="primary"> Ver </Button>
          </NuxtLink>
        </template>
      </Column>
    </DataTable>
  </div>
</template>
