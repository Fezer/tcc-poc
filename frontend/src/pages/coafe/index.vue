<template>
  <div>
    <h1>COAFE
      <h6>Coordenação de Atividades Formativas e Estágios</h6>
    </h1>
  </div>
  <div>
      <DataTable :value="processes" rowHover stripedRows>
        <template #header>
            <div class="flex justify-content-between">
                <span class="p-input-icon-left">
               <h4><b>Processos pendentes de definição</b></h4>
            </span>
                <span class="p-input-icon-left">
                    <i class="pi pi-search" />
                    <InputText placeholder="Keyword Search" />
                </span>
            </div>        
        </template>
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
      <Column field="action" header="Ação necessária" bodyStyle="color:orange;">
        <template #body="{ data }">
            {{ data.action }}
        </template>        
      </Column>
      <Column field="button">
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
      processes:[{
      process:'#3124',
      process_type:'Termo de Compromisso',
      student_name:'João da Silva',
      curse:'Economia',
      action: 'Parecer estágio'
      },
    {
      process:'#3120',
      process_type:'Termo Aditivo',
      student_name:'Maria da Silva Ferreira',
      curse:'Jornalismo',
      action: 'Parecer termo',
      }
    ],
    };
  },
};
</script>