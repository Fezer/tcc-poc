<script lang="ts">
import { useToast } from "primevue/usetoast";
import { reactive, defineComponent, onMounted } from "vue";
import SeguradoraService from "~~/services/SeguradoraService";
import { useRoute } from "vue-router";
import { stat } from "fs";
export default defineComponent({
  async setup() {
    const toast = useToast();
    const route = useRoute();
    const id = route.query.id;
    const state = reactive({
      id: null,
      nome: null,
      ativa: null,
      seguradoraUfpr: null,
    });
    onMounted(() => {
      console.log(seguradora.value.nome);
      state.id = seguradora.value.id;
      state.nome = seguradora.value.nome;
      state.ativa = seguradora.value.ativa;
      state.seguradoraUfpr = seguradora.value.seguradoraUfpr;
    });
    const { data: seguradora, refresh } = await useFetch(
      `http://localhost:5000/seguradora/${id}`
    );

    const seguradoraService = new SeguradoraService();
    const handleUpdateSeguradora = async () => {
      if (!state.nome) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      }
      try {
        const response = await seguradoraService
          .atualizaSeguradora(id, state.nome, state.ativa)
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Seguradora atualizada com sucesso",
              life: 3000,
            });
          });
      } catch (e) {
        console.log(e);
      }
      refresh();
    };
    return {
      seguradora,
      state,
      handleUpdateSeguradora,
    };
  },
});
</script>

<template>
  <div>
    <h5 class="mb-0 p-2 mt-4">Atualizar a Seguradora {{ state.nome }}</h5>

    <div class="col-12">
      <div class="card flex-column p-fluid col-12">
        <div class="formgrid grid flex flex-row justify-content-between gap-10">
          <div class="field col">
            <label for="nome">Nome</label>
            <InputText id="nome" type="text" v-model="state.nome" />
          </div>
          <div class="field row" style="align-content: center">
            <label for="seguradoraUfpr" class="flex flex-direction col"
              >Seguradora Ufpr</label
            >
            <InputSwitch
              id="seguradoraUfpr"
              v-model="state.seguradoraUfpr"
              :binary="true"
              disabled
            />
          </div>
          <div class="field col">
            <label for="ativa" class="flex flex-direction col">Ativo</label>
            <InputSwitch id="ativa" v-model="state.ativa" :binary="true" />
          </div>
        </div>
      </div>
      <div class="w-full flex justify-end gap-2 p">
        <NuxtLink :to="`seguradoraVisualizar?id=${state.id}`">
          <Button
            label="Voltar"
            class="p-button-secondary"
            icon="pi pi-arrow-left"
          />
        </NuxtLink>
        <Button
          @click="handleUpdateSeguradora"
          :label="'Atualizar'"
          class="p-button-success"
          icon="pi pi-check"
        />
      </div>
    </div>
  </div>
</template>

<style></style>
