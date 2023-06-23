<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive, ref, onMounted } from "vue";
import { z } from "zod";
import ZodErrorsService from "../../../../../services/ZodErrorsService";
import AlunoService from "~~/services/AlunoService";
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
    dados: {
      type: Object,
    },
  },
  async setup({
    advanceStep,
    backStep,
    dados,
  }: {
    advanceStep: Function;
    backStep: Function;
    dados?: Record<string, any>;
  }) {
    const toast = useToast();
    const zodErrors = new ZodErrorsService().getTranslatedErrors();

    const alunoService = new AlunoService();

    const { aluno } = useAluno();

    const errors = ref({} as Record<string, string>);

    const { auth } = useAuth();

    const grr = auth?.value?.identifier || "";
    const { data: alunoSiga } = useFetch(`/siga/aluno?grr=${grr}`);

    const tiposDeVaga = ref([
      {
        name: "Ampla Concorrência",
        code: "amplaConcorrencia",
      },
      {
        name: "Negros",
        code: "negros",
      },
      {
        name: "PCD",
        code: "pcd",
      },
    ]);

    const bancos = ref([
      {
        name: "Banco 341 - ITAU",
      },
      {
        name: "Banco 033 - Santander",
      },
      {
        name: "Banco 001 - Banco do Brasil",
      },
      {
        name: "Banco 104 - Caixa Econômica Federal",
      },
      {
        name: "Banco 237 - Bradesco",
      },
    ]);

    const gruposSanguineos = ref([
      {
        name: "A+",
      },
      {
        name: "A-",
      },
      {
        name: "B+",
      },
      {
        name: "B-",
      },
      {
        name: "AB+",
      },
      {
        name: "AB-",
      },
      {
        name: "O+",
      },
      {
        name: "O-",
      },
    ]);

    const state = reactive({
      estadoCivil: null,
      dependente: null,
      grupoSanguineo: null,
      dataChegada: null,
      dataExpedicao: null,
      tituloEleitoral: null,
      zona: null,
      secao: null,
      certificadoMilitar: null,
      serie: null,
      nomePai: null,
      dependentes: 1,
      corDaPele: null,
      sexo: null,
      nomeMae: null,
      nacionalidade: null,
      orgaoEmissor: null,
      uf: null,
      orgaoDeExpedicao: null,
      estadoNascimento: null,
      cidadeNascimento: null,
      corRaca: null,
      tipoVaga: null,
      dataEmissaoTitulo: null,
      // tipoVaga: null,
      // banco: null
    });

    const dadosBancarios = reactive({
      banco: null,
      numeroDaAgencia: null,
      numeroDaConta: null,
      nomeDaAgencia: null,
      cidadeDaAgencia: null,
      enderecoDaAgencia: null,
      bairroDaAgencia: null,
    });

    const handleValidateAndAdvance = async () => {
      console.log("validating..");
      const validator = z.object({
        estadoCivil: z.string().min(1),
        grupoSanguineo: z.string().min(1),
        // dataChegada: z.string(),
        dataExpedicao: z.date(),
        tituloEleitoral: z.string().min(1),
        zona: z.string().min(1).or(z.number().min(0)),
        secao: z.string().min(1).or(z.number().min(0)),
        certificadoMilitar: z.string().min(1),
        serie: z.string().min(1),
        nomePai: z.string().min(1),
        dependentes: z.number().min(0),
        corRaca: z.string().min(1),
        sexo: z.string().min(1),
        nomeMae: z.string().min(1),
        nacionalidade: z.string().min(1),
        orgaoEmissor: z.string().min(1),
        uf: z.string().min(1),
        orgaoDeExpedicao: z.string().min(1),
        estadoNascimento: z.string().min(1),
        cidadeNascimento: z.string().min(1),
        tipoVaga: z.string().min(1),
        dataEmissaoTitulo: z.date(),
        banco: z.string().min(1),
        numeroDaAgencia: z.number().min(1),
        numeroDaConta: z.number().min(1),
        nomeDaAgencia: z.string().min(1),
        cidadeDaAgencia: z.string().min(1),
        enderecoDaAgencia: z.string().min(1),
        bairroDaAgencia: z.string().min(1),
      });
      const result = validator.safeParse({
        ...aluno?.dadosAuxiliares,
        ...state,
        ...dadosBancarios,
      });

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
        await alunoService
          .atualizaDadosAuxiliares(grr, {
            ...state,
            dataExpedicao: dayjs(state.dataExpedicao),
            dataEmissaoTitulo: dayjs(state.dataEmissaoTitulo),
            dataDeChegadaNoPais:
              state?.dataChegada && dayjs(state?.dataChegada),
          })
          .then(() => {
            toast.add({
              severity: "success",
              summary: "Sucesso",
              detail: "Dados atualizados com sucesso",
              life: 3000,
            });
          });

        await alunoService.criaDadosBancarios(grr, dadosBancarios).then(() => {
          toast.add({
            severity: "success",
            summary: "Sucesso",
            detail: "Dados bancários atualizados com sucesso",
            life: 3000,
          });
        });

        advanceStep();
      } catch (err) {
        toast.add({
          severity: "error",
          summary: "Erro",
          detail: "Erro ao atualizar dados",
          life: 3000,
        });

        console.error(err);
      }
    };

    console.log(aluno?.dadosAuxiliares?.nacionalidade);

    onMounted(() => {
      const dadosAuxiliares = aluno?.value?.dadosAuxiliares;
      if (aluno && dadosAuxiliares) {
        for (let key in aluno?.value?.dadosAuxiliares) {
          state[key] = aluno?.value?.dadosAuxiliares[key];
        }

        state.dataChegada = dadosAuxiliares?.dataChegada
          ? dayjs(dadosAuxiliares?.dataDeChegadaNoPais).toDate()
          : null;
        state.dataExpedicao = dadosAuxiliares?.dataExpedicao
          ? dayjs(dadosAuxiliares?.dataExpedicao).toDate()
          : null;
        state.dataEmissaoTitulo = dadosAuxiliares?.dataEmissaoTitulo
          ? dayjs(dadosAuxiliares?.dataEmissaoTitulo).toDate()
          : null;
      }

      if (aluno?.value?.dadosBancarios) {
        for (let key in aluno?.value?.dadosBancarios) {
          dadosBancarios[key] = aluno?.value?.dadosBancarios[key];
        }
      }
    });

    return {
      errors,
      handleValidateAndAdvance,
      state,
      backStep,
      tiposDeVaga,
      bancos,
      gruposSanguineos,
      aluno,
      dadosBancarios,
      alunoSiga,
    };
  },
});
</script>

<template>
  <div class="grid mt-2">
    <h5 class="mb-0 p-2 mt-4">Dados Auxiliares</h5>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Dados da vaga</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="tipoVaga">Tipo de Vaga</label>
            <Dropdown
              id="tipoVaga"
              type="text"
              :options="tiposDeVaga"
              optionLabel="name"
              optionValue="code"
              placeholder="Selecione o tipo da vaga"
              v-model="state.tipoVaga"
              :class="{ 'p-invalid': errors['tipoVaga'] }"
            />
            <small class="text-rose-600">{{ errors["tipoVaga"] }}</small>
          </div>
        </div>
      </div>
    </div>

    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Dados do Aluno</h5>

        <div class="formgrid grid">
          <div class="field col">
            <label for="rg">RG</label>
            <InputText id="rg" type="text" disabled :value="aluno?.rg" />
          </div>
          <div class="field col">
            <label for="orgaoEmissor">Órgão Emissor RG</label>
            <InputText
              v-model="state.orgaoEmissor"
              :disabled="!!alunoSiga?.dadosAuxiliares?.orgaoEmissor"
              :class="{ 'p-invalid': errors['orgaoEmissor'] }"
            />
            <small class="text-rose-600">{{ errors["orgaoEmissor"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="uf">UF RG</label>
            <InputText
              v-model="state.uf"
              :disabled="!!alunoSiga?.dadosAuxiliares?.uf"
            />
          </div>
          <div class="field col">
            <label for="dataExpedicaoRG">Data de expedição RG</label>

            <Calendar
              showIcon
              dateFormat="dd/mm/yy"
              v-model="state.dataExpedicao"
              :disabled="!!alunoSiga?.dadosAuxiliares?.dataExpedicao"
              :class="{ 'p-invalid': errors['dataExpedicao'] }"
            />
            <small class="text-rose-600">{{ errors["dataExpedicao"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="tituloEleitoral">Título eleitoral</label>
            <InputText
              v-model="state.tituloEleitoral"
              :disabled="!!alunoSiga?.dadosAuxiliares?.tituloEleitoral"
              :class="{ 'p-invalid': errors['tituloEleitoral'] }"
            />
            <small class="text-rose-600">{{ errors["tituloEleitoral"] }}</small>
          </div>
          <div class="field col">
            <label for="dataEmissaoTitulo">Data de emissão</label>

            <Calendar
              showIcon
              dateFormat="dd/mm/yy"
              v-model="state.dataEmissaoTitulo"
              :disabled="!!alunoSiga?.dadosAuxiliares?.dataEmissaoTitulo"
              :class="{ 'p-invalid': errors['dataEmissaoTitulo'] }"
            />
            <small class="text-rose-600">{{
              errors["dataEmissaoTitulo"]
            }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="zonaEleitoral">Zona</label>
            <InputText
              v-model="state.zona"
              :disabled="!!alunoSiga?.dadosAuxiliares?.zona"
              :class="{ 'p-invalid': errors['zona'] }"
            />
            <small class="text-rose-600">{{ errors["zona"] }}</small>
          </div>
          <div class="field col">
            <label for="secaoEleitora">Seção</label>
            <InputText
              v-model="state.secao"
              :disabled="!!alunoSiga?.dadosAuxiliares?.secao"
              :class="{ 'p-invalid': errors['secao'] }"
            />
            <small class="text-rose-600">{{ errors["secao"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="estadoCivil">Estado Civil</label>
            <InputText
              v-model="state.estadoCivil"
              :disabled="!!alunoSiga?.dadosAuxiliares?.estadoCivil"
              :class="{ 'p-invalid': errors['estadoCivil'] }"
            />
            <small class="text-rose-600">{{ errors["estadoCivil"] }}</small>
          </div>
          <div class="field col">
            <label for="dependentes">Dependentes</label>
            <InputNumber
              v-model="state.dependentes"
              :disabled="!!alunoSiga?.dadosAuxiliares?.dependentes"
              :class="{ 'p-invalid': errors['dependentes'] }"
            />
            <small class="text-rose-600">{{ errors["dependentes"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="grupoSanguineo">Grupo Sanguíneo</label>
            <Dropdown
              id="grupoSanguineo"
              :options="gruposSanguineos"
              optionLabel="name"
              optionValue="name"
              v-model="state.grupoSanguineo"
              :disabled="!!alunoSiga?.dadosAuxiliares?.grupoSanguineo"
              :class="{ 'p-invalid': errors['grupoSanguineo'] }"
            />
            <small class="text-rose-600">{{ errors["grupoSanguineo"] }}</small>
          </div>
          <div class="field col">
            <label for="corDaPele">Cor da Pele</label>
            <InputText
              id="corDaPele"
              type="text"
              v-model="state.corRaca"
              :disabled="!!alunoSiga?.dadosAuxiliares?.corRaca"
              :class="{ 'p-invalid': errors['corRaca'] }"
            />
            <small class="text-rose-600">{{ errors["corRaca"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="sexo">Sexo</label>
            <InputText
              id="sexo"
              type="text"
              v-model="state.sexo"
              :disabled="!!alunoSiga?.dadosAuxiliares?.sexo"
              :class="{ 'p-invalid': errors['sexo'] }"
            />
            <small class="text-rose-600">{{ errors["sexo"] }}</small>
          </div>
          <div class="field col">
            <label for="cidadeNascimento">Cidade de Nascimento</label>
            <InputText
              v-model="state.cidadeNascimento"
              :disabled="!!alunoSiga?.dadosAuxiliares?.cidadeNascimento"
              :class="{ 'p-invalid': errors['cidadeNascimento'] }"
            />
            <small class="text-rose-600">{{
              errors["cidadeNascimento"]
            }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeDoPai">Nome do pai</label>
            <InputText
              id="nomeDoPai"
              type="text"
              v-model="state.nomePai"
              :disabled="!!alunoSiga?.dadosAuxiliares?.nomePai"
              :class="{ 'p-invalid': errors['nomePai'] }"
            />
            <small class="text-rose-600">{{ errors["nomePai"] }}</small>
          </div>
          <div class="field col">
            <label for="nomeDaMae">Nome da mãe</label>
            <InputText
              id="nomeDaMae"
              type="text"
              v-model="state.nomeMae"
              :disabled="!!alunoSiga?.dadosAuxiliares?.nomeMae"
              :class="{ 'p-invalid': errors['nomeMae'] }"
            />
            <small class="text-rose-600">{{ errors["nomeMae"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nacionalidade">Nacionalidade</label>
            <InputText
              id="nacionalidade"
              type="text"
              disabled
              :value="aluno?.dadosAuxiliares?.nacionalidade"
            />
          </div>
          <div
            class="field col"
            v-if="
              aluno?.dadosAuxiliares?.nacionalidade?.trim() !== 'BRASILEIRO'
            "
          >
            <label for="dataChegadaPais">Data de chegada no pais</label>

            <Calendar
              showIcon
              dateFormat="dd/mm/yy"
              v-model="state.dataChegada"
              :disabled="!!alunoSiga?.dadosAuxiliares?.dataChegada"
              :class="{ 'p-invalid': errors['dataChegada'] }"
            />
            <small class="text-rose-600">{{ errors["dataChegada"] }}</small>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12">
      <div class="card p-fluid col-12">
        <h5>Dados bancários</h5>
        <div class="formgrid grid">
          <div class="field col">
            <label for="banco">Banco</label>
            <Dropdown
              id="banco"
              :options="bancos"
              optionLabel="name"
              optionValue="name"
              v-model="dadosBancarios.banco"
              :class="{ 'p-invalid': errors['banco'] }"
            />
            <small class="text-rose-600">{{ errors["banco"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="numeroDaAgencia">Número da Agência</label>
            <InputNumber
              id="numeroDaAgencia"
              v-model="dadosBancarios.numeroDaAgencia"
              :class="{ 'p-invalid': errors['numeroDaAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["numeroDaAgencia"] }}</small>
          </div>

          <div class="field col">
            <label for="numeroDaConta">Número da Conta</label>
            <InputNumber
              id="numeroDaConta"
              v-model="dadosBancarios.numeroDaConta"
              :class="{ 'p-invalid': errors['numeroDaConta'] }"
            />
            <small class="text-rose-600">{{ errors["numeroDaConta"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeDaAgencia">Nome da agência</label>
            <InputText
              id="nomeDaAgencia"
              type="text"
              v-model="dadosBancarios.nomeDaAgencia"
              :disabled="aluno?.dadosBancarios?.nomeDaAgencia"
              :class="{ 'p-invalid': errors['nomeDaAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["nomeDaAgencia"] }}</small>
          </div>

          <div class="field col">
            <label for="cidadeDaAgencia">Cidade da agência</label>
            <InputText
              id="cidadeDaAgencia"
              type="text"
              v-model="dadosBancarios.cidadeDaAgencia"
              :disabled="aluno?.dadosBancarios?.cidadeDaAgencia"
              :class="{ 'p-invalid': errors['cidadeDaAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["cidadeDaAgencia"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="enderecoDaAgencia">Endereço da agência</label>
            <InputText
              id="enderecoDaAgencia"
              type="text"
              v-model="dadosBancarios.enderecoDaAgencia"
              :class="{ 'p-invalid': errors['enderecoDaAgencia'] }"
            />
            <small class="text-rose-600">{{
              errors["enderecoDaAgencia"]
            }}</small>
          </div>

          <div class="field col">
            <label for="bairroDaAgencia">Bairo da agência</label>
            <InputText
              id="bairroDaAgencia"
              type="text"
              v-model="dadosBancarios.bairroDaAgencia"
              :class="{ 'p-invalid': errors['bairroDaAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["bairroDaAgencia"] }}</small>
          </div>
        </div>
      </div>
    </div>

    <div class="col-12">
      <div
        class="card p-fluid col-12"
        v-if="aluno?.dadosAuxiliares?.sexo === 'M'"
      >
        <h5>Certificado Militar</h5>
        <div class="formgrid grid">
          <div class="field col">
            <InputText
              id="certificadoMilitar"
              type="text"
              placeholder="Obrigatório para homens acima dos 18 anos"
              v-model="state.certificadoMilitar"
              :class="{ 'p-invalid': errors['certificadoMilitar'] }"
            />
            <small class="text-rose-600">{{
              errors["certificadoMilitar"]
            }}</small>
          </div>
        </div>
        <div class="formgrid grid">
          <div class="field col">
            <label for="orgaoExpedicaoCertMilitar">Órgão de Expedição</label>
            <InputText
              id="orgaoExpedicaoCertMilitar"
              type="text"
              v-model="state.orgaoDeExpedicao"
              :class="{ 'p-invalid': errors['orgaoExpedicaoCertMilitar'] }"
            />
            <small class="text-rose-600">{{
              errors["orgaoExpedicaoCertMilitar"]
            }}</small>
          </div>

          <div class="field col">
            <label for="serieCertMilitar">Série</label>
            <InputText
              id="serieCertMilitar"
              type="text"
              v-model="state.serie"
              :class="{ 'p-invalid': errors['serieCertMilitar'] }"
            />
            <small class="text-rose-600">{{
              errors["serieCertMilitar"]
            }}</small>
          </div>
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
        @click="handleValidateAndAdvance"
        label="Gerar termo"
        class="p-button-success"
        icon="pi pi-file"
      />
    </div>
  </div>
</template>

<style></style>
