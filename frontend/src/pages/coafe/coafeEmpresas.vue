<template>
    <div class="flex justify-content-between">
    <h1>COAFE
      <h6>Coordenação de Atividades Formativas e Estágios</h6>
    </h1>
    <span class="p-input-icon-left">
        <Button label="Cadastrar"></Button>  
    </span>    
    </div>
    <div>
      <DataTable :value="Empresas" rowHover stripedRows>
        <template #header>
            <div class="flex justify-content-between">
                <span class="p-input-icon-left">
               <h4><b>Empresas</b></h4>
            </span>
                <span class="p-input-icon-left">
                    <i class="pi pi-search" />
                    <InputText placeholder="Keyword Search" />
                </span>
            </div>        
        </template>
        <template #empty> No customers found. </template>
        <template #loading> Loading customers data. Please wait. </template>
        <Column field="Empresas" header="Nome da Empresa">
            <template #body="{ data }">
                {{ data.empresa}}
            </template>
        </Column>
        <Column field="cnpj" header="CNPJ">
            <template #body="{ data }">
                {{ data.cnpj }}
            </template>
        </Column>
        <Column field="situação" header="Ativo">
            <template #body="{ data }">
                {{ data.situação }}
            </template>
        </Column>
        <Column field="button">
          <template #body>
            <Button label="Ver"></Button>
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
        Empresas:[{
        empresa:'Empresa de Estágio 1',
        cnpj:'124412142',
        situação:'ATIVO',
        },
      {
        empresa:'Empresa de Estágio 2',
        cnpj:'89024908',
        situação:'INATIVO',
        }]
      };
    },
  };
  </script>