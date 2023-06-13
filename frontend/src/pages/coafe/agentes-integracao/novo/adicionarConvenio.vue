<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import ConvenioService from "~~/services/ConvenioService";

export default defineComponent({
  setup() {
    const toast = useToast();
    const route = useRoute();
    const id = route.query.id;
    const state = reactive({
      id: null,
      numero: null,
      descricao: null,
      dataInicio: null,
      dataFim: null,
    });
    const convenioService = new ConvenioService();
    const handleRegisterConvenios = async () => {
      if (!state.numero) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      } else if (!state.descricao) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      } else if (!state.dataInicio) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      } else if (!state.dataFim) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
      } else if (state.dataInicio > state.dataFim) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "A data incial precisa ser antes da Data final ",
          life: 3000,
        });
      }
      try {
        const response = await convenioService
          .criarConvenio(
            state.numero,
            state.descricao,
            state.dataInicio,
            state.dataFim,
            id
          )
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Convênio adicionado com sucesso",
              life: 3000,
            });
          });
      } catch (e) {
        console.log(e);
      }
    };
    return {
      state,
      id,
      handleRegisterConvenios,
    };
  },
});
</script>

<template>
  <div>
    <h2 class="mb-0 p-2 mt-4">Novo Convenio</h2>

    <div class="col-12">
      <div class="p-fluid col-12">
        <h3>Dados de Registro</h3>
        <div class="flex flex-column gap-1 formgrid grid">
          <div class="field col">
            <label style="font-size: 20px" for="nome">Numero</label>
            <InputNumber
              v-model="state.numero"
              inputId="numero"
              :useGrouping="false"
            />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="cnpj">Descrição</label>
            <InputText id="cnpj" type="text" v-model="state.descricao" />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="dataInicio">Data Inicial</label>
            <Calendar
              v-model="state.dataInicio"
              dateFormat="dd/mm/yy"
              inputId="dataInicio"
              showIcon
              showButtonBar
            />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="dataFim">Data Final</label>
            <Calendar
              v-model="state.dataFim"
              dateFormat="dd/mm/yy"
              inputId="dataFim"
              showIcon
              showButtonBar
            />
          </div>
          <!-- <div class="field col">
              <label for="orgaoEmissor">Órgão Emissor</label>
              <InputText id="orgaoEmissor" type="text" disabled value="Teste" />
            </div> -->
        </div>
      </div>
      <div class="w-full flex justify-end mb-3">
        <Button
          @click="handleRegisterConvenios"
          :label="'Adicionar'"
          class="p-button-success"
        />
      </div>
      <div class="w-full flex justify-end gap-2">
        <NuxtLink :to="`/coafe/agentes-integracao/agenteVisualizar?id=${id}`">
          <Button
            label="Voltar"
            class="p-button-secondary"
            icon="pi pi-arrow-left"
          />
        </NuxtLink>
      </div>
    </div>
  </div>
</template>

<style></style>
