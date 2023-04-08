<script>
export default {
  data() {
    return {
      termos: [],
    };
  },
};
</script>

<template>
  <div>
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
    </div>
  </div>
</template>

<style></style>
