<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive, ref, onMounted } from "vue";
import { z } from "zod";
import ContratanteService from "~~/services/ContratanteService";
import SeguradoraService from "~~/services/SeguradoraService";
import ApoliceService from "~~/services/ApoliceService";
import ZodErrorsService from "../../../../../services/ZodErrorsService";

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
    dados?: any;
  }) {
    const toast = useToast();
    const errors = ref({} as Record<string, string>);
    const zodErrors = new ZodErrorsService().getTranslatedErrors();
    const contratanteService = new ContratanteService();
    const seguradoraService = new SeguradoraService();
    const apoliceService = new ApoliceService();
    const { termo, setTermo } = useTermo();

    onMounted(() => {
      console.log("mounted");
      if (dados) {
        const {
          tipoContratante,
          nomeContratante,
          telefoneContratante,
          cpfContratante,
          cnpjContratante,
          enderecoContratante,
          cepContratante,
          cidadeContratante,
          estadoContratante,
          nomeSeguradora,
          apoliceSeguradora,
        } = dados;

        state.tipoContratante = tipoContratante;
        state.nomeContratante = nomeContratante;
        state.telefoneContratante = telefoneContratante;
        state.cpfContratante = cpfContratante;
        state.cnpjContratante = cnpjContratante;
        state.enderecoContratante = enderecoContratante;
        state.cepContratante = cepContratante;
        state.cidadeContratante = cidadeContratante;
        state.estadoContratante = estadoContratante;
        state.nomeSeguradora = nomeSeguradora;
        state.apoliceSeguradora = apoliceSeguradora;
      }
    });

    const tipos = [
      { label: "Pessoa Jurídica", value: "PessoaJuridica" },
      { label: "Pessoa Física", value: "PessoaFisica" },
    ];
    const state = reactive({
      tipoContratante: null as "PessoaJuridica" | "PessoaFisica" | null,

      nomeContratante: null as string | null,
      telefoneContratante: null as string | null,
      cpfContratante: null as string | null,
      cnpjContratante: null as string | null,
      enderecoContratante: null as string | null,
      cepContratante: null as string | null,
      cidadeContratante: null as string | null,
      estadoContratante: null as string | null,
      nomeSeguradora: null as string | null,
      apoliceSeguradora: null as string | null,
    });

    const validateAndAdvanceStep = async () => {
      // return advanceStep();
      const validator = z.object({
        tipoContratante: z.string().nonempty(),
        nomeContratante: z.string().nonempty(),
        telefoneContratante: z.string().nonempty(),
        enderecoContratante: z.string().nonempty(),
        cepContratante: z.string().nonempty(),
        cidadeContratante: z.string().nonempty(),
        estadoContratante: z.string().nonempty(),
        nomeSeguradora: z.string().nonempty(),
        apoliceSeguradora: z.string().nonempty(),
      });

      const result = validator.safeParse({ ...state });

      if (!result.success) {
        toast.add({
          severity: "error",
          summary: "Erro ao validar dados",
          detail: "Verifique os campos e tente novamente",
          life: 3000,
        });

        errors.value = result.error.issues.reduce(
          (acc: Record<string, string>, issue) => {
            const path = issue.path.join(".");
            const message = zodErrors[issue.code] || issue.message;

            return { ...acc, [path]: message };
          },
          {}
        );

        if (!state.cpfContratante || !state.cnpjContratante) {
          errors.value = {
            ...errors.value,
            documentoContratante: "Campo obrigatório",
          };
        }

        return;
      }

      try {
        const { id: contratanteID } = await contratanteService.criarContratante(
          {
            ...state,
            id: 0,
            nome: state.nomeContratante,
            cnpj: state.cnpjContratante,
            cpf: state.cpfContratante,
            cep: state.cepContratante,
            cidade: state.cidadeContratante,
            estado: state.estadoContratante,
            endereco: state.enderecoContratante,
            telefone: state.telefoneContratante,
            tipo: state.tipoContratante,
          },
          termo.value.id
        );

        const seguradora = await seguradoraService.criarSeguradora(
          {
            id: 0,
            nome: state.nomeSeguradora,
          },
          termo.value.id
        );

        const apolice = await apoliceService.criarApolice(
          {
            id: 0,
            numero: parseInt(state.apoliceSeguradora),
          },
          seguradora,
          termo.value.id
        );

        console.log(contratanteID, seguradora, apolice);

        advanceStep();
      } catch (err) {
        console.log(err);
        toast.add({
          severity: "error",
          summary: "Erro ao criar contratante",
          detail: err,
          life: 3000,
        });
      }
    };

    return {
      tipos,
      state,
      errors,
      validateAndAdvanceStep,
      backStep,
    };
  },
});
</script>

<template>
  <div class="grid mt-2">
    <h5 class="mb-0 mt-4 ml-2">Contratante</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Tipo do Contratante</h5>
        <SelectButton
          v-model="state.tipoContratante"
          :options="tipos"
          optionLabel="label"
          optionValue="value"
        />
      </div>
    </div>

    <template v-if="!!state.tipoContratante">
      <div class="col-12">
        <div class="card p-fluid col-12">
          <h5>Dados do Contratante</h5>
          <div class="formgrid grid">
            <div class="field col">
              <label for="nomeContratante">Nome do Contratante</label>
              <InputText
                id="nomeContratante"
                type="text"
                v-model="state.nomeContratante"
                :class="{ 'p-invalid': errors['nomeContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["nomeContratante"]
              }}</small>
            </div>
            <div class="field col">
              <label for="telefoneContratante">Telefone do Contratante</label>
              <InputMask
                mask="(99) 9999-9999"
                v-model="state.telefoneContratante"
                :class="{ 'p-invalid': errors['telefoneContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["telefoneContratante"]
              }}</small>
            </div>
          </div>
          <div
            class="formgrid grid"
            v-if="state.tipoContratante === 'PessoaFisica'"
          >
            <div class="field col-6">
              <label for="cpfContratante">CPF</label>
              <InputMask
                id="cpfContratante"
                type="text"
                mask="999.999.999-99"
                v-model="state.cpfContratante"
                :class="{ 'p-invalid': errors['documentoContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["documentoContratante"]
              }}</small>
            </div>
          </div>
          <div class="formgrid grid" v-else>
            <div class="field col-6">
              <label for="cnpjContratante">CNPJ</label>
              <InputMask
                id="cnpjContratante"
                type="text"
                mask="99.999.999/9999-99"
                v-model="state.cnpjContratante"
                :class="{ 'p-invalid': errors['documentoContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["documentoContratante"]
              }}</small>
            </div>
          </div>
        </div>
      </div>

      <div class="col-12">
        <div class="card p-fluid col-12">
          <h5>Endereço Contratante</h5>
          <div class="formgrid grid">
            <div class="field col">
              <label for="enderecoContratante">Endereço</label>
              <InputText
                id="enderecoContratante"
                type="text"
                v-model="state.enderecoContratante"
                :class="{ 'p-invalid': errors['enderecoContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["enderecoContratante"]
              }}</small>
            </div>
            <div class="field col">
              <label for="cepContratante">CEP</label>
              <InputMask
                id="cepContratante"
                type="text"
                mask="99.999-999"
                v-model="state.cepContratante"
                :class="{ 'p-invalid': errors['cepContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["cepContratante"]
              }}</small>
            </div>
          </div>
          <div class="formgrid grid">
            <div class="field col">
              <label for="cidadeContratante">Cidade</label>
              <InputText
                id="cidadeContratante"
                type="text"
                v-model="state.cidadeContratante"
                :class="{ 'p-invalid': errors['cidadeContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["cidadeContratante"]
              }}</small>
            </div>
            <div class="field col">
              <label for="estadoContratante">Estado</label>
              <InputText
                id="estadoContratante"
                type="text"
                v-model="state.estadoContratante"
                :class="{ 'p-invalid': errors['estadoContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["estadoContratante"]
              }}</small>
            </div>
          </div>
        </div>
      </div>

      <div class="col-12">
        <div class="card p-fluid col-12">
          <h5>Dados seguradora</h5>
          <div class="formgrid grid">
            <div class="field col">
              <label for="nomeSeguradora">Nome empresa seguradora</label>
              <InputText
                id="nomeSeguradora"
                type="text"
                v-model="state.nomeSeguradora"
                :class="{ 'p-invalid': errors['nomeSeguradora'] }"
              />
              <small class="text-rose-600">{{
                errors["nomeSeguradora"]
              }}</small>
            </div>
            <div class="field col">
              <label for="apoliceSeguradora">Número da Apólice</label>
              <InputMask
                id="apoliceSeguradora"
                type="text"
                mask="99.999-999"
                v-model="state.apoliceSeguradora"
                :class="{ 'p-invalid': errors['apoliceSeguradora'] }"
              />
              <small class="text-rose-600">{{
                errors["apoliceSeguradora"]
              }}</small>
            </div>
          </div>
        </div>
      </div>
    </template>

    <div class="w-full flex justify-end gap-2">
      <Button
        @click="backStep()"
        label="Voltar"
        class="p-button-secondary"
        icon="pi pi-arrow-left"
      />
      <Button
        @click="validateAndAdvanceStep"
        label="Avançar"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<style></style>
