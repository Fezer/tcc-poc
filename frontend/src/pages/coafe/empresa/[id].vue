<template>
  <div>
    <h5 class="mb-0 p-2 mt-4">
      Contratante <strong>{{ contratante?.nome }}</strong>
    </h5>

    <div class="col-12">
      <div class="card flex-column p-fluid col-12">
        <div
          class="formgrid grid flex flex-column justify-content-between gap-10"
        >
          <div class="field col">
            <label for="nome">Nome</label>
            <InputText
              :disabled="true"
              id="nome"
              type="text"
              v-model="state.nome"
            />
          </div>

          <div class="field col">
            <label for="tipo" class="flex flex-direction col"
              >Tipo de Contratante</label
            >
            <SelectButton
              :disabled="!edicao.ativa"
              v-model="state.tipo"
              :options="tiposContratante"
              optionLabel="label"
              optionValue="value"
            />
          </div>

          <div v-if="state.cpf" class="field col">
            <label for="cpf">CPF</label>
            <InputText
              :disabled="!edicao.ativa"
              id="cpf"
              type="text"
              v-model="state.cpf"
            />
          </div>
          <div v-else="state.cnpj" class="field col">
            <label for="cnpj">CNPJ</label>
            <InputText
              :disabled="!edicao.ativa"
              id="cnpj"
              type="text"
              v-model="state.cnpj"
            />
          </div>
          <div class="field col">
            <label for="representanteEmpresa">Representante da Empresa</label>
            <InputText
              :disabled="!edicao.ativa"
              id="representanteEmpresa"
              type="text"
              v-model="state.representanteEmpresa"
            />
          </div>
          <div class="field col">
            <label for="telefone">Telefone</label>
            <InputText
              :disabled="!edicao.ativa"
              id="telefone"
              type="text"
              v-model="state.telefone"
            />
          </div>
        </div>
      </div>
      <div class="w-full flex justify-end gap-2 p">
        <NuxtLink :to="`/coafe/coafeEmpresas`">
          <Button
            label="Voltar"
            class="p-button-secondary"
            icon="pi pi-arrow-left"
          />
        </NuxtLink>
        <Button
          v-if="edicao.ativa"
          @click="desativarEdicao"
          :label="'Cancelar'"
          class="p-button-warning"
          icon="pi pi-undo"
        />
        <Button
          v-if="edicao.ativa"
          @click="handleUpdateContratante"
          :label="'Atualizar'"
          class="p-button-success"
          icon="pi pi-check"
        />
        <Button
          v-if="!edicao.ativa"
          @click="editar"
          :label="'Editar'"
          class="p-button-primary"
          icon="pi pi-file-edit"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive } from "vue";
import { useRoute } from "vue-router";
import ContratanteService from "~~/services/ContratanteService";
import Contratante from "~~/src/types/Contratante";

export default defineComponent({
  async setup() {
    const toast = useToast();
    const route = useRoute();
    const { id } = route.params;
    const { data: contratante } = await useFetch<Contratante>(
      `/contratante/${id}`
    );
    const edicao = reactive({ ativa: false }); // Usamos uma variável reativa para armazenar o estado de edição
    const tiposContratante = [
      { label: "Pessoa Física", value: "PessoaFisica" },
      { label: "Pessoa Jurídica", value: "PessoaJuridica" },
    ];
    const state = reactive<Contratante>({
      id: contratante.value?.id,
      nome: contratante.value?.nome,
      tipo: contratante.value?.tipo,
      representanteEmpresa: contratante.value?.representanteEmpresa,
      cpf: contratante.value?.cpf,
      cnpj: contratante.value?.cnpj,
      telefone: contratante.value?.telefone,
    });
    const contratanteService = new ContratanteService();
    const desativarEdicao = () => {
      edicao.ativa = false;
      state.id = contratante.value?.id;
      state.nome = contratante.value?.nome;
      state.tipo = contratante.value?.tipo;
      state.cpf = contratante.value?.cpf;
      state.cnpj = contratante.value?.cnpj;
      state.representanteEmpresa = contratante.value?.representanteEmpresa;
      state.telefone = contratante.value?.telefone;
    };
    const editar = () => {
      edicao.ativa = true; // Atualiza a variável reativa para habilitar a edição
    };

    const handleUpdateContratante = async () => {
      if (!contratante?.value) {
        return toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Os campos não podem ficar vazios",
          life: 3000,
        });
      }
      try {
        const response = await contratanteService.atualizaContratante(
          state?.id,
          state?.nome,
          state?.tipo,
          state?.cpf,
          state?.cnpj,
          state?.representanteEmpresa,
          state?.telefone
        );
        toast.add({
          severity: "success",
          summary: "Contratante atualizada com sucesso",
          life: 3000,
        });
        edicao.ativa = false; // Desabilita a edição após a atualização
      } catch (e) {
        toast.add({
          severity: "error",
          detail: e?.response?._data?.error || "Erro inesperado",
          summary: "Erro ao atualizar contratante",
          life: 3000,
        });
        console.log(e);
        edicao.ativa = false;
      }
    };

    return {
      contratante,
      handleUpdateContratante,
      tiposContratante,
      editar,
      desativarEdicao,
      edicao,
      state,
    };
  },
});
</script>
