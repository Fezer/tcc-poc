<script lang="ts">
import aluno from './aluno.vue';
import Contratante from './contratante.vue';
import estagio from './estagio.vue';
import planoAtividades from './plano-atividades.vue';
import Status from './status.vue';

type TipoUsuario = "ALUNO" | "COE" | "COAFE" | "COORD";

export default {
  components: { Aluno: aluno, Estagio: estagio, PlanoAtividades: planoAtividades, Contratante, Status },
  data() {
    return {
      tipoUsuário: "ALUNO" as TipoUsuario;

      indeferimentoConfirm: false,
      cancelationConfirm: false,
      justificativa: ''
    };
  },
  methods: {
    async responderTermo(resposta) {
      this.indeferimentoConfirm = false;
      const respostaFormated = resposta === 'aprovar' ? 'aprovado' : 'reprovado';

      await fetch(`http://localhost:5000/termo/${resposta}/coafe/${useRoute().params.id}`, {
        method: 'PUT',
        body: JSON.stringify({
          justificativa: this.justificativa
        }),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(() => {
        console.log('Aprovado com sucesso');
        this.$toast.add({ severity: 'success', summary: `${respostaFormated.toUpperCase()}`, detail: `Termo ${respostaFormated} com sucesso`, life: 3000 });
      }).catch((err) => {
        console.error(err);
        this.$toast.add({ severity: 'error', summary: 'Ops!', detail: 'Tivemos um problema ao efetivar a análise do termo.', life: 3000 });
      });
    }
  }

};
</script>

<script setup lang="ts">
const route = useRoute();

const { id } = route.params;

const { data: termo, refresh } = await useFetch(
  `http://localhost:5000/termo/${id}`
);

// const { data: dadosAluno } = await useFetch(`http://localhost:5000/aluno/${termo?.grr}`);

function refreshData() {
  refresh();
}
</script>

<template>
  <div>
    <Toast />
    <small>Processos > Ver processo</small>
    <h2>Termo de Compromisso</h2>

    <Status
      :etapa="termo?.etapaFluxo"
      :status="termo?.statusTermo"
      :motivo="termo?.motivoIndeferimento"
    />

    <Aluno />

    <Estagio :termo="termo" />

    <PlanoAtividades :termo="termo" />

    <Contratante :termo="termo" />

    <div
      v-if="termo?.statusTermo === 'EmAprovacao' && tipoUsuário !== 'ALUNO'"
      class="flex align-items-end justify-content-end gap-2"
    >
      <Button
        class="p-button-success"
        @click="() => responderTermo('aprovar').then(() => refresh())"
      >
        Aprovar
      </Button>
      <Button
        class="p-button-danger"
        @click="() => (indeferimentoConfirm = true)"
      >
        Indeferir
      </Button>
    </div>

    <div
      v-else-if="tipoUsuario === 'ALUNO'"
      class="flex align-items-end justify-content-end gap-2"
    >
      <Button
        class="p-button-danger"
        @click="() => (indeferimentoConfirm = true)"
      >
        Cancelar termo
      </Button>
    </div>

    <Dialog
      :visible="cancelationConfirm"
      header="Confirmar cancelamento"
      style="min-width: 500px"
      :modal="true"
    >
      <template #footer>
        <Button
          label="Voltar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="cancelationConfirm = false"
        />
        <Button
          label="Cancelar"
          icon="pi pi-check"
          class="p-button-danger"
          autofocus
        />
      </template>
    </Dialog>

    <Dialog
      :visible="indeferimentoConfirm"
      header="Justificativa indeferimento"
      style="min-width: 500px"
      :modal="true"
    >
      <div class="flex align-items-center justify-content-center flex-column">
        <Textarea
          id="justificativa"
          v-model="justificativa"
          style="min-width: 100%"
          name="justificativa"
          cols="30"
          rows="10"
        />
      </div>
      <template #footer>
        <Button
          label="Cancelar"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="displayConfirmation = false"
        />
        <Button
          label="Indeferir"
          icon="pi pi-check"
          class="p-button-danger"
          autofocus
          @click="responderTermo('reprovar').then(() => refresh())"
        />
      </template>
    </Dialog>
  </div>
</template>

<style scoped>
.col-4 {
  margin-bottom: 1rem;
}

.text-box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 5px;
  margin-bottom: 1rem;
}
</style>
