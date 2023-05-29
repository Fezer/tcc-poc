<script setup>
const grr = "GRR20200141";

const { data: estagios } = await useFetch(
  `http://localhost:5000/aluno/${grr}/estagio/`
);

console.log(estagios);

// const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);
</script>

<template>
  <div>
    <h1>Estágios</h1>

    <DataTable :value="estagios">
      <Column field="id" header="Processo">
        <template #body="{ data }"> #{{ data.id }} </template>
      </Column>
      <Column field="tipo" header="Tipo" style="min-width: 12rem">
        <template #body="{ data }">
          <!-- {{ data.tipoTermoDeEstagio }} -->
          Estágio
        </template>
      </Column>
      <Column field="contratante" header="Contratante" style="min-width: 12rem">
        <template #body="{ data }">
          {{ data?.contratante?.nome }} -
          {{ data?.contratante?.cnpj || data?.contratante?.cpf }}
        </template>
      </Column>
      <Column field="data" header="Data criação do processo">
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
            <Button type="primary"> Ver </Button>
          </NuxtLink>
        </template>
      </Column>
    </DataTable>
  </div>
</template>
