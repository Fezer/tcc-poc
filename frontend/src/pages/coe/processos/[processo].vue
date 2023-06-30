<script lang="ts">
import { defineComponent } from "vue";
import { BaseTermo } from "~~/src/types/Termos";
import parseObrigatoriedadeEstagio from "~~/src/utils/parseObrigatoriedadeEstagio";

import { PaginatedTermo } from "../../../types/Termos";

export default defineComponent({
  setup() {
    const config = useRuntimeConfig();
    const route = useRoute();
    const { statusOptions, etapaOptions, tipoOptions } = useTermoFilters();

    const { processo } = route.params;

    const filters = reactive({
      status: "EmAprovacao",
      etapa: "COE",
      grr: "",
    });

    const page = ref(0);

    const {
      data: processes,
      refresh,
      execute,
    } = useAsyncData(
      "termosCOE",
      () =>
        $fetch("/termo", {
          params: {
            page: page.value,
            status: filters.status,
            tipoTermo:
              processo === "termo" ? "TermoDeCompromisso" : "TermoAditivo",
            etapa: filters.etapa,
            tipoEstagio: "NaoObrigatorio",
            grr: filters.grr || undefined,
          },
        }),
      {
        watch: [filters, page],
      }
    );

    const handleSearch = () => {
      page.value = 0;
    };

    return {
      processo,
      processes,
      page,
      statusOptions,
      etapaOptions,
      tipoOptions,
      filters,
      handleSearch,
      parseObrigatoriedadeEstagio,
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
            <span class="">
              <h4 class="font-bold">
                {{
                  processo === "termo"
                    ? "Termo de Compromisso"
                    : "Termo Aditivo"
                }}
              </h4>
            </span>
            <div class="flex gap-2">
              <Dropdown
                :options="statusOptions"
                v-model="filters.status"
                optionLabel="label"
                optionValue="value"
                placeholder="Status"
                @change="() => handleSearch()"
              >
              </Dropdown>
              <Dropdown
                :options="etapaOptions"
                v-model="filters.etapa"
                optionLabel="label"
                optionValue="value"
                placeholder="Etapa do termo"
                @change="() => handleSearch()"
              >
              </Dropdown>
              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  placeholder="Matrícula (GRRXXXXXXXX)"
                  v-model="filters.grr"
                />
              </span>
            </div>
          </div>
        </template>
        <Column field="process" header="Número do Processo">
          <template #body="{ data }"> #{{ data.id }} </template>
        </Column>

        <Column field="tipo" header="Tipo do Estágio">
          <template #body="{ data }">
            {{ parseObrigatoriedadeEstagio(data?.estagio?.tipoEstagio) }}
          </template>
        </Column>
        <Column field="ufpr" header="Estágio UFPR">
          <template #body="{ data }">
            {{ data.estagio?.estagioUfpr ? "Sim" : "Não" }}
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
        <Column field="action" header="Status" bodyStyle="color:orange;">
          <template #body="{ data }">
            <StatusTag :status="data?.statusTermo" />
          </template>
        </Column>
        <Column field="action" header="Etapa" bodyStyle="color:orange;">
          <template #body="{ data }">
            {{ data?.etapaFluxo }}
          </template>
        </Column>
        <Column field="button" header="Ver">
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
