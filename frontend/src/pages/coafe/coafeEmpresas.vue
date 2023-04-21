<script lang="ts">
import { defineComponent } from "vue";
import ContratanteService from "~~/services/ContratanteService";
export default defineComponent({
  async setup() {
    const contratanteService = new ContratanteService();

    const { data: contratantes } = useFetch(
      "http://localhost:5000/contratante/"
    );

    console.log(contratantes);

    return { contratantes };
  },
});
</script>

<template>
  <div>
    <div class="flex justify-content-between">
      <h1>
        COAFE
        <h6>Coordenação de Atividades Formativas e Estágios</h6>
      </h1>
      <NuxtLink to="/contratantes/novo">
        <Button label="Cadastrar"></Button>
      </NuxtLink>
    </div>
    <div>
      <DataTable :value="contratantes" rowHover stripedRows>
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
            {{ data?.id }}
          </template>
        </Column>
        <Column field="cnpj" header="CNPJ">
          <template #body="{ data }">
            {{ data?.cnpj }}
          </template>
        </Column>
        <!-- <Column field="situação" header="Ativo">
          <template #body="{ c }">
            {{ c.situação }}
          </template>
        </Column> -->
        <Column field="button">
          <template #body>
            <Button label="Ver"></Button>
          </template>
        </Column>
      </DataTable>
    </div>
  </div>
</template>
