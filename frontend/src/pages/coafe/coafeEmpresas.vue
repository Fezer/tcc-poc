<script lang="ts">
import { defineComponent } from "vue";
import { ref } from "vue";
import { FilterMatchMode } from "primevue/api";
import ContratanteService from "~~/services/ContratanteService";
import { useToast } from "primevue/usetoast";
import EscolhaRelatorio from "~~/src/components/common/escolha-relatorios.vue";
import Contratante from "../../types/Contratante";
export default defineComponent({
  components: { EscolhaRelatorio },
  async setup() {
    let escolhaDeRelatorio = ref(0);
    const toast = useToast();
    const contratanteService = new ContratanteService();

    const relatorioExcel = async (id: number) => {
      escolhaDeRelatorio.value = 0;
      try {
        const file =
          await contratanteService.baixarRelatorioEstagioExcelEspecifico(id);
        var blob = new Blob([file], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });

        const link = document.createElement("a");
        link.href = window.URL.createObjectURL(blob);
        link.download = `relatorio-de-Empresa-excel.xlsx`;
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
        const file =
          await contratanteService.baixarRelatorioEstagioPdfEspecifico(id);
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

    const page = ref(0);

    const filtros = reactive({
      nome: "",
      cnpj: "",
    });
    const { data: contratantes } = useAsyncData<{
      totalElements: number;
      content: Contratante[];
    }>(
      "listaContratantes",
      () =>
        $fetch("/contratante/", {
          params: {
            page: page.value,
            nome: filtros?.nome || undefined,
            cnpj: filtros?.cnpj || undefined,
          },
        }),
      {
        watch: [filtros, page],
      }
    );

    return {
      contratantes,
      filtros,
      toast,
      contratanteService,
      relatorioExcel,
      relatorioPDF,
      escolhaDeRelatorio,
      page,
    };
  },
});
</script>
<Toast />
<template>
  <div>
    <div class="flex justify-content-between">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
      <!-- <NuxtLink to="/contratantes/novo">
        <Button label="Cadastrar"></Button>
      </NuxtLink> -->
    </div>
    <div>
      <DataTable
        v-model:filters="filtros"
        :value="contratantes?.content"
        paginator
        :globalFilterFields="['Empresas']"
        rowHover
        stripedRows
        :rows="10"
        :totalRecords="contratantes?.totalElements"
        @page="page = $event.page"
        :show-gridlines="true"
      >
        <template #header>
          <div class="flex justify-content-between">
            <span class="p-input-icon-left">
              <h4><b>Empresas</b></h4>
            </span>

            <div class="flex gap-2">
              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  v-model="filtros.nome"
                  placeholder="Pesquisar por nome"
                />
              </span>

              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  v-model="filtros.cnpj"
                  placeholder="Pesquisar por CNPJ"
                />
              </span>
            </div>
          </div>
        </template>
        <template #empty> Nenhuma Empresa encontrada. </template>
        <template #loading> Carregando dados. Por favor espere. </template>
        <Column field="Id" header="Identificador">
          <template #body="{ data }"> #{{ data?.id }} </template>
        </Column>
        <Column field="Empresas" header="Nome da Empresa">
          <template #body="{ data }">
            {{ data?.nome }}
          </template>
        </Column>
        <Column field="cnpj" header="CNPJ">
          <template #body="{ data }">
            {{ data?.cnpj }}
          </template>
        </Column>
        <Column field="representante" header="Representante">
          <template #body="{ data }">
            {{ data?.representanteEmpresa }}
          </template>
        </Column>
        <Column field="ver" header="Ver">
          <template #body="{ data }">
            <NuxtLink :to="`/coafe/empresa/${data.id}`">
              <Button
                :label="'Ver'"
                class="p-button-primary"
                icon="pi pi-search"
              />
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
                :description="`Gerar relatório da Empresa ${data.nome} em que formato?`"
              >
              </EscolhaRelatorio>
            </div>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
