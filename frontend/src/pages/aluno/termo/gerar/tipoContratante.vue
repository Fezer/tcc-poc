<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive, ref, onMounted } from "vue";
import { z } from "zod";
import ContratanteService from "~~/services/ContratanteService";
import SeguradoraService from "~~/services/SeguradoraService";
import ApoliceService from "~~/services/ApoliceService";
import ZodErrorsService from "../../../../../services/ZodErrorsService";
import NovoEstagioService from "../../../../../services/NovoEstagioService";
import buscaContratante from "../../../../components/contratante/buscaContratante.vue";

export default defineComponent({
  components: { buscaContratante },
  props: {
    advanceStep: {
      type: Function,
      required: true,
    },
    backStep: {
      type: Function,
      required: true,
    },
  },
  setup({
    advanceStep,
    backStep,
  }: {
    advanceStep: Function;
    backStep: Function;
  }) {
    const toast = useToast();
    const errors = ref({} as Record<string, string>);
    const zodErrors = new ZodErrorsService().getTranslatedErrors();
    const contratanteService = new ContratanteService();
    const seguradoraService = new SeguradoraService();
    const apoliceService = new ApoliceService();
    const novoEstagioService = new NovoEstagioService();
    const { termo, setTermo } = useTermo();

    const contratanteLoading = ref(false);
    const contratantes = ref([]);

    const selectedContratante = ref(null);

    onMounted(async () => {
      console.log("mounted");

      if (termo) {
        const contratante = termo?.value?.contratante;
        const seguradora = termo?.value?.seguradora;
        const apolice = termo?.value?.apolice;

        console.log(contratante, seguradora, apolice);

        state.id = contratante?.id || null;
        state.tipoContratante = contratante?.tipo || null;
        state.nomeContratante = contratante?.nome || null;

        state.telefoneContratante = contratante?.telefone || null;
        state.cpfContratante = contratante?.cpf || null;
        state.cnpjContratante = contratante?.cnpj || null;
        state.enderecoContratante = contratante?.endereco?.logradouro || null;
        state.cepContratante = contratante?.endereco?.cep || null;
        state.cidadeContratante = contratante?.endereco?.cidade || null;
        state.estadoContratante = contratante?.endereco?.estado || null;
        state.representanteEmpresa = contratante?.representanteEmpresa || null;

        state.nomeSeguradora = seguradora?.nome || null;
        state.apoliceSeguradora = apolice?.numero || null;
      }

      await fetch(`/contratante/`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((res) => res.json())
        .then((res) => {
          contratantes.value = res;
        })
        .catch((err) => {
          console.log(err);
        });
    });

    const tipos = [
      { label: "Pessoa Jurídica", value: "PessoaJuridica" },
      { label: "Pessoa Física", value: "PessoaFisica" },
    ];
    const state = reactive({
      tipoContratante: null as "PessoaJuridica" | "PessoaFisica" | null,

      id: null as number | null,
      cadastrarContratante: false as boolean,

      nomeContratante: null as string | null,
      telefoneContratante: null as string | null,
      cpfContratante: null as string | null,
      cnpjContratante: null as string | null,
      representanteEmpresa: null as string | null,
      enderecoContratante: null as string | null,
      cepContratante: null as string | null,
      cidadeContratante: null as string | null,
      estadoContratante: null as string | null,
      nomeSeguradora: null as string | null,
      apoliceSeguradora: null as string | null,

      complemento: null as string | null,
      numero: null as number | null,
    });

    const handleToggleRegisterContratante = () => {
      state.cadastrarContratante = !state.cadastrarContratante;

      if (state.cadastrarContratante) {
        state.id = null;
      }
    };

    const validateAndAdvanceStep = async () => {
      // return advanceStep();

      const contratanteFields = state.id
        ? {}
        : {
            nomeContratante: z.string().nonempty(),
            telefoneContratante: z.string().nonempty(),
            enderecoContratante: z.string().nonempty(),
            cepContratante: z.string().nonempty(),
            cidadeContratante: z.string().nonempty(),
            estadoContratante: z.string().nonempty(),
            representanteEmpresa: z.string().nonempty(),

            numero: z.number().min(1),
          };

      const validator = z.object({
        tipoContratante: z.string().nonempty(),
        nomeSeguradora: z.string().nonempty(),
        apoliceSeguradora: z.number(),
        ...contratanteFields,
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
        let contratanteID =
          state.id && state.tipoContratante === "PessoaJuridica"
            ? state.id
            : "";
        if (!contratanteID) {
          const contratante = await contratanteService.criarContratante(
            {
              ...state,
              id: 0,
              nome: state.nomeContratante,
              cnpj: state.cnpjContratante,
              cpf: state.cpfContratante,
              telefone: state.telefoneContratante,
              tipo: state.tipoContratante,
              representanteEmpresa: state.representanteEmpresa,
            },
            termo.value.id
          );

          contratanteID = contratante.id;
          const endereco = await contratanteService.criarEnderecoContratante(
            contratanteID,
            {
              cep: state.cepContratante,
              cidade: state.cidadeContratante,
              uf: state.estadoContratante,
              rua: state.enderecoContratante,
              numero: state.numero,
              complemento: state.complemento,
            }
          );

          setTermo({
            ...termo.value,
            contratante: {
              ...contratante,
              endereco: endereco,
            },
          });
        }

        await novoEstagioService.setContratante(termo.value.id, contratanteID);

        const seguradora = await seguradoraService.criarSeguradora(
          {
            nome: state.nomeSeguradora,
          },
          termo.value.id
        );

        const apolice = await apoliceService.criarApolice(
          {
            dataInicio: new Date(),
            dataFim: new Date(),
            numero: parseInt(state.apoliceSeguradora),
          },
          seguradora
        );

        await novoEstagioService.setApolice(termo.value.id, apolice?.id);

        setTermo({
          ...termo.value,
          seguradora: seguradora,
          apolice: apolice,
        });

        advanceStep();
      } catch (err) {
        console.log(err);
        toast.add({
          severity: "error",
          summary: "Erro ao criar contratante",
          detail: err?.response?._data?.error || "Tente novamente",
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
      contratantes,
      handleToggleRegisterContratante,
      contratanteLoading,
      selectedContratante,
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
          @change="state.id = null"
        />
      </div>
    </div>

    <div
      class="card p-fluid col-12 m-2"
      v-if="state.tipoContratante === 'PessoaJuridica'"
    >
      <busca-contratante
        v-model="state.id"
        v-if="!state.cadastrarContratante"
      />
      <div class="flex justify-end w-full">
        <Button
          class="w-2 flex items-center justify-center mt-4"
          icon="pi-add"
          @click="handleToggleRegisterContratante"
        >
          {{
            state.cadastrarContratante
              ? "Buscar contratante"
              : "Cadastrar nova contratante"
          }}
        </Button>
      </div>
    </div>

    <template v-if="!!state.tipoContratante">
      <div class="col-12">
        <div
          class="card p-fluid col-12"
          v-if="
            state.cadastrarContratante ||
            state.tipoContratante === 'PessoaFisica'
          "
        >
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
          <div class="formgrid grid">
            <div
              class="field col-6"
              v-if="state.tipoContratante === 'PessoaFisica'"
            >
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

            <div class="field col-6" v-else>
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

            <div class="field col-6">
              <label for="representanteEmpresa"
                >Representante/Responsável pela empresa</label
              >
              <InputText
                id="representanteEmpresa"
                type="text"
                mask="99.999.999/9999-99"
                v-model="state.representanteEmpresa"
                :class="{ 'p-invalid': errors['representanteEmpresa'] }"
              />
              <small class="text-rose-600">{{
                errors["representanteEmpresa"]
              }}</small>
            </div>
          </div>
        </div>
      </div>

      <div
        class="col-12"
        v-if="
          state.cadastrarContratante || state.tipoContratante === 'PessoaFisica'
        "
      >
        <div class="card p-fluid col-12">
          <h5>Endereço Contratante</h5>
          <div class="formgrid grid">
            <div class="field col">
              <label for="enderecoContratante">Rua</label>
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
              <label for="numero">Número</label>
              <InputNumber
                id="numero"
                type="text"
                :min="0"
                v-model="state.numero"
                :class="{ 'p-invalid': errors['numero'] }"
              />
              <small class="text-rose-600">{{ errors["numero"] }}</small>
            </div>
          </div>
          <div class="formgrid grid">
            <div class="field col">
              <label for="complemento">Complemento (opcional)</label>
              <InputText
                id="complemento"
                type="text"
                v-model="state.complemento"
                :class="{ 'p-invalid': errors['complemento'] }"
              />
              <small class="text-rose-600">{{ errors["complemento"] }}</small>
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
              <InputNumber
                v-model="state.apoliceSeguradora"
                :class="{ 'p-invalid': errors['apoliceSeguradora'] }"
                max="9999999999"
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
