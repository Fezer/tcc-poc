<script lang="ts">
import { useToast } from "primevue/usetoast";
import { reactive, defineComponent, onMounted, ref } from "vue";
import AgenteService from "~~/services/AgenteService";
import { useRoute } from "vue-router";
export default defineComponent({
  async setup() {
    const toast = useToast();
    const route = useRoute();
    const id = route.query.id;
    const state = reactive({
      id: null,
      nome: null,
      cnpj: null,
      telefone: null,
    });
    onMounted(() => {
      state.id = agente.value.id;
      state.nome = agente.value.nome;
      state.cnpj = agente.value.cnpj;
      state.telefone = agente.value.telefone;
    });
    const { data: agente, refresh } = await useFetch(
      `/agente-integrador/${id}`
    );

    const agenteService = new AgenteService();
    const handleUpdateAgentes = async () => {
      if (!state.nome) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      } else if (!state.cnpj) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      } else if (!state.telefone) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      }
      try {
        const response = await agenteService
          .atualizaAgente(id, state.nome, state.cnpj, state.telefone)
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Agente de Integração atualizado com sucesso",
              life: 3000,
            });
          });
      } catch (e) {
        console.error(e);
      }
      refresh();
    };
    return {
      agente,
      state,
      handleUpdateAgentes,
    };
  },
});
</script>

<template>
  <div>
    <h2 class="mb-0 p-2 mt-4">Editar</h2>

    <div class="col-12">
      <div class="p-fluid col-12">
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
        <NuxtLink
          :to="`/coafe/agentes-integracao/agenteVisualizar?id=${state.id}`"
        >
          <Button
            label="Voltar"
            class="p-button-secondary"
            icon="pi pi-arrow-left"
          />
        </NuxtLink>
        <Button
          @click="handleUpdateAgentes"
          :label="'Salvar'"
          class="p-button-success"
        />
      </div>
    </div>
  </div>
</template>

<style></style>
