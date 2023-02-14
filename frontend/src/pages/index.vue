<script>
import aluno from './aluno.vue';
import Contratante from './contratante.vue';
import estagio from './estagio.vue';
import planoAtividades from './plano-atividades.vue';
export default {
  components: { Aluno: aluno, Estagio: estagio, PlanoAtividades: planoAtividades, Contratante }

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
          <p>Ut hendrerit nisl a varius euismod. Morbi tincidunt sodales augue, vel ullamcorper diam tincidunt ut. Quisque convallis dolor elit, eu porta odio iaculis et. Vivamus tincidunt fermentum egestas. Nam eu imperdiet elit. Sed lorem massa, rutrum quis feugiat ut, porta quis nibh. Morbi tempus magna at risus pellentesque sodales. Donec quis neque ac tellus mollis eleifend. Mauris nisi eros, congue nec imperdiet porta, accumsan vel orci. Curabitur elementum eleifend felis nec egestas. Vivamus sed dolor vitae turpis aliquet placerat. Vestibulum bibendum tellus eros, sed elementum neque venenatis et. Quisque sit amet venenatis ante, eu fringilla tellus. </p>
        </div>
      </div>
    </div>

    <Aluno />

    <Estagio />

    <PlanoAtividades />

    <Contratante />

    <div class="flex align-items-end justify-content-end gap-2">
      <Button class="   p-button-success">
        Aprovar
      </Button>
      <Button class="p-button-danger">
        Indeferir
      </Button>
    </div>
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
