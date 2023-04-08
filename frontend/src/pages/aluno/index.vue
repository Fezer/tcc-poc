<script>
import Column from "primevue/column";
import DataTable from "primevue/datatable";

export default {
  data() {
    return {
      termos: [],
    };
  },
  components: { DataTable, Column },
};
</script>

<template>
  <div>
    <div class="flex flex-col">
      <h2 class="m-0">João Feliz da Silva</h2>
      <p class="m-0">GRR20200141</p>
      <p>Tecnologia em Análise e Desenvolvimento de sistemas</p>
    </div>
    <div
      class="h-full w-full flex items-center justify-center flex-col pt-3"
      v-if="termos.length === 0"
    >
      <p>
        Você não possui nenhum estágio ativo! Deseja iniciar um novo estágio
        gerando um termo de compromisso?
      </p>
      <NuxtLink to="/aluno/termo/gerar">
        <Button
          label="Gerar novo termo de compromisso"
          icon="pi pi-plus"
        ></Button>
      </NuxtLink>

      <NuxtLink to="/estagio/1">
        <Button
          label="Ir para estágio dummy (dev)"
          icon="pi pi-arrow-right"
          class="p-button-secondary mt-2"
        ></Button>
      </NuxtLink>
    </div>
  </div>
</template>

<style></style>
