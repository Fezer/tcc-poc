<script lang="ts">
import { reactive, ref, defineComponent, onMounted } from "vue";
import NovoEstagioService from "../../../../../services/NovoEstagioService";
import { TipoEstagio } from "../../../../types/NovoEstagio";
import { useToast } from "primevue/usetoast";

export default defineComponent({
  props: {
    advanceStep: {
      type: Function,
      required: true,
    },
    backStep: {
      type: Function,
      required: true,
    },
    dados: {
      type: Object,
    },
  },
  setup({
    advanceStep,
    backStep,
  }: {
    advanceStep: Function;
    backStep: Function;
    dados: any;
  }) {
    const toast = useToast();
    const { termo, setTermo } = useTermo();

    const locais = [
      { label: "Empresa Externa", value: "EXTERNO" },
      { label: "UFPR", value: "UFPR" },
    ];
    const tiposEstagio = [
      { label: "Obrigatório", value: "Obrigatorio" },
      { label: "Não obrigatório", value: "NaoObrigatorio" },
    ];
    const dadosTipoEstagio = reactive({
      tipoEstagio: null as TipoEstagio | null,
      localEstagio: null as "UFPR" | "EXTERNO" | null,
    });

    const novoEstagioService = new NovoEstagioService();

    const error = ref(null as string | null);

    const handleValidateAndAdvanceStep = async () => {
      const { localEstagio, tipoEstagio } = dadosTipoEstagio;

      if (localEstagio && tipoEstagio) {
        try {
          if (!termo?.value?.id) {
            const termo = await novoEstagioService.criarNovoEstagio();

            setTermo(termo)
          }
          const { id } = termo?.value;

          await novoEstagioService.setTipoEstagio(id, tipoEstagio);

          await novoEstagioService.setEstagioUfpr(id, localEstagio === "UFPR");
        } catch (error) {
          toast.add({
            severity: "error",
            summary: error,
            detail: "Erro ao salvar dados",
            life: 3000,
          });
          console.error(error);
          return;
        }

        setTermo({
          ...termo.value,
          tipoEstagio,
          estagioUfpr: localEstagio === "UFPR",
        });

        advanceStep();
      } else {
        error.value = "Este campo é obrigatório";
      }
    };

    onMounted(() => {
      if (termo) {
        dadosTipoEstagio.localEstagio = termo.value?.estagioUfpr
          ? "UFPR"
          : termo.value?.estagioUfpr === false && "EXTERNO";
        dadosTipoEstagio.tipoEstagio = termo.value?.tipoEstagio;
      }
    });

    return {
      dadosTipoEstagio,
      tiposEstagio,
      locais,
      error,
      handleValidateAndAdvanceStep,
      backStep,
    };
  },
});
</script>

<template>
  <div class="grid mt-2">
    <Toast />
    <h5 class="mb-0 mt-4 ml-2">Tipo do Estágio</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Obrigatoriedade do estágio</h5>
        <SelectButton
          v-model="dadosTipoEstagio.tipoEstagio"
          :options="tiposEstagio"
          optionLabel="label"
          optionValue="value"
          :class="{ 'p-invalid': error }"
        />

        <small class="text-rose-600">{{ error }}</small>
      </div>
      <div class="card p-fluid col-12">
        <h5>Local do Estágio</h5>
        <SelectButton
          v-model="dadosTipoEstagio.localEstagio"
          :options="locais"
          optionLabel="label"
          optionValue="value"
          :class="{ 'p-invalid': error }"
        />
        <small class="text-rose-600">{{ error }}</small>
      </div>
    </div>
    <div class="w-full flex justify-end gap-2">
      <Button
        @click="backStep()"
        label="Voltar"
        class="p-button-secondary"
        icon="pi pi-arrow-left"
      />
      <Button
        @click="handleValidateAndAdvanceStep"
        label="Avançar"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<style></style>
