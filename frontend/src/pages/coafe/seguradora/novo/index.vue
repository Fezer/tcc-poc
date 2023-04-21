<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import SeguradoraService from "~~/services/SeguradoraService";

export default defineComponent({
  setup() {
    const toast = useToast();
    const state = reactive({
      nome: null,
    });

    const seguradoraService = new SeguradoraService();

    const handleRegisterSeguradora = async () => {
      console.log("ofsasfa");
      if (!state.nome) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      }

      await seguradoraService.criarSeguradora({ ...state }).then(() => {
        toast.add({
          severity: "success",
          summary: "Seguradora criada com sucesso",
          life: 3000,
        });
      });
    };

    return {
      state,
      handleRegisterSeguradora,
    };
  },
});
</script>

<template>
  <div>
    <Toast />
    <h5 class="mb-0 p-2 mt-4">Nova seguradora</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <div class="formgrid grid">
          <div class="field col">
            <label for="nome">Nome</label>
            <InputText id="nome" type="text" v-model="state.nome" />
          </div>
          <!-- <div class="field col">
              <label for="orgaoEmissor">Órgão Emissor</label>
              <InputText id="orgaoEmissor" type="text" disabled value="Teste" />
            </div> -->
        </div>
      </div>

      <div class="w-full flex justify-end gap-2">
        <NuxtLink to="/coafe/coafeSeguradoras">
          <Button
            label="Voltar"
            class="p-button-secondary"
            icon="pi pi-arrow-left"
          />
        </NuxtLink>
        <Button
          @click="handleRegisterSeguradora"
          :label="'Cadastrar'"
          class="p-button-success"
          icon="pi pi-check"
        />
      </div>
    </div>
  </div>
</template>

<style></style>
