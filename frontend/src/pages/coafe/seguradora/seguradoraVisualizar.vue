<template>
  <div>
    <div class="flex justify-content-between">
      <h6>
        Seguradora
        <h3>{{ seguradora.nome }}</h3>
        <h5>Dados da Seguradora</h5>
      </h6>
    </div>
    <div class="card flex-column">
      <div class="flex flex-row justify-content-between gap-10">
        <div class="flex align-items-left flex-column pb-4">
          <b>Seguradora UFPR</b>
          <Tag
            style="font-size: small"
            :value="
              seguradoraService.pegarAtivaValue(seguradora.seguradoraUfpr)
            "
            :severity="
              seguradoraService.pegarAtivaSeverity(seguradora.seguradoraUfpr)
            "
          />
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>Ativa</b>
          <Tag
            style="font-size: medium"
            :value="seguradoraService.pegarAtivaValue(seguradora.ativa)"
            :severity="seguradoraService.pegarAtivaSeverity(seguradora.ativa)"
          />
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>Numero de Apolices</b>
          <p>{{ seguradora.apolice.length }}</p>
        </div>
      </div>
    </div>
    <div class="w-full flex justify-between mb-3">
      <NuxtLink to="/coafe/coafeSeguradoras">
        <Button
          label="Voltar"
          class="p-button-secondary"
          icon="pi pi-arrow-left"
        />
      </NuxtLink>
      <Button
        @click="handleInativateSeguradora"
        :label="'Inativar'"
        class="p-button-info"
        :disabled="!seguradora.ativa"
      />
      <Button label="Editar" severity="info" />
    </div>
    <DataTable class="flex-column" :value="seguradora.apolice" rowHover>
      <template #header>
        <div>
          <span>
            <h4><b>Apolices</b></h4>
          </span>
        </div>
      </template>
      <template #empty>
        A Seguradora {{ seguradora.nome }} não possui nenhuma apolice
      </template>
      <Column field="numero" header="Número">
        <template #body="{ data }">
          {{ data.numero }}
        </template>
      </Column>
      <Column field="dataInicio" header="Data de Inicio">
        <template #body="{ data }">
          {{ data.dataInicio }}
        </template>
      </Column>
      <Column field="dataFim" header="Data de Fim">
        <template #body="{ data }">
          {{ data.dataFim }}
        </template>
      </Column>
      <Column field="links" header="Links">
        <template #body="{ data }">
          {{ data.links }}
        </template>
      </Column>
    </DataTable>
  </div>
</template>

<script setup>
import Button from "primevue/button";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import { useRoute } from "vue-router";
import { useToast } from "primevue/usetoast";
import SeguradoraService from "~~/services/SeguradoraService";
const route = useRoute();
const toast = useToast();
const id = route.query.id;
const seguradoraService = new SeguradoraService();
const { data: seguradora, refresh } = await useFetch(
  `http://localhost:5000/seguradora/${id}`
);
const handleInativateSeguradora = async () => {
  try {
    await seguradoraService.desativarSeguradora(id).then(() => {
      toast.add({
        severity: "success",
        summary: "Seguradora Inativada com sucesso",
        life: 3000,
      });
    });
  } catch (e) {
    console.log(e);
  }
  refresh();
};
</script>
