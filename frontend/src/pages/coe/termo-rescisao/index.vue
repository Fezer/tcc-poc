<script>
import { defineComponent } from "vue";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";

export default defineComponent({
  setup() {
    const route = useRoute();

    const { processo } = route.params;
    const page = ref(0);
    const filters = reactive({
      grrAluno: "",
      // default => pendentes de parecer
      cienciaCOE: false,
    });

    const { data: processes } = useAsyncData(
      "rescisaoCOE",
      () =>
        $fetch(`/termoDeRescisao`, {
          params: {
            page: page.value,
            grrAluno: filters.grrAluno || undefined,
            cienciaCOE: filters.cienciaCOE,
          },
        }),
      {
        watch: [page, filters],
      }
    );

    return {
      processes,
      parseTipoTermo,
      page,
      filters,
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
        :paginator="true"
        :rows="10"
        @page="page = $event.page"
        :totalRecords="processes?.totalElements"
        rowHover
        stripedRows
        :show-gridlines="true"
      >
        <template #header>
          <div class="flex items-center justify-content-between">
            <span class="p-input-icon-left">
              <h4 class="font-bold">Termos de Rescisão</h4>
            </span>
            <div class="flex gap-2">
              <Button
                label="Pendentes de parecer"
                :class="`${
                  !filters.cienciaCOE
                    ? 'p-button-primary'
                    : 'p-button-secondary opacity-50'
                }`"
                @click="filters.cienciaCOE = !filters.cienciaCOE"
              />
              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  placeholder="Buscar por matrícula (GRRXXXXXXXX)"
                  v-model="filters.grrAluno"
                />
              </span>
            </div>
          </div>
        </template>
        <Column field="process" header="Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>

        <Column field="estagio" header="Estágio">
          <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
        </Column>
        <Column field="process_type" header="Data Término Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.dataTermino) }}
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
            <NuxtLink :to="`/coe/termo-rescisao/${data.id}`">
              <Button label="Ver contato"></Button>
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
