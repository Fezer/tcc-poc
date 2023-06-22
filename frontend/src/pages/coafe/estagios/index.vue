<script lang="ts">
import { defineComponent } from "vue";
import { Estagio } from "~~/src/types/NovoEstagio";
import parseObrigatoriedadeEstagio from "~~/src/utils/parseObrigatoriedadeEstagio";
import { ref } from "vue";
import { useToast } from "primevue/usetoast";
import EscolhaRelatorio from "~~/src/components/common/escolha-relatorios.vue";
import RelatorioEstagioService from "~~/services/RelatorioEstagioService";
export default defineComponent({
  components: { EscolhaRelatorio },
  setup() {
    const route = useRoute();
    const relatorioService = new RelatorioEstagioService();
    let escolhaDeRelatorio = ref(0);
    const relatorioExcel = async (id: number) => {
      escolhaDeRelatorio.value = 0;
      try {
        const file =
          await relatorioService.baixarRelatorioEstagioExcelEspecifico(id);
        var blob = new Blob([file], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });

        const link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = `relatorio-de-Estagio-excel.xlsx`;
        link.click();
      } catch (Error) {
        if (Error?.response?._data?.error) {
          return toast.add({
            severity: "error",
            summary: "Erro",
            detail: "" + Error?.response?._data?.error,
            life: 3000,
          });
        }
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Não foi possível baixar o Relatório",
          life: 3000,
        });
      }
    };
    const relatorioPDF = async (id: number) => {
      escolhaDeRelatorio.value = 0;
      try {
        const file = await relatorioService.baixarRelatorioEstagioPdfEspecifico(
          id
        );
        const fileURL = URL.createObjectURL(file);
        return window.open(fileURL, "_blank");
      } catch (Error) {
        if (Error?.response?._data?.error) {
          return toast.add({
            severity: "error",
            summary: "Erro",
            detail: "" + Error?.response?._data?.error,
            life: 3000,
          });
        }
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Não foi possível baixar o Relatório",
          life: 3000,
        });
      }
    };
    const toast = useToast();

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
      route,
      relatorioExcel,
      relatorioPDF,
      escolhaDeRelatorio,
      filters,
      statusOptions,
      parseObrigatoriedadeEstagio,
      tipoOptions,
      handleSearch,
      page,
    };
  },
});
</script>

<template>
  <div>
    <div>
      <h1>COAFE</h1>
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
            {{ data?.contratante?.cnpj || data?.contratante?.cpf }}
          </template>
        </Column>
        <Column field="process_type" header="Tipo Estágio">
          <template #body="{ data }">
            {{ data?.tipoEstagio }}
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
            <NuxtLink :to="`/estagio/${data.id}?perfil=coafe`">
              <Button
                class="p-button-icon-only p-button-outlined"
                icon="pi pi-eye"
                type="primary"
              ></Button>
            </NuxtLink>
          </template>
        </Column>
        <Column field="relatorio" header="Relatório">
          <template #body="{ data }">
            <Button
              @click="escolhaDeRelatorio = data.id"
              :label="'Relatório'"
              icon="pi pi-file"
              class="p-button-success"
            />
            <div v-if="escolhaDeRelatorio == data.id">
              <EscolhaRelatorio
                :excel="() => relatorioExcel(data.id)"
                :pdf="() => relatorioPDF(data.id)"
                :cancelar="() => (escolhaDeRelatorio = 0)"
                :description="`Gerar relatório do Estágio do ${data?.aluno?.matricula} em que formato?`"
              >
              </EscolhaRelatorio>
            </div>
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
