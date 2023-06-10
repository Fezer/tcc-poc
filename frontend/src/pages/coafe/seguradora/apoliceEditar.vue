<script lang="ts">
import { useToast } from "primevue/usetoast";
import { reactive, defineComponent, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import ApoliceService from "~~/services/ApoliceService";

export default defineComponent({
  async setup() {
    const toast = useToast();
    const route = useRoute();
    const id = route.query.id;
    const state = reactive({
      id: null,
      idSeguradora: null,
      numero: null,
      dataInicio: null,
      dataFim: null,
    });
    onMounted(() => {
      const dataInicioFormatada = new Date(apolice.value.dataInicio);
      const dataFimFormatada = new Date(apolice.value.dataFim);
      state.id = apolice.value.id;
      state.idSeguradora = apolice.value.seguradora.id;
      state.numero = apolice.value.numero;
      state.dataInicio = dataInicioFormatada;
      state.dataFim = dataFimFormatada;
    });
    const { data: apolice, refresh } = await useFetch(
      `http://localhost:5000/apolice/${id}`
    );

    const apoliceService = new ApoliceService();
    const handleUpdateApolices = async () => {
      if (!state.numero) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      } else if (!state.dataInicio) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      } else if (!state.dataFim) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      }
      try {
        const response = await apoliceService
          .atualizaApolice(
            state.numero,
            state.dataInicio,
            state.dataFim,
            state.id,
            state.idSeguradora
          )
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Apólice atualizada com sucesso",
              life: 3000,
            });
          });
      } catch (e) {
        console.log(e);
      }
      refresh();
    };
    return {
      apolice,
      state,
      handleUpdateApolices,
    };
  },
});
</script>

<template>
  <div>
    <h2 class="mb-0 p-2 mt-4">Editar Apólice</h2>
    <div class="col-12">
      <div class="p-fluid col-12">
        <div class="flex flex-column gap-1 formgrid grid">
          <div class="field col">
            <label style="font-size: 20px" for="numero">Numero</label>
            <InputText id="numero" type="text" v-model="state.numero" />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="dataInicio"
              >Data de Início</label
            >
            <Calendar
              v-model="state.dataInicio"
              dateFormat="dd/mm/yy"
              inputId="dataInicio"
              showIcon
              showButtonBar
            />
          </div>
          <div class="field col">
            <label style="font-size: 20px" for="dataFim">Data de Fim</label>
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
      <div class="w-full flex justify-end gap-2">
        <NuxtLink
          :to="`/coafe/seguradora/seguradoraVisualizar?id=${state.idSeguradora}`"
        >
          <Button
            label="Voltar"
            class="p-button-secondary"
            icon="pi pi-arrow-left"
          />
        </NuxtLink>
        <Button
          @click="handleUpdateApolices"
          :label="'Salvar'"
          class="p-button-success"
        />
      </div>
    </div>
  </div>
</template>

<style></style>
