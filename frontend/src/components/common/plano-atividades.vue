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
        <p>{{ planoAtividades?.formacaoSupervisor }}</p>
      </div>
      <div class="col-4">
        <strong>Nome do Professor Orientador UFPR</strong>
        <p>Prof. {{ termo?.orientador?.nome }}</p>
      </div>
      <div class="col-12">
        <strong>Atividades a Serem Desenvolvidas</strong>
        <p>{{ planoAtividades?.descricaoAtividades }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
