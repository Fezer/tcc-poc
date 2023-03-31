<script>
export default {

};
</script>

<template>
  <div class="h-full w-full flex items-center justify-center flex-col pt-3">
    <p>Você não possui nenhum estágio ativo. Deseja iniciar um novo estágio gerando um termo de compromisso?</p>
    <NuxtLink to="/aluno/termo/gerar">
      <Button label="Gerar novo termo de compromisso" icon="pi pi-plus"></Button>
    </NuxtLink>
  </div>
</template>

<style>

</style>
