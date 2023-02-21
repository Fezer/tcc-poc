<script>
export default {
  props: {
    termo: {
      type: Object,
      required: true
    }
  }

};
</script>

<template>
  <div class="card">
    <h5>Dados do estágio</h5>

    <div class="grid">
      <div class="col-4">
        <strong>Tipo do Estágio</strong>
        <p>{{ termo?.tipoEstagio }}</p>
      </div>
      <div class="col-4">
        <strong>Estágio na UFPR</strong>
        <p>{{ termo?.estagioUfpr ? "Sim" : "Não" }}</p>
      </div>
      <div class="col-4">
        <strong>Jornada Diária</strong>
        <p>{{ termo?.jornadaDiaria }} horas</p>
      </div>
      <div class="col-4">
        <strong>Jornada Semanal</strong>
        <p>{{ termo?.jornadaSemanal }} horas</p>
      </div>

      <div class="col-4">
        <strong>Valor da Bolsa Auxílio (mensal)</strong>
        <p>{{ Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(termo?.valorBolsa || 0) }}</p>
      </div>
      <div class="col-4">
        <strong>Valor do Auxílio Transporte</strong>
        <p>{{ Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(termo?.valorTransporte || 0) }}</p>
      </div>
    </div>
  </div>
</template>

<style>

</style>
