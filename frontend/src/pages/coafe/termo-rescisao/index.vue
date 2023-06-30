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
      cienciaCOAFE: false,
    });

    const { data: processes } = useAsyncData(
      "rescisaoCOAFE",
      () =>
        $fetch(`/termoDeRescisao`, {
          params: {
            page: page.value,
            grrAluno: filters.grrAluno || undefined,
            cienciaCOAFE: filters.cienciaCOAFE,
            cienciaCOE: filters?.cienciaCOAFE === false ? true : undefined,
            cienciaOrientador:
              filters?.cienciaCOAFE === false ? true : undefined,
            cienciaCoordenacao:
              filters?.cienciaCOAFE === false ? true : undefined,
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
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
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
                  !filters.cienciaCOAFE
                    ? 'p-button-primary'
                    : 'p-button-secondary opacity-50'
                }`"
                @click="filters.cienciaCOAFE = !filters.cienciaCOAFE"
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
        <Column field="process" header="Número do Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>

        <Column field="estagio" header="Estágio">
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
            <NuxtLink :to="`/coafe/termo-rescisao/${data.id}`">
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
  </div>
</template>
