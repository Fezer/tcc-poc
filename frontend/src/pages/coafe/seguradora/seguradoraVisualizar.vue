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
    <div class="w-full flex justify-content-between mb-3">
      <NuxtLink to="/coafe/coafeSeguradoras">
        <Button
          label="Voltar"
          class="p-button-secondary"
          icon="pi pi-arrow-left"
        />
      </NuxtLink>
      <Button
        @click="
          seguradora.ativa
            ? handleInativateSeguradora()
            : handleAtivateSeguradora()
        "
        :label="seguradora.ativa ? 'Inativar' : 'Ativar'"
        class="p-button-info"
      />
      <NuxtLink :to="`seguradoraEditar?id=${seguradora.id}`">
        <Button label="Editar" />
      </NuxtLink>
      <Button
        @click="
          handleDeleteSeguradora(seguradora.id, seguradora.apolice.length)
        "
        :label="'Deletar'"
        icon="pi pi-times"
        class="p-button-danger"
      />
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
          <p>{{ parseDate(data?.dataInicio) }}</p>
        </template>
      </Column>
      <Column field="dataFim" header="Data de Fim">
        <template #body="{ data }">
          <p>{{ parseDate(data?.dataFim) }}</p>
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
import { useRouter } from "vue-router";
import { useRoute } from "vue-router";
import { useToast } from "primevue/usetoast";
import SeguradoraService from "~~/services/SeguradoraService";
import parseDate from "~/utils/parseDate";
const route = useRoute();
const toast = useToast();
const router = useRouter();
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
const handleAtivateSeguradora = async () => {
  try {
    await seguradoraService.ativarSeguradora(id).then(() => {
      toast.add({
        severity: "success",
        summary: "Seguradora Ativada com sucesso",
        life: 3000,
      });
    });
  } catch (e) {
    console.log(e);
  }
  refresh();
};
const handleDeleteSeguradora = async (id, numeroapolices) => {
  if (numeroapolices != 0) {
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "A Seguradora não pode ser deletada pois possui apólices",
      life: 3000,
    });
  }
  try {
    const response = await seguradoraService.deletaSeguradora(id).then(() => {
      return (
        toast.add({
          severity: "success",
          summary: "Seguradora deletada com sucesso123",
          life: 3000,
        }),
        router.push(`../coafeSeguradoras`)
      );
    });
  } catch (e) {
    return toast.add({
      severity: "error",
      summary: "Erro",
      detail: "Erro ao deletar a Seguradora",
      life: 3000,
    });
  }
  return {
    state,
    id,
    response,
    handleDeleteConvenio,
  };
};
</script>
