<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import ApoliceService from "~~/services/ApoliceService";
import InputNumber from "primevue/inputnumber";
export default defineComponent({
  setup() {
    const toast = useToast();
    const route = useRoute();
    const id = route.query.id;
    const state = reactive({
      numero: null,
      dataInicio: null,
      dataFim: null,
    });
    const seg = reactive({
      id: null,
    });
    seg.id = id;
    const apoliceService = new ApoliceService();
    const handleRegisterApolices = async () => {
      if (!state.numero) {
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
        const response = await apoliceService
          .criarApolice(state, seg)
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Apólice adicionada com sucesso",
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
      handleRegisterApolices,
    };
  },
});
</script>

<template>
  <div>
    <h2 class="mb-0 p-2 mt-4">Nova Apôlice</h2>

    <div class="col-12">
      <div class="p-fluid col-12">
        <h3>Dados de Registro</h3>
        <div class="flex flex-column gap-1 formgrid grid">
          <div class="field col">
            <label style="font-size: 20px" for="numero">Número</label>
            <InputNumber
              v-model="state.numero"
              inputId="numero"
              :useGrouping="false"
            />
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
          @click="handleRegisterApolices"
          :label="'Adicionar'"
          class="p-button-success"
        />
      </div>
      <div class="w-full flex justify-end gap-2">
        <NuxtLink :to="`/coafe/seguradora/seguradoraVisualizar?id=${id}`">
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
