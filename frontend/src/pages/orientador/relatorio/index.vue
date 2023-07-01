<script lang="ts">
export default {
  setup() {
    const { auth } = useAuth();

    const { data: processes } = useFetch(
      `/orientador/${auth?.value?.identifier}/relatorioDeEstagio/pendenteCiencia`
    );

    const parseTipoRelatorio = (
      tipo: "RelatorioParcial" | "RelatorioFinal"
    ) => {
      if (tipo === "RelatorioParcial") {
        return "Relatório Parcial";
      } else {
        return "Relatório Final";
      }
    };

    return {
      processes,
      parseTipoRelatorio,
    };
  },
};
</script>

<template>
  <div>
    <div>
      <h1>
        Prof. Professor Orientador
        <h6>Orientador</h6>
      </h1>
    </div>
    <div>
      <DataTable :value="processes" rowHover stripedRows :show-gridlines="true">
        <template #header>
          <div class="flex items-center justify-content-between">
            <span class="p-input-icon-left">
              <h4 class="font-bold">
                Relatórios de Estágios Pendentes de Ciência
              </h4>
            </span>
          </div>
        </template>
        <Column field="process" header="Número do Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>
        <Column field="process" header="Número do Estágio">
          <template #body="{ data }"> #{{ data.estagio?.id }} </template>
        </Column>
        <Column field="process_type" header="Tipo de Processo">
          <template #body="{ data }">
            {{ parseTipoRelatorio(data.tipoRelatorio) }}
          </template>
        </Column>
        <Column field="action" header="Etapa" bodyStyle="color:orange;">
          <template #body="{ data }">{{ data?.etapaFluxo }}</template>
        </Column>
        <Column field="button" header="Ver">
          <template #body="{ data }">
            <NuxtLink :to="`/orientador/relatorio/${data.id}`">
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
  </div>
</template>
