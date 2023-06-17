<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  async setup() {
    const config = useRuntimeConfig();
    const route = useRoute();

    const { processo } = route.params;

    const page = ref(0);

    const { data: processes } = useFetch<PaginatedTermo>(
      `${config.BACKEND_URL}/coafe/${processo}/pendenteAprovacaoCoafe`,
      {
        params: {
          page,
          size: 10,
        },
      }
    );

    return {
      processes,
      page,
    };
  },
});
</script>

<template>
  <div>
    <div>
      <h1>
        COAFE
        <h6>COAFE</h6>
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
        <Column field="student_name" header="Nome do Aluno">
          <template #body="{ data }">
            {{ data?.aluno?.nome }}
          </template>
        </Column>
        <Column field="curse" header="Curso">
          <template #body="{ data }">
            {{ data.curse }}
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
          <template #body="{ data }"> Parecer </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/coafe/termo/${data.id}`">
              <Button label="Ver contato"></Button>
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
