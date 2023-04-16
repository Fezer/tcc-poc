<script lang="ts">
import { defineComponent, reactive, ref } from "vue";

import { z } from "zod";
import NovoEstagioService from "../../../../../services/NovoEstagioService";
import ZodErrorsService from "../../../../../services/ZodErrorsService";
import validateStringDate from "../../../../utils/validateStringDate";
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
    finalStep: {
      type: Boolean,
      required: true,
    },
    dados: {
      type: Object,
    },
  },
  async setup({
    advanceStep,
    backStep,
    finalStep,
    data,
  }: {
    advanceStep: Function;
    backStep?: Function;
    finalStep?: boolean;
    data?: any;
  }) {
    const toast = useToast();
    const errors = ref({} as Record<string, string>);

    const zodErrors = new ZodErrorsService().getTranslatedErrors();
    const novoEstagioService = new NovoEstagioService();

    const state = reactive({
      dataInicio: undefined as undefined | string,
      dataFinal: undefined as undefined | string,
      jornadaDiaria: undefined as string | undefined,
      jornadaSemanal: undefined as string | undefined,
      bolsaAuxilio: undefined as string | undefined,
      auxilioTransporte: undefined as string | undefined,
      coordenador: undefined as string | undefined,
      orientador: undefined as string | undefined,
      departamentoOrientador: undefined as string | undefined,
      nomeSupervisor: undefined as string | undefined,
      telefoneSupervisor: undefined as string | undefined,
      atividades: undefined as string | undefined,
    });

    const validateAndAdvance = () => {
      // return this.advanceStep();
      errors.value = {};
      const validator = z.object({
        dataInicio: z.custom(validateStringDate, {
          message: "Data inválida",
        }),
        dataFinal: z.custom(validateStringDate, { message: "Data inválida" }),
        jornadaDiaria: z.number().min(1).max(24),
        jornadaSemanal: z.number().min(1).max(99),
        bolsaAuxilio: z.number(),
        auxilioTransporte: z.number(),
        coordenador: z.string(),
        // orientador: z.number(),
        departamentoOrientador: z.string().min(2),
        nomeSupervisor: z.string().min(2),
        telefoneSupervisor: z.string().min(2),
        atividades: z.string().min(50).max(700),
      });

      const result = validator.safeParse({ ...state });

      if (!result.success) {
        console.log(result.error.issues);
        toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Preencha todos os campos",
          life: 3000,
        });
        errors.value = result.error.issues.reduce((acc, issue) => {
          acc[issue.path[0]] = zodErrors[issue.code] || issue.message;
          return acc;
        }, {} as Record<string, string>);
        return;
      }

      advanceStep({
        ...state,
      });
    };

    const grr = "GRR20201212";

    const { data: aluno } = await useFetch(
      `http://localhost:5000/aluno/${grr}`
    );

    return {
      errors,
      validateAndAdvance,
      backStep,
      state,
      aluno,
    };
  },
});
</script>

<template>
  <div class="grid mt-2">
    <Toast />
    <h5 class="mb-0 p-2 mt-4">Dados do Estágio</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Periodo de Estágio</h5>

        <div class="formgrid grid">
          <div class="field col">
            <label for="dataInicio"
              >Data de Início {{ state.dataInicio }}</label
            >
            <InputMask
              id="dataInicio"
              type="text"
              mask="99/99/9999"
              v-tooltip.top="
                'Inserir o período de início e término do estágio. Este termo de compromisso deve ser colocado na plataforma, contendo todas as assinaturas, com pelo menos 10 dias ANTES do início das atividades de estágio.'
              "
              v-model="state.dataInicio"
              required
              :class="errors['dataInicio'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataInicio"] }}</small>
          </div>
          <div class="field col">
            <label for="dataFinal">Data de Termino</label>
            <InputMask
              id="dataFinal"
              type="text"
              mask="99/99/9999"
              v-model="state.dataFinal"
              :class="errors['dataFinal'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataFinal"] }}</small>
          </div>
          <div class="field col">
            <label for="jornadaDiaria">Jornada Diária</label>
            <InputNumber
              id="jornadaDiaria"
              type="number"
              :max="6"
              :min="1"
              v-tooltip.top="
                'Máximo de 4h para estágios de nível fundamental e especial. Máximo de 6h para estágios de nível médio e superior.\n (Art. 10 - Lei Federal 11.788/2008)'
              "
              suffix="h"
              v-model="state.jornadaDiaria"
              :class="errors['jornadaDiaria'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["jornadaDiaria"] }}</small>
          </div>
          <div class="field col">
            <label for="jornadaSemanal">Jornada Semanal</label>
            <InputNumber
              id="jornadaSemanal"
              type="number"
              maxlength="2"
              :max="6"
              :min="1"
              v-tooltip.top="
                'Máximo de 20h para estágios de nível fundamental e especial. Máximo de 30h para estágios de nível médio e superior. (Art. 10 - Lei Federal no. 11.788/2008).'
              "
              suffix="h"
              v-model="state.jornadaSemanal"
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
            <InputNumber
              mode="currency"
              v-tolltip.top="
                'A contratante é responsável pelo pagamento de bolsa auxílio mensal para o estudante que realiza o estágio na modalidade não obrigatório (Lei Federal 11.788/2008).'
              "
              currency="BRL"
              id="bolsaAuxilio"
              v-model="state.bolsaAuxilio"
              :class="errors['bolsaAuxilio'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["bolsaAuxilio"] }}</small>
          </div>
          <div class="field col">
            <label for="auxilioTransporte"
              >Valor auxílio transporte (R$/diário)</label
            >
            <InputNumber
              mode="currency"
              currency="BRL"
              v-tooltip.top="
                'A contratante é responsável pelo pagamento de auxílio transporte para o estudante que realiza o estágio na modalidade não obrigatório (Lei Federal 11.788/2008).'
              "
              id="auxilioTransporte"
              v-model="state.auxilioTransporte"
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
              :value="aluno?.coordenador"
              v-model="state.coordenador"
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
              v-model="state.orientador"
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
              v-model="state.departamentoOrientador"
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
              v-model="state.nomeSupervisor"
              :class="errors['nomeSupervisor'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["nomeSupervisor"] }}</small>
          </div>
          <div class="field col">
            <label for="telefoneSupervisor">Telefone do Supervisor</label>
            <InputMask
              id="telefoneSupervisor"
              type="text"
              mask="(99) 99999-9999"
              v-model="state.telefoneSupervisor"
              :class="errors['telefoneSupervisor'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{
              errors["telefoneSupervisor"]
            }}</small>
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <span>
              <label for="atividades">Atividades a Serem Desenvolvidas</label>
              <small class="ml-2"
                >50 - 700 caracteres ({{
                  state.atividades?.trim()?.length || 0
                }})</small
              >
            </span>
            <Textarea
              id="atividades"
              type="text"
              v-tooltip.top="
                'Inserir todas as atividades que serão realizadas durante o período de estágio. As atividades devem ser compatíveis com a área do curso do estagiário. (Art. 3 - Lei Federal no. 11.788/2008)(Art. 2 - Instrução Normativa no. 01/13-CEPE)'
              "
              v-model="state.atividades"
              :class="errors['atividades'] && 'p-invalid'"
              maxlength="700"
              class="h-32"
            />
            <small class="text-rose-500">{{ errors["atividades"] }}</small>
          </div>
        </div>
      </div>
      <div class="w-full flex justify-end gap-2">
        <Button
          @click="() => backStep({ ...state })"
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

<style></style>
