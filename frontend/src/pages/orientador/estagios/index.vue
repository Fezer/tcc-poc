<script lang="ts">
import { defineComponent } from "vue";
import { Estagio } from "~~/src/types/NovoEstagio";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";
import { PaginatedEstagio } from "../../../types/Termos";

export default defineComponent({
  setup() {
    const route = useRoute();

    const { statusOptions } = useTermoFilters();

    const { auth } = useAuth();

    const page = ref(0);

    const filters = reactive({
      statusEstagio: "",
      grrAluno: "",
    });
    const { data: estagios } = useAsyncData<PaginatedEstagio>(
      "estagiosOrientador",
      () =>
        $fetch(`/orientador/${auth?.value?.identifier}/estagio`, {
          params: {
            page: page.value,
            statusEstagio: filters.statusEstagio || undefined,
            grrAluno: filters.grrAluno || undefined,
          },
        }),
      {
        watch: [page, filters],
        lazy: true,
      }
    );

    return {
      estagios,
      parseTipoTermo,
      page,
      filters,
      statusOptions,
    };
  },
});
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
              >
              </Dropdown>

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
        <Column field="process_type" header="Tipo de Estágio">
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
        <Column field="button" header="Ver">
          <template #body="{ data }">
            <NuxtLink :to="`/estagio/${data.id}?perfil=orientador`">
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
