<script lang="ts">
import { defineComponent, onMounted, reactive, ref } from "vue";

import { z } from "zod";
import NovoEstagioService from "../../../../../services/NovoEstagioService";
import ZodErrorsService from "../../../../../services/ZodErrorsService";
import validateStringDate from "../../../../utils/validateStringDate";
import { useToast } from "primevue/usetoast";
import { DadoEstagio } from "~~/src/types/NovoEstagio";
import dayjs from "dayjs";

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
  },

  setup({
    advanceStep,
    backStep,
    finalStep,
  }: {
    advanceStep: Function;
    backStep?: Function;
    finalStep?: boolean;
  }) {
    const { termo, setTermo } = useTermo();
    const toast = useToast();
    const errors = ref({} as Record<string, string>);

    const parseDateToMask = (date?: string) => {
      if (!date) return "";
      const [year, month, day] = date.split("-");
      return `${day}/${month}/${year}`;
    };

    onMounted(() => {
      if (termo) {
        console.log(termo.value);
        state.dataInicio = parseDateToMask(termo.value?.dataInicio);
        state.dataFinal = parseDateToMask(termo.value?.dataTermino);
        state.jornadaDiaria = termo.value?.jornadaDiaria;
        state.jornadaSemanal = termo.value?.jornadaSemanal;
        state.bolsaAuxilio = termo.value?.valorBolsa;
        state.auxilioTransporte = termo.value?.valorTransporte;
        // state.coordenador = termo.value?.coordenador;
        state.orientador = termo.value?.orientador?.id;
        state.nomeSupervisor = termo.value?.nomeSupervisor;
        state.telefoneSupervisor = termo.value?.telefoneSupervisor;
        state.cpfSupervisor = termo.value?.cpfSupervisor;
        state.formacaoSupervisor = termo.value?.formacaoSupervisor;
        state.atividades = termo.value?.descricaoAtividades;
      }

      handleLoadDocentes();
    });

    const zodErrors = new ZodErrorsService().getTranslatedErrors();
    const novoEstagioService = new NovoEstagioService();
    const docentes = ref([] as any[]);

    const { aluno } = useAluno();
    const handleLoadDocentes = async () => {
      console.log(aluno.value);

      const response = await $fetch(
        `http://localhost:5000/curso/${aluno.value?.idPrograma}/orientadores`
      );

      docentes.value = response;
    };

    const state = reactive({
      dataInicio: undefined as undefined | string,
      dataFinal: undefined as undefined | string,
      jornadaDiaria: undefined as number | undefined,
      jornadaSemanal: undefined as number | undefined,
      bolsaAuxilio: undefined as number | undefined,
      auxilioTransporte: undefined as number | undefined,
      orientador: undefined as string | undefined,
      nomeSupervisor: undefined as string | undefined,
      cpfSupervisor: undefined as string | undefined,
      formacaoSupervisor: undefined as string | undefined,
      telefoneSupervisor: undefined as string | undefined,
      atividades: undefined as string | undefined,
    });

    const validateAndAdvance = async () => {
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
        // orientador: z.number(),
        nomeSupervisor: z.string().min(2),
        telefoneSupervisor: z.string().min(2),
        formacaoSupervisor: z.string().min(2),
        cpfSupervisor: z.string().min(2),
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

      const { id } = termo.value;
      const {
        nomeSupervisor,
        cpfSupervisor,
        formacaoSupervisor,
        telefoneSupervisor,
      } = state;

      await novoEstagioService
        .setDadosEstagio(id, {
          dataInicio: dayjs(state.dataInicio).format("YYYY-MM-DD"),
          dataTermino: dayjs(state.dataFinal).format("YYYY-MM-DD"),
          jornadaDiaria: state.jornadaDiaria,
          jornadaSemanal: state.jornadaSemanal,
          valorBolsa: state.bolsaAuxilio,
          valorTransporte: state.auxilioTransporte,
        })
        .catch((err) => {
          console.log(err);
          toast.add({
            severity: "error",
            summary: "Erro na etapa de atualizar dados de estágio",
            detail: "Erro ao salvar dados do estágio",
            life: 3000,
          });
        });

      await novoEstagioService
        .setAtividadesEstagio(termo.value.id, {
          local: "Qualquer",
          descricaoAtividades: state.atividades,
          nomeSupervisor,
          telefoneSupervisor,
          cpfSupervisor,
          formacaoSupervisor,
        })
        .catch((err) => {
          console.log(err);
          toast.add({
            severity: "error",
            summary: "Erro na etapa de atualizar as atividades de estágio",
            detail: "Erro ao salvar dados do estágio",
            life: 3000,
          });
        });

      await novoEstagioService
        .setOrientador(termo.value.id, state.orientador)
        .catch((err) => {
          console.log(err);
          toast.add({
            severity: "error",
            summary: "Erro na etapa de atualizar o orientador",
            detail: "Erro ao salvar dados do estágio",
            life: 3000,
          });
        });

      advanceStep();
    };

    return {
      errors,
      validateAndAdvance,
      backStep,
      state,
      aluno,
      handleLoadDocentes,
      docentes,
      finalStep,
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
            <label for="dataInicio">Data de Início</label>
            <InputMask
              mask="99/99/9999"
              v-tooltip.top="
                'Inserir o período de início e término do estágio. Este termo de compromisso deve ser colocado na plataforma, contendo todas as assinaturas, com pelo menos 10 dias ANTES do início das atividades de estágio.'
              "
              v-model="state.dataInicio"
              :class="errors['dataInicio'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataInicio"] }}</small>
          </div>
          <div class="field col">
            <label for="dataFinal">Data de Termino</label>
            <InputMask
              mask="99/99/9999"
              v-model="state.dataFinal"
              :class="errors['dataFinal'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["dataFinal"] }}</small>
          </div>
          <div class="field col">
            <label for="jornadaDiaria">Jornada Diária</label>
            <InputNumber
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
              :max="30"
              :min="state.jornadaDiaria || 1"
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
            <label for="bolsaAuxilio">Valor bolsa auxílio </label>
            <InputNumber
              mode="currency"
              v-tooltip.top="
                'A contratante é responsável pelo pagamento de bolsa auxílio mensal para o estudante que realiza o estágio na modalidade não obrigatório (Lei Federal 11.788/2008).'
              "
              currency="BRL"
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
        <!-- <div class="formgrid grid">
          <div class="field col">
            <label for="coordenador">Coordenador do curso</label>
            <InputText
              id="coordenador"
              type="text"
              disabled
              :value="aluno?.coordenador"
            />
          </div>
        </div> -->
        <div class="formgrid grid">
          <div class="field col">
            <label for="orientador">Professor Orientador na UFPR</label>
            <Dropdown
              filter
              :options="docentes"
              optionLabel="nome"
              optionValue="id"
              placeholder="Selecione orientador(a)"
              :filter-fields="['nome']"
              v-model="state.orientador"
              :class="{ 'p-invalid': errors['orientador'] }"
            />
            <small class="text-rose-500">{{ errors["orientador"] }}</small>
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
            <label for="nomeSupervisor"
              >CPF do Supervisor no Local de Estágio</label
            >
            <InputMask
              mask="999.999.999-99"
              v-model="state.cpfSupervisor"
              :class="errors['cpfSupervisor'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{ errors["cpfSupervisor"] }}</small>
          </div>
          <div class="field col">
            <label for="telefoneSupervisor"
              >Formação do Supervisor no Local de Estágio</label
            >
            <InputText
              id="formacaoSupervisor"
              type="text"
              v-model="state.formacaoSupervisor"
              :class="errors['formacaoSupervisor'] && 'p-invalid'"
            />
            <small class="text-rose-500">{{
              errors["formacaoSupervisor"]
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

<style></style>
