<script lang="ts">
import { defineComponent } from "vue";
import { Estagio } from "~~/src/types/NovoEstagio";
import parseObrigatoriedadeEstagio from "~~/src/utils/parseObrigatoriedadeEstagio";
import { PaginatedEstagio } from "../../../types/Termos";

export default defineComponent({
  setup() {
    const route = useRoute();
    const { statusOptions, tipoOptions } = useTermoFilters();

    const filters = reactive({
      statusEstagio: "",
      tipoEstagio: "",
      grr: "",
      nomeEmpresa: "",
    });
    const page = ref(0);

    const { data: estagios } = useAsyncData<PaginatedEstagio>(
      "estagiosCOE",
      () =>
        $fetch("/estagio/", {
          params: {
            statusEstagio: filters.statusEstagio || undefined,
            tipoEstagio: filters.tipoEstagio || undefined,
            grr: filters.grr || undefined,
            nomeEmpresa: filters.nomeEmpresa || undefined,
            page: page.value,
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
      estagios,
      filters,
      page,
      statusOptions,
      tipoOptions,
      parseObrigatoriedadeEstagio,
      handleSearch,
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
        :value="estagios?.content"
        rowHover
        stripedRows
        :show-gridlines="true"
      >
        <template #header>
          <div class="flex items-center justify-content-between">
            <span class="">
              <h4 class="font-bold">Estágios</h4>
            </span>
            <div class="flex gap-2">
              <Dropdown
                :options="statusOptions"
                v-model="filters.statusEstagio"
                optionLabel="label"
                optionValue="value"
                placeholder="Status"
                @change="() => handleSearch()"
              >
              </Dropdown>
              <Dropdown
                :options="tipoOptions"
                v-model="filters.tipoEstagio"
                optionLabel="label"
                optionValue="value"
                placeholder="Tipo Estágio"
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
              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  placeholder="Nome Contratante"
                  v-model="filters.nomeEmpresa"
                />
              </span>
            </div>
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
        <Column field="curse" header="Matrícula">
          <template #body="{ data }">
            {{ data.aluno?.matricula }}
          </template>
        </Column>
        <Column field="contratante" header="Contratante">
          <template #body="{ data }">
            {{ data?.contratante?.nome }} -
            {{ data?.contratante?.cnpj }}
          </template>
        </Column>
        <Column field="process_type" header="Tipo Estágio">
          <template #body="{ data }">
            {{ parseObrigatoriedadeEstagio(data?.tipoEstagio) }}
          </template>
        </Column>
        <Column field="process_type" header="Estágio UFPR">
          <template #body="{ data }">
            {{ data?.estagioUfpr ? "Sim" : "Não" }}
          </template>
        </Column>
        <Column field="status" header="Status" bodyStyle="color:orange;">
          <template #body="{ data }">
            <StatusTag :status="data?.statusEstagio" />
          </template>
        </Column>
        <Column field="button">
          <template #body="{ data }">
            <NuxtLink :to="`/estagio/${data.id}?perfil=coe`">
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
        :totalRecords="estagios?.totalElements"
        @page="page = $event.page"
      ></Paginator>
    </div>
  </div>
</template>
