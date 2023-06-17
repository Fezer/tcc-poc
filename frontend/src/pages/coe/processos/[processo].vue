<script lang="ts">
import { defineComponent } from "vue";
import { BaseTermo } from "~~/src/types/Termos";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";

export default defineComponent({
  setup() {
    const config = useRuntimeConfig();
    const route = useRoute();

    const { processo } = route.params;

    const page = ref(0);

    const { data: processes } = useFetch<{
      content: BaseTermo[];
      totalElements: number;
      totalPages: number;
    }>(`${config.BACKEND_URL}/coe/${processo}/pendenteAprovacaoCoe`, {
      params: {
        page,
        size: 10,
      },
    });

    return {
      processes,
      parseTipoTermo,
      page,
    };
  },
});
</script>

<template>
  <div>
    <div>
      <h1>
        COE
        <h6>Comissão Orientadora de Estágio</h6>
      </h1>
    </div>
    <div>
      <DataTable
        :value="processes?.content"
        rowHover
        stripedRows
        :show-gridlines="true"
      >
        <template #header>
          <div class="flex items-center justify-content-between">
            <span class="p-input-icon-left">
              <h4 class="font-bold">Processos pendentes de parecer</h4>
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
        <Column field="process_type" header="Tipo de Processo">
          <template #body="{ data }">
            {{ parseTipoTermo(data.tipoTermoDeEstagio) }}
          </template>
        </Column>
        <Column field="student_name" header="Nome do Aluno">
          <template #body="{ data }">
            {{ data?.aluno }}
          </template>
        </Column>
        <Column field="grr" header="GRR">
          <template #body="{ data }">
            {{ data.grrAluno }}
          </template>
        </Column>
        <Column field="contratante" header="Contratante">
          <template #body="{ data }">
            {{ data?.contratante?.nome }}
          </template>
        </Column>
        <Column field="process_type" header="Data de Criação">
          <template #body="{ data }">
            {{ parseDate(data?.dataCriacao) }}
          </template>
        </Column>
        <Column
          field="action"
          header="Ação necessária"
          bodyStyle="color:orange;"
        >
          <template #body="{ data }">
            <Tag value="Parecer" severity="warning" class="p-2 font-md" />
          </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/coe/termo/${data.id}`">
              <Button
                class="p-button-icon-only p-button-outlined"
                icon="pi pi-eye"
                type="primary"
              ></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
      <Paginator
        :rows="10"
        :totalRecords="processes?.totalElements"
        @page="page = $event.page"
      ></Paginator>
    </div>
  </div>
</template>
