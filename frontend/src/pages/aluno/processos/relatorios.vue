<script setup lang="ts">
const { auth } = useAuth();

const grr = auth?.id || "";
const { data: relatorios } = useFetch(`/aluno/${grr}/relatorioDeEstagio`);

const parseTipoRelatorio = (tipo: "RelatorioParcial" | "RelatorioFinal") => {
  if (tipo === "RelatorioParcial") return "Relatório Parcial";
  if (tipo === "RelatorioFinal") return "Relatório Final";
  return "Relatório";
};
</script>

<template>
  <div>
    <h1>Relatórios de Estágio</h1>

    <DataTable :value="relatorios" rowHover stripedRows :show-gridlines="true">
      <Column field="id" header="Processo">
        <template #body="{ data }"> #{{ data.id }} </template>
      </Column>
      <Column field="tipo" header="Tipo">
        <template #body="{ data }">
          {{ parseTipoRelatorio(data.tipoRelatorio) }}
        </template>
      </Column>
      <Column field="estagio" header="Estágio">
        <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
      </Column>
      <Column
        field="etapa"
        header="Etapa"
        style="min-width: 12rem; font-weight: bold"
      >
        <template #body="{ data }">
          {{ data.etapaFluxo }}
        </template>
      </Column>
      <Column field="acoes" header="Ações">
        <template #body="{ data }">
          <NuxtLink :to="`/aluno/relatorio/${data.id}`">
            <Button
              class="p-button-icon-only p-button-outlined"
              icon="pi pi-eye"
              type="primary"
            ></Button>
          </NuxtLink>
        </template>
      </Column>
    </DataTable>
  </div>
</template>
