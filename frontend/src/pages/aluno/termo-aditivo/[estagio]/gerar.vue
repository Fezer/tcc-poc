<script lang="ts">
import { defineComponent, onMounted, reactive, ref } from "vue";

import { z } from "zod";
import NovoEstagioService from "../../../../../services/NovoEstagioService";
import ZodErrorsService from "../../../../../services/ZodErrorsService";
import validateStringDate from "../../../../utils/validateStringDate";
import { useToast } from "primevue/usetoast";
import NovoEstagio, { DadoEstagio } from "~~/src/types/NovoEstagio";
import dayjs from "dayjs";
import AlunoService from "~~/services/AlunoService";

export default defineComponent({
  setup() {
    const route = useRoute();
    const router = useRouter();
    const toast = useToast();
    const errors = ref({} as Record<string, string>);

    const { termo } = useTermo();

    const parseDateToMask = (date?: string) => {
      if (!date) return "";
      const [year, month, day] = date.split("-");
      return `${day}/${month}/${year}`;
    };

    const zodErrors = new ZodErrorsService().getTranslatedErrors();
    const novoEstagioService = new NovoEstagioService();
    const alunoSerivce = new AlunoService();
    const docentes = ref([] as any[]);

    const { estagio: idEstagio } = route.params;

    const { data: estagio } = useFetch<NovoEstagio>(
      `http://localhost:5000/estagio/${idEstagio}`
    );

    const { aluno } = useAluno();
    const handleLoadDocentes = async () => {
      const response = await $fetch(
        `http://localhost:5000/curso/${aluno.value?.idPrograma}/orientadores`
      );

      docentes.value = response;
    };

    const locais = [
      { label: "Empresa Externa", value: "EXTERNO" },
      { label: "UFPR", value: "UFPR" },
    ];
    const tiposEstagio = [
      { label: "Obrigatório", value: "Obrigatorio" },
      { label: "Não obrigatório", value: "NaoObrigatorio" },
    ];

    const suspensaoEstagio = reactive({
      isSuspensao: false,

      dataFimSuspensao: null,
      dataInicioRetomada: null,
    });

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

    const tipoEstagio = reactive({
      tipoEstagio: null,
      localEstagio: null,
      estagioSeed: false,
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

      const suspensaoValidator = z.object({
        dataFimSuspensao: z.custom(validateStringDate, {
          message: "Data inválida",
        }),
        dataInicioRetomada: z.custom(validateStringDate, {
          message: "Data inválida",
        }),
      });

      const result = suspensaoEstagio?.isSuspensao
        ? suspensaoValidator.safeParse({ ...suspensaoEstagio })
        : validator.safeParse({ ...state });

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

      try {
        const grr = "GRR20200141";

        let termoAditivoID = termo?.value?.id;
        if (!termoAditivoID) {
          const { id: novoID } = await alunoSerivce.criarTermoAditivo(
            grr,
            idEstagio
          );

          termoAditivoID = novoID;
        }

        const dadosTermoAditivo = suspensaoEstagio?.isSuspensao
          ? {
              dataFimSuspensao: dayjs(suspensaoEstagio.dataFimSuspensao).format(
                "YYYY-MM-DD"
              ),
              dataInicioRetomada: dayjs(
                suspensaoEstagio.dataInicioRetomada
              ).format("YYYY-MM-DD"),
            }
          : {
              dataInicio: dayjs(state.dataInicio).format("YYYY-MM-DD"),
              dataTermino: dayjs(state.dataFinal).format("YYYY-MM-DD"),
              jornadaDiaria: state.jornadaDiaria,
              jornadaSemanal: state.jornadaSemanal,
              valorBolsa: state.bolsaAuxilio,
              valorTransporte: state.auxilioTransporte,
              dataFimSuspensao: null,
              dataInicioRetomada: null,
            };

        await novoEstagioService
          .setDadosEstagio(termoAditivoID, dadosTermoAditivo)
          .catch((err) => {
            console.log(err);
            toast.add({
              severity: "error",
              summary: "Erro na etapa de atualizar dados de estágio",
              detail: "Erro ao salvar dados do estágio",
              life: 3000,
            });
          });
        if (suspensaoEstagio?.isSuspensao) {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Termo aditivo gerado com sucesso",
            life: 3000,
          });
          router.push(`/aluno/termo-aditivo/${termoAditivoID}`);

          return;
        }

        const {
          nomeSupervisor,
          cpfSupervisor,
          formacaoSupervisor,
          telefoneSupervisor,
        } = state;

        await novoEstagioService
          .setAtividadesEstagio(termoAditivoID, {
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
          .setOrientador(termoAditivoID, state.orientador)
          .catch((err) => {
            console.log(err);
            toast.add({
              severity: "error",
              summary: "Erro na etapa de atualizar o orientador",
              detail: "Erro ao salvar dados do estágio",
              life: 3000,
            });
          });

        toast.add({
          severity: "success",
          summary: "Sucesso",
          detail: "Termo aditivo gerado com sucesso",
          life: 3000,
        });

        router.push(`/aluno/termo-aditivo/${termoAditivoID}`);
      } catch (err) {
        console.log(err);
        toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Erro ao gerar termo aditivo",
          life: 3000,
        });
      }
    };

    onMounted(() => {
      if (estagio) {
        console.log(estagio.value, "estagio");
        state.dataInicio = parseDate(estagio.value?.dataInicio);
        state.dataFinal = parseDate(estagio.value?.dataTermino);
        state.jornadaDiaria = estagio.value?.jornadaDiaria;
        state.jornadaSemanal = estagio.value?.jornadaSemanal;
        state.bolsaAuxilio = estagio.value?.valorBolsa;
        state.auxilioTransporte = estagio.value?.valorTransporte;
        state.orientador = estagio.value?.orientador?.id;
        state.nomeSupervisor = estagio.value?.planoDeAtividades?.nomeSupervisor;
        state.telefoneSupervisor =
          estagio.value?.planoDeAtividades?.telefoneSupervisor;
        state.cpfSupervisor = estagio.value?.planoDeAtividades?.cpfSupervisor;
        state.formacaoSupervisor =
          estagio.value?.planoDeAtividades?.formacaoSupervisor;
        state.atividades =
          estagio.value?.planoDeAtividades?.descricaoAtividades;

        tipoEstagio.localEstagio = estagio.value?.estagioUfpr
          ? "UFPR"
          : estagio.value?.estagioUfpr === false && "EXTERNO";
        tipoEstagio.tipoEstagio = estagio.value?.tipoEstagio;
      }

      if (termo) {
        console.log("termo", termo.value);
        state.dataInicio = termo.value?.dataInicio
          ? parseDateToMask(termo.value?.dataInicio)
          : state.dataInicio;
        state.dataFinal = termo.value?.dataTermino
          ? parseDateToMask(termo.value?.dataTermino)
          : state.dataFinal;
        state.jornadaDiaria = termo.value?.jornadaDiaria || state.jornadaDiaria;
        state.jornadaSemanal =
          termo.value?.jornadaSemanal || state.jornadaSemanal;
        state.bolsaAuxilio = termo.value?.valorBolsa || state.bolsaAuxilio;
        state.auxilioTransporte =
          termo.value?.valorTransporte || state.auxilioTransporte;
        state.orientador = termo.value?.orientador?.id || state.orientador;
        state.nomeSupervisor =
          termo.value?.planoDeAtividades?.nomeSupervisor ||
          state.nomeSupervisor;
        state.telefoneSupervisor =
          termo.value?.planoDeAtividades?.telefoneSupervisor ||
          state.telefoneSupervisor;
        state.cpfSupervisor =
          termo.value?.planoDeAtividades?.cpfSupervisor || state.cpfSupervisor;
        state.formacaoSupervisor =
          termo.value?.planoDeAtividades?.formacaoSupervisor ||
          state.formacaoSupervisor;
        state.atividades =
          termo.value?.planoDeAtividades?.descricaoAtividades ||
          state.atividades;

        tipoEstagio.tipoEstagio =
          termo.value?.estagio?.tipoEstagio || tipoEstagio.tipoEstagio;

        suspensaoEstagio.isSuspensao = !!termo.value?.dataFimSuspensao;
        if (suspensaoEstagio.isSuspensao) {
          suspensaoEstagio.dataFimSuspensao = parseDateToMask(
            termo.value?.dataFimSuspensao
          );

          suspensaoEstagio.dataInicioRetomada = parseDateToMask(
            termo.value?.dataInicioRetomada
          );
        }
      }

      handleLoadDocentes();
    });

    return {
      errors,
      validateAndAdvance,
      state,
      aluno,
      handleLoadDocentes,
      docentes,
      tipoEstagio,
      locais,
      tiposEstagio,
      suspensaoEstagio,
    };
  },
});
</script>

<template>
  <div class="grid">
    <h1 class="mb-0 p-2 mt-2">Gerar termo aditivo</h1>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Suspender estágio</h5>

        <InputSwitch
          v-model="suspensaoEstagio.isSuspensao"
          @change="
            () => {
              if (!suspensaoEstagio.isSuspensao) {
                suspensaoEstagio.dataFimSuspensao = '';
                suspensaoEstagio.dataInicioRetomada = '';
              }
            }
          "
        />

        <div v-if="suspensaoEstagio.isSuspensao" class="mt-4">
          <div class="formgrid grid">
            <div class="field col">
              <label for="dataInicio">Data de Suspensão</label>
              <InputMask
                mask="99/99/9999"
                v-model="suspensaoEstagio.dataFimSuspensao"
                :class="errors['dataFimSuspensao'] && 'p-invalid'"
              />
              <small class="text-rose-500">{{
                errors["dataFimSuspensao"]
              }}</small>
            </div>
            <div class="field col">
              <label for="dataFinal">Data de Retomada</label>
              <InputMask
                mask="99/99/9999"
                v-model="suspensaoEstagio.dataInicioRetomada"
                :class="errors['dataInicioRetomada'] && 'p-invalid'"
              />
              <small class="text-rose-500">{{
                errors["dataInicioRetomada"]
              }}</small>
            </div>
          </div>
        </div>
      </div>
    </div>

    <template v-if="!suspensaoEstagio?.isSuspensao">
      <div class="col-12">
        <div class="card p-fluid col-12">
          <h5>Obrigatoriedade do estágio</h5>
          <SelectButton
            v-model="tipoEstagio.tipoEstagio"
            :options="tiposEstagio"
            optionLabel="label"
            optionValue="value"
            :class="{ 'p-invalid': error }"
          />

          <small class="text-rose-600">{{ error }}</small>
        </div>
      </div>

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
              <small class="text-rose-500">{{
                errors["jornadaSemanal"]
              }}</small>
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
              <small class="text-rose-500">{{
                errors["nomeSupervisor"]
              }}</small>
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
      </div>
    </template>
    <div class="w-full flex justify-end gap-2">
      <NuxtLink to="/aluno">
        <Button
          label="Voltar"
          class="p-button-secondary"
          icon="pi pi-arrow-left"
        />
      </NuxtLink>
      <Button
        @click="validateAndAdvance"
        label="Gerar termo aditivo"
        class="p-button-success"
        icon="pi pi-arrow-right"
      />
    </div>
  </div>
</template>

<style></style>
