<script lang="ts">
import { reactive, ref, defineComponent } from "vue";
import NovoEstagioService from "../../../../../services/NovoEstagioService";
import { TipoEstagio } from "../../../../types/NovoEstagio";

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
    dados,
  }: {
    advanceStep: Function;
    backStep: Function;
    dados: any;
  }) {
    const preenchimentoEstagio = useNovoEstagio();

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
          if (!preenchimentoEstagio.value.id) {
            const { id } = await novoEstagioService.criarNovoEstagio();

            preenchimentoEstagio.value.id = id;
          }
          const { id } = preenchimentoEstagio.value;

          await novoEstagioService
            .setTipoEstagio(id, tipoEstagio)
            .then((res) => {
              console.log(res);
            });

          await novoEstagioService.setEstagioUfpr(id, localEstagio === "UFPR");
        } catch (error) {
          console.log(error);

          return;
        }

        advanceStep({
          tipoEstagio,
          localEstagio,
        });
      } else {
        error.value = "Este campo é obrigatório";
      }
    };

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
        @click="backStep({ ...$data })"
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
