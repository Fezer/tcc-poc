<script lang="ts">
import { useToast } from "primevue/usetoast";
import { reactive, defineComponent, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import ConvenioService from "~~/services/ConvenioService";

export default defineComponent({
  async setup() {
    const toast = useToast();
    const route = useRoute();
    const id = route.query.id;
    const state = reactive({
      id: null,
      idAgente: null,
      numero: null,
      descricao: null,
      dataInicio: null,
      dataFim: null,
    });
    onMounted(() => {
      const dataInicioFormatada = new Date(convenio.value.dataInicio);
      const diaInicio = dataInicioFormatada.getDate();
      const mesInicio = dataInicioFormatada.getMonth() + 1;
      const anoInicio = dataInicioFormatada.getFullYear();
      const dataIniciocompleta = `${diaInicio}/${
        mesInicio < 10 ? "0" + mesInicio : mesInicio
      }/${anoInicio}`;
      const dataFimFormatada = new Date(convenio.value.dataFim);
      const dia = dataFimFormatada.getDate();
      const mes = dataFimFormatada.getMonth() + 1;
      const ano = dataFimFormatada.getFullYear();
      const dataFimcompleta = `${dia}/${mes < 10 ? "0" + mes : mes}/${ano}`;

      state.id = convenio.value.id;
      state.idAgente = convenio.value.agenteIntegrador.id;
      state.numero = convenio.value.numero;
      state.descricao = convenio.value.descricao;
      state.dataInicio = dataInicioFormatada;
      state.dataFim = dataFimFormatada;
    });
    const { data: convenio, refresh } = await useFetch(`/convenio/${id}`);

    const convenioService = new ConvenioService();
    const handleUpdateAgentes = async () => {
      if (!state.numero) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      } else if (!state.descricao) {
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
          .atualizaConvenio(
            state.numero,
            state.descricao,
            state.dataInicio,
            state.dataFim,
            state.id,
            state.idAgente
          )
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Convênio atualizado com sucesso",
              life: 3000,
            });
          });
      } catch (e) {
        console.log(e);
      }
      refresh();
    };
    return {
      convenio,
      state,
      handleUpdateAgentes,
    };
  },
});
</script>

<template>
  <div>
    <h2 class="mb-0 p-2 mt-4">Editar Convênio</h2>

    <div class="col-12">
      <div class="p-fluid col-12">
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
            <label style="font-size: 20px" for="descricao">Descrição</label>
            <InputText id="descricao" type="text" v-model="state.descricao" />
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
          :to="`/coafe/agentes-integracao/agenteVisualizar?id=${state.idAgente}`"
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
