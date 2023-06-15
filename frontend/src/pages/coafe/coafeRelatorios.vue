<template>
  <div>
    <div class="flex justify-content-between">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
    </div>
    <div class="flex justify-content-between">
      <span class="p-input-icon-left">
        <h3><b>Relatórios</b></h3>
      </span>
    </div>
    <div class="card">
      <h4 class="flex justify-content-center">Gerar Relatórios</h4>
      <div class="w-full flex justify-content-around p-3">
        <div class="card p-fluid col-4 m-1">
          <label for="agente" class="flex justify-content-center"
            ><h5>Seguradoras</h5></label
          >
          <div class="w-full flex justify-content-end mt-3">
            <Button
              @click=""
              :label="'Excel'"
              icon="pi pi-file-excel"
              class="p-button-success m-3"
            />
            <Button
              @click=""
              :label="'PDF'"
              icon="pi pi-file-pdf"
              class="p-button-danger m-3"
            />
          </div>
        </div>
        <div class="card p-fluid col-4 m-1">
          <label for="contratante" class="flex justify-content-center"
            ><h5>Certificados</h5></label
          >
          <div class="w-full flex justify-content-end mt-3">
            <Button
              @click=""
              :label="'Excel'"
              icon="pi pi-file-excel"
              class="p-button-success m-3"
            />
            <Button
              @click=""
              :label="'PDF'"
              icon="pi pi-file-pdf"
              class="p-button-danger m-3"
            />
          </div>
        </div>
        <div class="card p-fluid col-4 m-1">
          <label for="estagio" class="flex justify-content-center"
            ><h5>Estágios</h5></label
          >
          <div class="w-full flex justify-content-end mt-3">
            <Button
              @click=""
              :label="'Excel'"
              icon="pi pi-file-excel"
              class="p-button-success m-3"
            />
            <Button
              @click=""
              :label="'PDF'"
              icon="pi pi-file-pdf"
              class="p-button-danger m-3"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="card">
      <h4 class="flex justify-content-center">Gerar Relatórios Especificos</h4>
      <div class="w-full flex justify-content-around p-3">
        <div class="card p-fluid col-4 m-1">
          <label for="agente" class="flex justify-content-center"
            ><h5>Nome do Agente de Integração</h5></label
          >
          <Dropdown
            v-model="agenteSelecionado"
            :options="agentes"
            optionLabel="nome"
            placeholder="Selecione um Agente de Integração"
            filter
          />
          <div class="w-full flex justify-content-end mt-3">
            <Button
              @click=""
              :label="'Excel'"
              icon="pi pi-file-excel"
              class="p-button-success m-3"
            />
            <Button
              @click=""
              :label="'PDF'"
              icon="pi pi-file-pdf"
              class="p-button-danger m-3"
            />
          </div>
        </div>
        <div class="card p-fluid col-4 m-1">
          <label for="contratante" class="flex justify-content-center"
            ><h5>Nome do Contratante</h5></label
          >
          <Dropdown
            v-model="contratanteSelecionado"
            :options="contratantes"
            optionLabel="nome"
            placeholder="Selecione um Contratante"
            filter
          />
          <div class="w-full flex justify-content-end mt-3">
            <Button
              @click=""
              :label="'Excel'"
              icon="pi pi-file-excel"
              class="p-button-success m-3"
            />
            <Button
              @click=""
              :label="'PDF'"
              icon="pi pi-file-pdf"
              class="p-button-danger m-3"
            />
          </div>
        </div>
        <div class="card p-fluid col-4 m-1">
          <label for="estagio" class="flex justify-content-center"
            ><h5>Nome do Estágio</h5></label
          >
          <Dropdown
            v-model="estagioSelecionado"
            :options="estagios"
            optionLabel="aluno.nome"
            placeholder="Selecione um Estágio"
            filter
          />
          <div class="w-full flex justify-content-end mt-3">
            <Button
              @click=""
              :label="'Excel'"
              icon="pi pi-file-excel"
              class="p-button-success m-3"
            />
            <Button
              @click=""
              :label="'PDF'"
              icon="pi pi-file-pdf"
              class="p-button-danger m-3"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Button from "primevue/button";
import Dropdown from "primevue/dropdown";
import { ref } from "vue";
</script>
<script setup>
const agenteSelecionado = ref();
const contratanteSelecionado = ref();
const estagioSelecionado = ref();

const { data: agentes } = await useFetch(
  `http://localhost:5000/agente-integrador/`
);
const { data: contratantes } = await useFetch(
  `http://localhost:5000/contratante/`
);
const { data: estagios } = await useFetch(`http://localhost:5000/estagio/`);
</script>
