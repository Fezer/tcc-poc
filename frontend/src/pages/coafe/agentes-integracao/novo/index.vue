<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import AgenteService from "~~/services/AgenteService";

export default defineComponent({
  setup() {
    const toast = useToast();

    const state = reactive({
      id: null,
      nome: null,
      cnpj: null,
      telefone: null,
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
      } else if (!state.cnpj) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      } else if (!state.telefone) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      }
      try {
        const response = await agenteService
          .criarAgente(state.nome, state.cnpj, state.telefone)
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Agente de Integração adicionado com sucesso",
              life: 3000,
            });
          });
      } catch (e) {
        console.log(e);
      }
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
        <div class="flex flex-column gap-1 formgrid grid">
          <div class="field col">
            <label style="font-size: 20px" for="nome">Nome</label>
            <InputText id="nome" type="text" v-model="state.nome" />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="cnpj">CNPJ</label>
            <InputMask
              id="cnpj"
              v-model="state.cnpj"
              mask="99.999.999/9999-99"
              placeholder="##.###.###/####-##"
            />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="telefone">Telefone</label>
            <InputMask
              id="telefone"
              v-model="state.telefone"
              mask="(99)9 9999-9999"
              placeholder="(99) 9-9999-9999"
            />
          </div>
          <!-- <div class="field col">
              <label for="orgaoEmissor">Órgão Emissor</label>
              <InputText id="orgaoEmissor" type="text" disabled value="Teste" />
            </div> -->
        </div>
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
