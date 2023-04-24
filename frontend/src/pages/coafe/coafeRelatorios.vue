<template>
    <div class="flex justify-content-between">
      <h1>COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
    </div>
    <div class="flex justify-content-between">
      <span class="p-input-icon-left">
        <h3><b>Relatórios</b></h3>
      </span>
    </div>
    <div>
    <span class="p-input-icon-left">
        <h5>Relatório de Estágios Ativos</h5>
    </span>
    <div class="card flex-column">
      <div class="flex flex-row gap-10">
        <div class="flex align-items-left pb-4">
            <Checkbox  v-model="checked" inputId="opção1" name="tipoEstagio" value="estagioUfpr" :binary="true" />
            <label for="opção1" class="ml-2"> Estágios UFPR </label>
        </div>
        <div class="flex align-items-left pb-6">
            <Checkbox v-model="checked" inputId="opção2" name="tipoEstagio" value="estagioExterno" :binary="true" />
            <label for="opção2" class="ml-2"> Estágios Externos </label>
        </div>        
      </div>
      <div>
        <div class="flex align-items-center pb-6 gap-6">
          <span class="p-float-label ml-2">            
            <Calendar v-model="date" dateFormat="dd/mm/yy" inputId="dataInicio" showIcon showButtonBar />
            <label for="dataInicio">De </label>
          </span>
          <span class="p-float-label ml-2">
            <Calendar v-model="date" dateFormat="dd/mm/yy" inputId="DatadeFim" showIcon  showButtonBar />
            <label for="DatadeFim">Até</label>
          </span>
        </div>
      </div>
      <div class="flex flex-column gap-1">
          <label for="curso">Curso</label>
          <InputText id="curso" v-model="value" />
      </div>
    </div>
    <div class="flex justify-content-end">
        <Button label="Cadastrar" class="botao-principal"></Button>  
    </div>
  </div>
  <div>
    <span class="p-input-icon-left">
        <h5>Relatório de Indeferimentos</h5>
    </span>
    <div class="card flex-column">
      <div class="flex flex-row gap-10">
        <div class="flex align-items-left pb-4">
            <Checkbox  v-model="checked" inputId="opção1" name="tipoEstagio" value="estagioUfpr" :binary="true" />
            <label for="opção1" class="ml-2"> Estágios UFPR </label>
        </div>
        <div class="flex align-items-left pb-6">
            <Checkbox v-model="checked" inputId="opção2" name="tipoEstagio" value="estagioExterno" :binary="true" />
            <label for="opção2" class="ml-2"> Estágios Externos </label>
        </div>        
      </div>
      <div>
        <div class="flex align-items-center pb-6 gap-6">
          <span class="p-float-label ml-2">            
            <Calendar v-model="date" dateFormat="dd/mm/yy" inputId="dataInicio" showIcon showButtonBar />
            <label for="dataInicio">De </label>
          </span>
          <span class="p-float-label ml-2">
            <Calendar v-model="date" dateFormat="dd/mm/yy" inputId="DatadeFim" showIcon  showButtonBar />
            <label for="DatadeFim">Até</label>
          </span>
        </div>
      </div>
      <div class="flex flex-column gap-1">
          <label for="curso">Curso</label>
          <InputText id="curso" v-model="value" />
      </div>
    </div>
    <div class="flex justify-content-end">
        <Button label="Cadastrar" class="botao-principal"></Button>  
    </div>
  </div>  
</template>  
<script>
  import InputText from "primevue/inputtext";
  import Button from "primevue/button";
  import Checkbox from 'primevue/checkbox';
 export default {
    data() {
      return {
        Relatorios:[{
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
<script setup>
import { ref } from "vue";
const value = ref(null);
const date = ref();
</script>
