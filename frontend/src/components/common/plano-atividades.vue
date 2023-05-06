<script>
export default {
  props: {
    termo: {
      type: Object,
      required: true,
    },
    planoAtividades: {
      type: Object,
      required: true,
    },
  },
};
</script>

<template>
  <div class="card">
    <h5>Plano de Atividades</h5>

    <div class="grid">
      <div class="col-4">
        <strong>Nome do Supervisor no Local de Estágio</strong>
        <p>{{ planoAtividades?.nomeSupervisor }}</p>
      </div>
      <div class="col-4">
        <strong>Telefone do Supervisor</strong>
        <p>{{ planoAtividades?.telefoneSupervisor }}</p>
      </div>
      <div class="col-4">
        <strong>Formação do Supervisor</strong>
        <p>{{ planoAtividades?.formacaoSupervisor }}o</p>
      </div>
      <div class="col-4">
        <strong>Nome Professor Orientador UFPR</strong>
        <p>Prof. {{ termo?.orientador?.nome }}</p>
      </div>
      <div class="col-4">
        <strong>Departamento do Orientador</strong>
        <p>{{ termo?.orientador?.departamento }}</p>
      </div>
      <div class="col-12">
        <strong>Atividades a serem desenvolvidas</strong>
        <p>{{ planoAtividades?.descricaoAtividades }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
