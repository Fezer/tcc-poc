<script setup lang="ts">
import { vMaska } from "maska";

// parse money to BRL currency and R$ prefix
const moneyMask = {
  preProcess: (value: string) => {
    return value.replace(/\D/g, "");
  },
  postProcess: (value: string) => {
    if (!value) return "";

    return "R$ " + value.replace(/(\d{1,2})$/, ",$1");
  },
};
</script>

<script lang="ts">
import { z } from "zod";
import ZodErrorsService from "../../../../../services/ZodErrorsService";
import validateStringDate from "../../../../utils/validateStringDate";

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
    finalStep: {
      type: Boolean,
      required: true,
    },
  },
  methods: {
    validateAndAdvance() {
      return this.advanceStep();
      this.errors = {};
      const validator = z.object({
        dataInicio: z.custom(validateStringDate, { message: "Data inválida" }),
        dataFinal: z.custom(validateStringDate, { message: "Data inválida" }),
        jornadaDiaria: z
          .string()
          .regex(/^\d{2}$/)
          .max(24),
        jornadaSemanal: z
          .string()
          .regex(/^\d{2}$/)
          .max(99),
        bolsaAuxilio: z.string().regex(/^\d{2}$/),
        auxilioTransporte: z.string().regex(/^\d{2}$/),
        coordenador: z.number(),
        // orientador: z.number(),
        departamentoOrientador: z.string().min(2),
        nomeSupervisor: z.string().min(2),
        telefoneSupervisor: z.string().min(2),
        atividades: z.string().min(20),
      });

      const result = validator.safeParse({ ...this.$data });

      if (!result.success) {
        console.log(result.error.issues);
        this.errors = result.error.issues.reduce((acc, issue) => {
          acc[issue.path[0]] = this.zodErrors[issue.code] || issue.message;
          return acc;
        }, {} as Record<string, string>);
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
      .getCountries()
      .then((data: Record<string, string>) => (this.zodErrors = data));
  },
  data() {
    return {
      zodErrors: null as Record<string, string> | null,
      errors: {} as Record<string, string>,

      dataInicio: "",
      dataFinal: "",
      jornadaDiaria: "",
      jornadaSemanal: "",
      bolsaAuxilio: "",
      auxilioTransporte: "",
      coordenador: "",
      orientador: "",
      departamentoOrientador: "",
      nomeSupervisor: "",
      telefoneSupervisor: "",
      atividades: "",
    };
  },
};
</script>

<style></style>

<template>
  <div class="grid mt-2">
    <h5 class="mb-0 p-2 mt-4">Dados do Estágio</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Periodo de Estágio</h5>

        <div class="formgrid grid">
          <div class="field col">
            <label for="dataInicio">Data de Início {{ dataInicio }}</label>
            <InputText
              id="dataInicio"
              type="text"
              v-maska
              data-maska="##/##/####"
              v-model="dataInicio"
              required
              :class="errors['dataInicio'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataInicio"] }}</small>
          </div>
          <div class="field col">
            <label for="dataFinal">Data de Termino</label>
            <InputText
              id="dataFinal"
              type="text"
              v-maska
              data-maska="##/##/####"
              v-model="dataFinal"
              :class="errors['dataFinal'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataFinal"] }}</small>
          </div>
          <div class="field col">
            <label for="jornadaDiaria">Jornada Diária (em horas)</label>
            <InputText
              id="jornadaDiaria"
              type="number"
              v-maska
              data-maska="##"
              v-model="jornadaDiaria"
              :class="errors['jornadaDiaria'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["jornadaDiaria"] }}</small>
          </div>
          <div class="field col">
            <label for="jornadaSemanal">Jornada Semanal (em horas)</label>
            <InputText
              id="jornadaSemanal"
              type="number"
              maxlength="2"
              v-maska
              data-maska="##"
              v-model="jornadaSemanal"
              :class="errors['jornadaSemanal'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["jornadaSemanal"] }}</small>
          </div>
        </div>
      </div>
      <div class="card p-fluid col-12">
        <h5>Valores da Bolsa Auxílio</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="bolsaAuxilio"
              >Valor bolsa auxílio {{ bolsaAuxilio }}</label
            >
            <InputText
              id="bolsaAuxilio"
              v-maska:[moneyMask]
              data-maska="0.99"
              data-maska-tokens="0:\d:multiple|9:\d:optional"
              v-model="bolsaAuxilio"
              :class="errors['bolsaAuxilio'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["bolsaAuxilio"] }}</small>
          </div>
          <div class="field col">
            <label for="auxilioTransporte"
              >Valor auxílio transporte (R$/diário)</label
            >
            <InputText
              id="auxilioTransporte"
              v-maska:[moneyMask]
              data-maska="0.99"
              data-maska-tokens="0:\d:multiple|9:\d:optional"
              v-model="auxilioTransporte"
              :class="errors['auxilioTransporte'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{
              errors["auxilioTransporte"]
            }}</small>
          </div>
        </div>
      </div>
      <div class="card p-fluid col-12">
        <h5>Plano de Atividades</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="coordenador">Coordenador do curso</label>
            <InputText
              id="coordenador"
              type="text"
              disabled
              value="Prof. Dr. Professor Professor"
              v-model="coordenador"
              :class="errors['coordenador'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["coordenador"] }}</small>
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="orientador">Professor Orientador na UFPR</label>
            <InputText
              id="orientador"
              type="text"
              v-model="orientador"
              :class="errors['orientador'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["orientador"] }}</small>
          </div>
          <div class="field col">
            <label for="departamentoOrientador"
              >Lotação/Departamento do Orientador</label
            >
            <InputText
              id="departamentoOrientador"
              type="text"
              v-model="departamentoOrientador"
              :class="errors['departamentoOrientador'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{
              errors["departamentoOrientador"]
            }}</small>
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeSupervisor"
              >Nome do Supervisor no Local de Estágio</label
            >
            <InputText
              id="nomeSupervisor"
              type="text"
              v-model="nomeSupervisor"
              :class="errors['nomeSupervisor'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["nomeSupervisor"] }}</small>
          </div>
          <div class="field col">
            <label for="telefoneSupervisor">Telefone do Supervisor</label>
            <InputText
              id="telefoneSupervisor"
              type="text"
              v-maska
              data-maska="(##) #####-####"
              v-model="telefoneSupervisor"
              :class="errors['telefoneSupervisor'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{
              errors["telefoneSupervisor"]
            }}</small>
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="atividades">Atividades a Serem Desenvolvidas</label>
            <Textarea
              id="atividades"
              type="text"
              v-model="atividades"
              :class="errors['atividades'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["atividades"] }}</small>
          </div>
        </div>
      </div>
      <div class="w-full flex justify-end gap-2">
        <Button
          @click="() => backStep()"
          label="Voltar"
          class="p-button-secondary"
          icon="pi pi-arrow-left"
        />
        <Button
          @click="validateAndAdvance"
          :label="finalStep ? 'Gerar termo' : 'Avançar'"
          class="p-button-success"
          icon="pi pi-arrow-right"
        />
      </div>
    </div>
  </div>
</template>
