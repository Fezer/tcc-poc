<template>
  <div>
    <div class="flex justify-content-between">
      <h6>
        Agente
        <h3>{{ agente.nome }}</h3>
        <h5>Dados do Agente de Integração</h5>
      </h6>
    </div>
    <div class="card flex-column">
      <div class="flex flex-row justify-content-between gap-10">
        <div class="flex align-items-left flex-column pb-4">
          <b>Telefone</b>
          <p>{{ agente.telefone }}</p>
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>CNPJ</b>
          <p>{{ agente.cnpj }}</p>
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>Numero de Convênios</b>
          <p>{{ agente.convenio.length }}</p>
        </div>
      </div>
    </div>
    <DataTable class="flex-column" :value="agente.convenio" rowHover>
      <template #header>
        <div class="flex flex-row justify-content-between gap-10">
          <span class="flex align-items-left flex-column pb-4">
            <p><b>Cônvênios</b></p>
          </span>
          <div class="w-full flex justify-end gap-2">
            <NuxtLink :to="`novo/adicionarConvenio?id=${agente.id}`">
              <Button
                :label="'Adicionar'"
                class="p-button-success"
                icon="pi pi-plus"
              />
            </NuxtLink>
            <Button
              @click="handleDeleteAgente(agente.id, agente.convenio.length)"
              :label="'Deletar'"
              icon="pi pi-times"
              class="p-button-danger"
            />
          </div>
        </div>
      </template>
      <template #empty>
        o Agente {{ agente.nome }} não possui nenhum convênio
      </template>
      <Column field="numero" header="Número">
        <template #body="{ data }">
          {{ data.numero }}
        </template>
      </Column>
      <Column field="descricao" header="Descrição">
        <template #body="{ data }">
          {{ data.descricao }}
        </template>
      </Column>
      <Column field="dataInicio" header="Data de Inicio">
        <template #body="{ data }">
          <p>{{ parseDate(data?.dataInicio) }}</p>
        </template>
      </Column>
      <Column field="dataFim" header="Data de Fim">
        <template #body="{ data }">
          {{ parseDate(data?.dataFim) }}
        </template>
      </Column>
      <Column field="button">
        <template #body="{ data }">
          <NuxtLink
            :to="`/coafe/agentes-integracao/convenioEditar?id=${data.id}`"
          >
            <Button label="Editar" />
          </NuxtLink>
        </template>
      </Column>
      <Column field="button">
        <template #body="{ data }">
          <Button
            @click="handleDeleteConvenio(data.id)"
            :label="'Deletar'"
            icon="pi pi-times"
            class="p-button-danger"
          />
        </template>
      </Column>
    </DataTable>
    <div class="flex flex-row justify-content-end flex-wrap pb-2 gap-2">
      <NuxtLink to="/coafe/coafeAgentes">
        <Button
          label="Voltar"
          class="p-button-secondary"
          icon="pi pi-arrow-left"
        />
      </NuxtLink>
      <NuxtLink :to="`agenteEditar?id=${agente.id}`">
        <Button label="Editar" />
      </NuxtLink>
    </div>
  </div>
</template>
<script setup>
import Button from "primevue/button";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import { useToast } from "primevue/usetoast";
import parseDate from "~/utils/parseDate";
import ConvenioService from "~~/services/ConvenioService";
import AgenteService from "~~/services/AgenteService";
const route = useRoute();
const router = useRouter();

const id = route.query.id;
const { data: agente, refresh } = await useFetch(
  `http://localhost:5000/agente-integrador/${id}`
);
const convenioService = new ConvenioService();
const agenteService = new AgenteService();
const toast = useToast();
const handleDeleteConvenio = async (id) => {
  try {
    const response = await convenioService.deletaConvenio(id).then(() => {
      return toast.add({
        severity: "success",
        summary: "Convênio deletado com sucesso",
        life: 3000,
      });
    });
  } catch (e) {
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Erro ao deletar o convênio",
      life: 3000,
    });
    console.log(e);
  }
  refresh();
  return {
    state,
    id,
    response,
    handleDeleteConvenio,
  };
};
const handleDeleteAgente = async (id, numeroCovenios) => {
  if (numeroCovenios != 0) {
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "O Agente não pode ser Deletado pois possui convênios",
      life: 3000,
    });
  }
  try {
    const response = await agenteService.deletaAgente(id).then(() => {
      return (
        toast.add({
          severity: "success",
          summary: "Agente de Integração deletado com sucesso",
          life: 3000,
        }),
        router.push(`../coafeAgentes`)
      );
    });
  } catch (e) {
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Erro ao deletar o Agente de Integração",
      life: 3000,
    });
    console.log(e);
  }

  return {
    state,
    id,
    response,
    handleDeleteConvenio,
  };
};
</script>
