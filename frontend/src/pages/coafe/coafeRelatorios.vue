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
    <div class="card flex flex-wrap justify-content-center gap-3">
        <div class="flex align-items-center">
            <RadioButton  v-model="checked" inputId="opção1" name="tipoEstagio" value="estagioUfpr" :binary="true" />
            <label for="opção1" class="ml-2"> Estágios UFPR </label>
        </div>
        <div class="flex align-items-center">
            <RadioButton v-model="checked" inputId="opção2" name="tipoEstagio" value="estagioExterno" :binary="true" />
            <label for="opção2" class="ml-2"> Estágios Externos </label>
        </div>        
    </div>
  </div>
</template>  
<script>
  import RadioButton  from 'primevue/radiobutton';
  import Button from "primevue/button";
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

const checked = ref(false);
</script>