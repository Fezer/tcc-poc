<script lang="ts">
import { defineComponent } from "vue";
import { Estagio } from "~~/src/types/NovoEstagio";
import parseTipoTermo from "~~/src/utils/parseTipoProcesso";
import { ref } from "vue";
import { useToast } from "primevue/usetoast";
import EscolhaRelatorio from "~~/src/components/common/escolha-relatorios.vue";
export default defineComponent({
  components: { EscolhaRelatorio },
  setup() {
    const route = useRoute();

    let escolhaDeRelatorio = ref(0);
    const relatorioExcel = (id: number) => {
      escolhaDeRelatorio.value = 0;
      return toast.add({
        severity: "success",
        summary: "Excel escolhido com id: " + id,
        life: 3000,
      });
    };
    const relatorioPDF = (id: number) => {
      escolhaDeRelatorio.value = 0;
      return toast.add({
        severity: "success",
        summary: "PDF escolhido com id: " + id,
        life: 3000,
      });
    };
    const toast = useToast();

    const { data: estagios } = useFetch<Estagio>(`/estagio/`);

    return {
      estagios,
      route,
      relatorioExcel,
      relatorioPDF,
      escolhaDeRelatorio,
      parseTipoTermo,
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
      <DataTable :value="estagios" rowHover stripedRows :show-gridlines="true">
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
              <Button label="Ver Estágio" icon="pi pi-search"></Button>
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
    </div>
  </div>
</template>
