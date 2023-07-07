<template>
  <div>
    <h1>
      COAFE
      <h6>Coordenação de Atividades Formativas e Estágios</h6>
    </h1>
  </div>
  <div>
    <DataTable :value="processes" rowHover stripedRows :show-gridlines="true">
      <template #header>
        <div class="flex justify-content-between">
          <span class="p-input-icon-left">
            <h4><b>Procesos</b></h4>
          </span>
          <span class="p-input-icon-left">
            <i class="pi pi-search" />
            <InputText placeholder="Keyword Search" />
          </span>
        </div>
      </template>
      <template #empty> No customers found. </template>
      <template #loading> Loading customers data. Please wait. </template>
      <Column field="process" header="Processo">
        <template #body="{ data }">
          {{ data.process }}
        </template>
      </Column>
      <Column field="process_type" header="Tipo de Processo">
        <template #body="{ data }">
          {{ data.process_type }}
        </template>
      </Column>
      <Column field="student_name" header="Nome do Aluno">
        <template #body="{ data }">
          {{ data.student_name }}
        </template>
      </Column>
      <Column field="curse" header="Curso">
        <template #body="{ data }">
          {{ data.curse }}
        </template>
      </Column>
      <Column field="last_update" header="Última Atualização">
        <template #body="{ data }">
          {{ data.last_update }}
        </template>
      </Column>
      <Column field="button" header="Ver">
        <template #body>
          <Button label="Ver contato"></Button>
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<script>
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import Button from "primevue/button";
export default {
  data() {
    return {
      processes: [
        {
          process: "#3124",
          process_type: "Termo de Compromisso",
          student_name: "João da Silva",
          curse: "Economia",
          last_update: "13/12/2022",
        },
        {
          process: "#3120",
          process_type: "Termo Aditivo",
          student_name: "Maria da Silva Ferreira",
          curse: "Jornalismo",
          last_update: "10/12/2022",
        },
        {
          process: "#3110",
          process_type: "Relatório de Estágio",
          student_name: "João da Silva",
          curse: "Economia",
          last_update: "13/12/2022",
        },
        {
          process: "#3120",
          process_type: "Estágio",
          student_name: "Maria da Silva Ferreira",
          curse: "Educação Física",
          last_update: "13/12/2022",
        },
        {
          process: "#3124",
          process_type: "Termo de Compromisso",
          student_name: "João da Silva",
          curse: "Economia",
          last_update: "13/12/2022",
        },
        {
          process: "#3120",
          process_type: "Termo Aditivo",
          student_name: "Maria da Silva Ferreira",
          curse: "Direito",
          last_update: "10/12/2022",
        },
        {
          process: "#3124",
          process_type: "Termo de Compromisso",
          student_name: "João da Silva",
          curse: "Economia",
          last_update: "13/12/2022",
        },
        {
          process: "#3120",
          process_type: "Termo Aditivo",
          student_name: "Maria da Silva Ferreira",
          curse: "Medicina",
          last_update: "10/12/2022",
        },
      ],
    };
  },
};
</script>
