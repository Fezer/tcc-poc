<script>
import aluno from './aluno.vue';
import Contratante from './contratante.vue';
import estagio from './estagio.vue';
import planoAtividades from './plano-atividades.vue';
export default {
  components: { Aluno: aluno, Estagio: estagio, PlanoAtividades: planoAtividades, Contratante },
  methods: {
    async responderTermo(resposta) {
      const respostaFormated = resposta === 'aprovar' ? 'aprovado' : 'reprovado';

      await fetch(`http://localhost:5000/termo/${resposta}/coafe/${useRoute().params.id}`, {
        method: 'PUT',
        body: JSON.stringify({}),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(() => {
        console.log('Aprovado com sucesso');
        this.$toast.add({ severity: 'success', summary: `${respostaFormated.toUpperCase()}`, detail: `Termo ${respostaFormated} com sucesso`, life: 3000 });
      }).catch((err) => {
        console.error(err);
        this.$toast.add({ severity: 'error', summary: 'Ops!', detail: `Tivemos um problema para ${respostaFormated} o termo`, life: 3000 });
      });
    }
  }

};
</script>

<script setup>
const route = useRoute();

const { id } = route.params;

const { data: termo } = await useFetch(`http://localhost:5000/termo/${id}`);

const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);
</script>

<template>
  <div>
    <Toast />
    <small>Processos > Ver processo</small>
    <h2>Termo de Compromisso</h2>

    <div class="card">
      <h4>Andamento do processo</h4>

      <ProgressBar :value="50" :show-value="false" style="height:15px;" class="mb-3" />

      <div>
        <div class="grid">
          <div class="text-box col-6">
            <strong>Status</strong>
            <span class="text-blue-500">
              ANÁLISE {{ termo?.etapaFluxo }}
            </span>
          </div>
          <!-- <div class="text-box col-6">
            <strong>Data de Início do processo</strong>
            <span>01/01/2021</span>
          </div> -->
        </div>
        <div>
          <strong>Detalhes</strong>
          <p>Ut hendrerit nisl a varius euismod. Morbi tincidunt sodales augue, vel ullamcorper diam tincidunt ut. Quisque convallis dolor elit, eu porta odio iaculis et. Vivamus tincidunt fermentum egestas. Nam eu imperdiet elit. Sed lorem massa, rutrum quis feugiat ut, porta quis nibh. Morbi tempus magna at risus pellentesque sodales. Donec quis neque ac tellus mollis eleifend. Mauris nisi eros, congue nec imperdiet porta, accumsan vel orci. Curabitur elementum eleifend felis nec egestas. Vivamus sed dolor vitae turpis aliquet placerat. Vestibulum bibendum tellus eros, sed elementum neque venenatis et. Quisque sit amet venenatis ante, eu fringilla tellus. </p>
        </div>
      </div>
    </div>

    <Aluno :aluno="dadosAluno" />

    <Estagio :termo="termo" />

    <PlanoAtividades :termo="termo" />

    <Contratante :termo="termo" />

    <div class="flex align-items-end justify-content-end gap-2">
      <Button class="   p-button-success" @click="() => responderTermo('aprovar')">
        Aprovar
      </Button>
      <Button class="p-button-danger" @click="() => responderTermo('reprovar')">
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
