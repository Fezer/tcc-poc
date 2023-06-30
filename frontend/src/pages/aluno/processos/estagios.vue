<script setup>
const { auth } = useAuth();

const grr = auth?.value?.identifier || "";

const { data: estagios } = await useFetch(`/aluno/${grr}/estagio/`);
</script>

<template>
  <div>
    <h1>Estágios</h1>

    <DataTable :value="estagios" rowHover stripedRows :show-gridlines="true">
      <Column field="id" header="Número do Processo">
        <template #body="{ data }"> #{{ data.id }} </template>
      </Column>
      <Column field="tipo" header="Tipo" style="min-width: 12rem">
        <template #body="{ data }"> Estágio </template>
      </Column>
      <Column field="contratante" header="Contratante" style="min-width: 12rem">
        <template #body="{ data }">
          {{ data?.contratante?.nome }} -
          {{ data?.contratante?.cnpj || data?.contratante?.cpf }}
        </template>
      </Column>
      <Column field="data" header="Data de Criação do Processo">
        <template #body="{ data }">
          {{ parseDate(data.dataCriacao) }}
        </template>
      </Column>
      <Column
        field="etapa"
        header="Status"
        style="min-width: 12rem; font-weight: bold"
      >
        <template #body="{ data }">
          <StatusTag :status="data.statusEstagio" />
        </template>
      </Column>
      <Column field="acoes" header="Ações" style="min-width: 12rem">
        <template #body="{ data }">
          <NuxtLink :to="`/aluno/estagio/${data.id}`">
            <Button
              class="p-button-icon-only p-button-outlined"
              icon="pi pi-eye"
              type="primary"
            />
          </NuxtLink>
        </template>
      </Column>
    </DataTable>
  </div>
</template>
