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
      cienciaCoordenacao: false,
    });

    const { data: processes } = useAsyncData(
      "rescisaoCoord",
      () =>
        $fetch(`/termoDeRescisao`, {
          params: {
            page: page.value,
            grrAluno: filters.grrAluno || undefined,
            cienciaCoordenacao: filters.cienciaCoordenacao,
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
        Coordenação
        <h6>Análise e Desenvolvimento de Sistemas</h6>
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
              <h4 class="font-bold">Termos de Rescisão</h4>
            </span>
            <div class="flex gap-2">
              <Button
                label="Pendentes de parecer"
                :class="`${
                  !filters.cienciaCoordenacao
                    ? 'p-button-primary'
                    : 'p-button-secondary opacity-50'
                }`"
                @click="
                  filters.cienciaCoordenacao = !filters.cienciaCoordenacao
                "
              />
              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  placeholder="Matrícula (GRRXXXXXXXX)"
                  v-model="filters.grrAluno"
                />
              </span>
            </div>
          </div>
        </template>
        <Column field="process" header="Número do Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>

        <Column field="estagio" header="Número do Estágio">
          <template #body="{ data }"> #{{ data?.estagio?.id }} </template>
        </Column>

        <Column field="dataInicio" header="Data de Início do Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.estagio?.dataInicio) }}
          </template>
        </Column>
        <Column field="process_type" header="Data de Término do Estágio">
          <template #body="{ data }">
            {{ parseDate(data?.dataTermino) }}
          </template>
        </Column>

        <Column field="dataInicio" header="Estágio UFPR">
          <template #body="{ data }">
            {{ data?.estagio?.estagioUfpr ? "Sim" : "Não" }}
          </template>
        </Column>

        <Column field="button" header="Ver">
          <template #body="{ data }">
            <NuxtLink :to="`/coord/termo-rescisao/${data.id}`">
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
