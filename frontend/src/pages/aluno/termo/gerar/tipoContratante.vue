<script lang="ts">
import { z } from "zod";
import ZodErrorsService from "~~/services/ZodErrorsService";

export default {
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
  methods: {
    validateAndAdvanceStep() {
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

      const result = validator.safeParse(this.$data);

      if (!result.success) {
        this.$toast.add({
          severity: "error",
          summary: "Erro ao validar dados",
          detail: "Verifique os campos e tente novamente",
          life: 3000,
        });

        this.errors = result.error.issues.reduce(
          (acc: Record<string, string>, issue) => {
            const path = issue.path.join(".");
            const message = this.zodErrors[issue.code] || issue.message;

            return { ...acc, [path]: message };
          },
          {}
        );

        if (!this.cpfContratante || !this.cnpjContratante) {
          this.errors = {
            ...this.errors,
            documentoContratante: "Campo obrigatório",
          };
        }

        return;
      }

      this.advanceStep();
    },
  },
  zodErrorsService: null as ZodErrorsService | null,
  created() {
    this.zodErrorsService = new ZodErrorsService();
  },
  mounted() {
    this.zodErrorsService
      .getTranslatedErrors()
      .then((data: Record<string, string>) => (this.zodErrors = data));
  },
  data() {
    return {
      zodErrors: {} as Record<string, string>,
      errors: {} as Record<string, string>,

      tipoContratante: null as "PJ" | "PF" | null,
      tipos: [
        { label: "Pessoa Jurídica", value: "PJ" },
        { label: "Pessoa Física", value: "PF" },
      ],

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
    };
  },
};
</script>

<template>
  <div class="grid mt-2">
    <h5 class="mb-0 mt-4 ml-2">Contratante</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Tipo do Contratante</h5>
        <SelectButton
          v-model="tipoContratante"
          :options="tipos"
          optionLabel="label"
          optionValue="value"
        />
      </div>
    </div>

    <template v-if="!!tipoContratante">
      <div class="col-12">
        <div class="card p-fluid col-12">
          <h5>Dados do Contratante</h5>
          <div class="formgrid grid">
            <div class="field col">
              <label for="nomeContratante">Nome do Contratante</label>
              <InputText
                id="nomeContratante"
                type="text"
                v-model="nomeContratante"
                :class="{ 'p-invalid': errors['nomeContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["nomeContratante"]
              }}</small>
            </div>
            <div class="field col">
              <label for="telefoneContratante">Telefone do Contratante</label>
              <InputMask
                id="telefoneContratante"
                type="text"
                mask="(99) 9999-9999"
                v-model="telefoneContratante"
                :class="{ 'p-invalid': errors['telefoneContratante'] }"
              />
              <small class="text-rose-600">{{
                errors["telefoneContratante"]
              }}</small>
            </div>
          </div>
          <div class="formgrid grid" v-if="tipoContratante === 'PF'">
            <div class="field col-6">
              <label for="cpfContratante">CPF</label>
              <InputMask
                id="cpfContratante"
                type="text"
                mask="999.999.999-99"
                v-model="cpfContratante"
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
                v-model="cnpjContratante"
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
                v-model="enderecoContratante"
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
                v-model="cepContratante"
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
                v-model="cidadeContratante"
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
                v-model="estadoContratante"
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
                v-model="nomeSeguradora"
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
                v-model="apoliceSeguradora"
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
