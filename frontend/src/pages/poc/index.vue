<script>
import aluno from './aluno.vue';
export default {
  components: { Aluno: aluno }

};
</script>

<template>
  <div>
    <small>Processos > Ver processo</small>
    <h2>Termo de Compromisso</h2>

    <div class="card">
      <h4>Andamento do processo</h4>

      <ProgressBar :value="50" :show-value="false" style="height:15px;" class="mb-3" />

      <div>
        <div class="grid">
          <div class="text-box col-6">
            <strong>Status</strong>
            <big class="text-blue-500">
              ANÁLISE COORDENAÇÃO
            </big>
          </div>
          <div class="text-box col-6">
            <strong>Data de Início do processo</strong>
            <big>01/01/2021</big>
          </div>
        </div>
        <div>
          <strong>Detalhes</strong>
          <p>Lorem Ipsium afn asfklsa flkasnf alsknnwqklfn flqwknf qlwkn f</p>
        </div>
      </div>
    </div>

    <Aluno />
  </div>
</template>

<style scoped>
.col-4{
  margin-bottom:1rem;
}

.text-box{
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap:5px;
  margin-bottom:1rem;
}
</style>
