<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import SeguradoraService from "~~/services/SeguradoraService";
import InputSwitch from "primevue/inputswitch";

export default defineComponent({
  setup() {
    const toast = useToast();
    const state = reactive({
      nome: null,
      seguradoraUfpr: true,
      ativa: null,
    });

    const seguradoraService = new SeguradoraService();

    const handleRegisterSeguradora = async () => {
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
    <h5 class="mb-0 p-2 mt-4">Nova seguradora</h5>

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
          <!-- <div class="field col">
              <label for="orgaoEmissor">Órgão Emissor</label>
              <InputText id="orgaoEmissor" type="text" disabled value="Teste" />
            </div> -->
        </div>
      </div>
      <div class="w-full flex justify-end gap-2 p">
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
