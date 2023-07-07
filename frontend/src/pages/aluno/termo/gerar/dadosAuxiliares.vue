<script lang="ts">
import { useToast } from "primevue/usetoast";
import { defineComponent, reactive, ref } from "vue";
import { z } from "zod";
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
    dados?: Record<string, any>;
  }) {
    const toast = useToast();
    const zodErrors = new ZodErrorsService().getTranslatedErrors();

    const errors = ref({} as Record<string, string>);

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
      tipoVaga: null,
      banco: null,
      grupoSanguineo: null,
      numAgencia: null,
      numConta: null,
      nomeAgencia: null,
      cidadeAgencia: null,
      enderecoAgencia: null,
      bairroAgencia: null,
      certificadoMilitar: null,
      orgaoExpedicaoCertMilitar: null,
      serieCertMilitar: null,
    });

    const handleValidateAndAdvance = () => {
      console.log("validating..");
      const validator = z.object({
        banco: z.string().trim().min(1),
        numAgencia: z.string().trim().min(1),
        numConta: z.string().trim().min(1),
        nomeAgencia: z.string().trim().min(1),
        cidadeAgencia: z.string().trim().min(1),
        enderecoAgencia: z.string().trim().min(1),
        bairroAgencia: z.string().trim().min(1),
        tipoVaga: z.string().trim().min(1),
        grupoSanguineo: z.string().trim().min(1),
        certificadoMilitar: z.string().trim().min(1),
        orgaoExpedicaoCertMilitar: z.string().trim().min(1),
        serieCertMilitar: z.string().trim().min(1),
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

      advanceStep();
    };

    return {
      errors,
      handleValidateAndAdvance,
      state,
      backStep,
      tiposDeVaga,
      bancos,
      gruposSanguineos,
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
            <label for="formacao">Formação em andamento</label>
            <InputText id="formacao" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="nivelVaga">Peridiocidade total do curso</label>
            <InputText id="nivelVaga" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="rg">RG</label>
            <InputText id="rg" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="orgaoEmissor">Órgão Emissor</label>
            <InputText id="orgaoEmissor" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="uf">UF</label>
            <InputText id="uf" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="dataExpedicaoRG">Data de expedição</label>
            <InputText
              id="dataExpedicaoRG"
              type="text"
              disabled
              value="Teste"
            />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="tituloEleitoral">Título eleitoral</label>
            <InputText
              id="tituloEleitoral"
              type="text"
              disabled
              value="Teste"
            />
          </div>
          <div class="field col">
            <label for="dataEmissaoTitulo">Data de emissão</label>
            <InputMask
              id="dataEmissaoTitulo"
              type="text"
              disabled
              mask="99/99/9999"
              value="Teste"
            />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="zonaEleitoral">Título eleitoral</label>
            <InputText id="zonaEleitoral" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="secaoEleitora">Seção</label>
            <InputText id="secaoEleitora" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="estadoCivil">Estado Civil</label>
            <InputText id="estadoCivil" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="dependentes">Dependentes</label>
            <InputText id="dependentes" type="text" disabled value="Teste" />
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
            />
          </div>
          <div class="field col">
            <label for="corDaPele">Cor da Pele</label>
            <InputText id="corDaPele" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="sexo">Sexo</label>
            <InputText id="sexo" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="cidadeNascimento">Cidade de Nascimento</label>
            <InputText
              id="cidadeNascimento"
              type="text"
              disabled
              value="Teste"
            />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeDoPai">Nome do pai</label>
            <InputText id="nomeDoPai" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="nomeDaMae">Nome da mãe</label>
            <InputText id="nomeDaMae" type="text" disabled value="Teste" />
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nacionalidade">Nacionalidade</label>
            <InputText id="nacionalidade" type="text" disabled value="Teste" />
          </div>
          <div class="field col">
            <label for="dataChegadaPais">Data de chegada no pais</label>
            <InputMask
              id="dataChegadaPais"
              type="text"
              value="Teste"
              mask="99/99/9999"
            />
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
              v-model="state.banco"
              :class="{ 'p-invalid': errors['banco'] }"
            />
            <small class="text-rose-600">{{ errors["banco"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="numAgencia">Número da Agência</label>
            <InputText
              id="numAgencia"
              type="text"
              v-model="state.numAgencia"
              :class="{ 'p-invalid': errors['numAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["numAgencia"] }}</small>
          </div>

          <div class="field col">
            <label for="numConta">Número da Conta</label>
            <InputMask
              id="numConta"
              type="text"
              mask="999.999-9"
              v-model="state.numConta"
              :class="{ 'p-invalid': errors['numConta'] }"
            />
            <small class="text-rose-600">{{ errors["numConta"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="nomeAgencia">Nome da agência</label>
            <InputText
              id="nomeAgencia"
              type="text"
              v-model="state.nomeAgencia"
              :class="{ 'p-invalid': errors['nomeAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["nomeAgencia"] }}</small>
          </div>

          <div class="field col">
            <label for="cidadeAgencia">Cidade da agência</label>
            <InputText
              id="cidadeAgencia"
              type="text"
              v-model="state.cidadeAgencia"
              :class="{ 'p-invalid': errors['cidadeAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["cidadeAgencia"] }}</small>
          </div>
        </div>

        <div class="formgrid grid">
          <div class="field col">
            <label for="endAgencia">Endereço da agência</label>
            <InputText
              id="endAgencia"
              type="text"
              v-model="state.enderecoAgencia"
              :class="{ 'p-invalid': errors['enderecoAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["enderecoAgencia"] }}</small>
          </div>

          <div class="field col">
            <label for="bairroAgencia">Bairo da agência</label>
            <InputText
              id="bairroAgencia"
              type="text"
              v-model="state.bairroAgencia"
              :class="{ 'p-invalid': errors['bairroAgencia'] }"
            />
            <small class="text-rose-600">{{ errors["bairroAgencia"] }}</small>
          </div>
        </div>
      </div>
    </div>

    <div class="col-12">
      <div class="card p-fluid col-12">
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
              v-model="state.orgaoExpedicaoCertMilitar"
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
              v-model="state.serieCertMilitar"
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
