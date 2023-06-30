<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import AgenteService from "~~/services/AgenteService";

export default defineComponent({
  setup() {
    const toast = useToast();
    const state = reactive({
      nome: null,
      convenio: null,
    });

    const agenteService = new AgenteService();

    const handleRegisterAgentes = async () => {
      if (!state.nome) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      }

      await agenteService.criarAgente({ ...state }).then(() => {
        toast.add({
          severity: "success",
          summary: "Agente de Integração adicionado com sucesso",
          life: 3000,
        });
      });
    };

    return {
      state,
      handleRegisterAgentes,
    };
  },
});
</script>

<template>
  <div>
    <h2 class="mb-0 p-2 mt-4">Novo Agente de Integração</h2>

    <div class="col-12">
      <div class="p-fluid col-12">
        <h3>Dados de Registro</h3>
        <div class="flex-column gap-1 formgrid grid">
          <div class="field col">
            <label style="font-size: 20px" for="nome">Nome</label>
            <InputText id="nome" type="text" v-model="state.nome" />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="covenios">Convênios</label>
            <InputText id="convenios" type="text" v-model="state.convenio" />
          </div>
          <!-- <div class="field col">
              <label for="orgaoEmissor">Órgão Emissor</label>
              <InputText id="orgaoEmissor" type="text" disabled value="Teste" />
            </div> -->
        </div>
      </div>
      <div class="w-full flex justify-end mb-3">
        <Button
          @click="handleRegisterAgentes"
          :label="'Adicionar'"
          class="p-button-success"
        />
      </div>
      <div class="w-full flex justify-end gap-2">
        <NuxtLink to="/coafe/coafeAgentes">
          <Button
            label="Voltar"
            class="p-button-secondary"
            icon="pi pi-arrow-left"
          />
        </NuxtLink>
        <Button
          @click="handleRegisterAgentes"
          :label="'Cadastrar'"
          class="p-button-success"
          icon="pi pi-check"
        />
      </div>
    </div>
  </div>
</template>

<style></style>
