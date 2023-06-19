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
              <h4 class="font-bold">Relatórios de Estágio pendentes ciência</h4>
            </span>
            <span class="p-input-icon-left">
              <i class="pi pi-search" />
              <InputText placeholder="Keyword Search" />
            </span>
          </div>
        </template>
        <Column field="process" header="Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>
        <Column field="process" header="Estágio">
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
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/orientador/relatorio/${data.id}`">
              <Button label="Ver contato"></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
