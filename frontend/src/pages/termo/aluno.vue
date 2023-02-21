<script>
export default {
  props: {
    aluno: {
      type: Object,
      default: () => ({})
    }
  }
};
</script>

<script scoped>
</script>

<template>
  <div class="card">
    <h5>Dados do aluno</h5>

    <div class="grid">
      <div class="col-4">
        <strong>Nome completo</strong>
        <p>{{ aluno?.nome }}</p>
      </div>
      <div class="col-4">
        <strong>PcD</strong>
        <p>{{ aluno?.isPcD ? "Sim" : "Não" }}</p>
      </div>
      <div class="col-4">
        <strong>Data de Nascimento</strong>
        <p>{{ aluno?.dataNascimento }}</p>
      </div>
      <!-- <div class="col-4">
        <strong>CPF</strong>
        <p>111.111.111-11</p>
      </div> -->
      <div class="col-4">
        <strong>RG</strong>
        <p>{{ aluno?.rg }}</p>
      </div>
      <div class="col-4">
        <strong>GRR</strong>
        <p>{{ aluno?.grr }}</p>
      </div>

      <div class="col-4">
        <strong>Email</strong>
        <p>{{ aluno?.email }}</p>
      </div>

      <div class="col-4">
        <strong>Período Atual</strong>
        <p>{{ aluno?.periodoAtual }}</p>
      </div>
      <div class="col-6">
        <strong>Curso</strong>
        <p>{{ aluno?.curso }}</p>
      </div>
    </div>
  </div>
</template>

<style>

</style>
