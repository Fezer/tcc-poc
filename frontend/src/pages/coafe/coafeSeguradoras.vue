<template>
  <div>
    <div class="flex justify-content-between items-center">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
      <span class="p-input-icon-left">
        <NuxtLink to="/coafe/seguradora/novo">
          <Button
            label="Cadastrar"
            class="p-button-success"
            icon="pi pi-plus"
          ></Button>
        </NuxtLink>
      </span>
    </div>
    <div>
      <DataTable
        :value="seguradoras?.content"
        rowHover
        stripedRows
        paginator
        :show-gridlines="true"
        :rows="10"
        @page="page = $event.page"
      >
        <template #header>
          <div class="flex justify-content-between">
            <span class="p-input-icon-left">
              <h4><b>Seguradoras</b></h4>
            </span>
            <div class="flex items-center gap-2">
              <SelectButton
                v-model="filtros.ativo"
                :options="[
                  {
                    label: 'Ativo',
                    value: true,
                  },
                  {
                    label: 'Inativo',
                    value: false,
                  },
                ]"
                optionLabel="label"
                optionValue="value"
                aria-labelledby="basic"
              />

              <Dropdown
                v-model="filtros.seguradoraUFPR"
                :options="[
                  {
                    label: 'UFPR',
                    value: true,
                  },
                  {
                    label: 'Externa',
                    value: false,
                  },
                  {
                    label: 'UFPR & Externa',
                    value: undefined,
                  },
                ]"
                optionLabel="label"
                optionValue="value"
                aria-labelledby="basic"
                placeholder="Seguradora UFPR"
              />

              <span class="p-input-icon-left">
                <i class="pi pi-search" />
                <InputText
                  v-model="filtros.nome"
                  placeholder="Pesquisar Seguradoras"
                />
              </span>
            </div>
          </div>
        </template>
        <template #empty> Nenhuma seguradora encontrada. </template>
        <template #loading>
          Carregando seguradoras, por favor aguarde.
        </template>
        <Column field="nome" header="Nome da Seguradora" filterField="name">
          <template #body="{ data }">
            {{ data.nome }}
          </template>
        </Column>
        <Column field="apolice" header="Quantidade de Apólices">
          <template #body="{ data }">
            {{ data.apolice.length }}
          </template>
        </Column>
        <Column field="seguradoraUfpr" header="Seguradora UFPR">
          <template #body="{ data }">
            <Tag
              style="font-size: medium"
              :value="seguradoraService.pegarAtivaValue(data.seguradoraUfpr)"
              :severity="
                seguradoraService.pegarAtivaSeverity(data.seguradoraUfpr)
              "
            />
          </template>
        </Column>
        <Column field="ativa" header="Ativo">
          <template #body="{ data }">
            <Tag
              style="font-size: medium"
              :value="seguradoraService.pegarAtivaValue(data.ativa)"
              :severity="seguradoraService.pegarAtivaSeverity(data.ativa)"
            />
          </template>
        </Column>
        <Column field="button" header="Ver">
          <template #body="{ data }">
            <NuxtLink
              :to="`/coafe/seguradora/seguradoraVisualizar?id=${data.id}`"
            >
              <Button
                class="p-button-icon-only p-button-outlined"
                icon="pi pi-eye"
                type="primary"
              />
            </NuxtLink>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
<script setup>
import { reactive, ref } from "vue";
import Column from "primevue/column";
import DataTable from "primevue/datatable";
import Button from "primevue/button";
import SeguradoraService from "~~/services/SeguradoraService";
import { FilterMatchMode } from "primevue/api";
const seguradoraService = new SeguradoraService();

const filtros = reactive({
  nome: "",
  ativo: true,
  seguradoraUFPR: true,
});

const page = ref(0);

const { data: seguradoras } = useAsyncData(
  "seguradorasList",
  () =>
    $fetch("/seguradora/", {
      params: {
        page: page.value,
        nome: filtros.nome || undefined,
        ativo: filtros.ativo,
        seguradoraUFPR: filtros.seguradoraUFPR,
      },
    }),
  {
    watch: [page, filtros],
  }
);
</script>
