<script lang="ts">
import dayjs from "dayjs";
import { defineComponent, onMounted, reactive } from "vue";
import AlunoService from "~~/services/AlunoService";
import { Aluno } from "../../types/Aluno";

export default defineComponent({
  props: {
    grrAluno: {
      type: String,
      required: false,
    },
    showDadosAuxiliares: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  setup({
    grrAluno,
    showDadosAuxiliares = false,
  }: {
    grrAluno: string;
    showDadosAuxiliares: boolean;
  }) {
    const { aluno, setAluno } = useAluno();
    const route = useRoute();

    const alunoService = new AlunoService();

    const { id } = route.params;

    const { auth } = useAuth();

    const grr = grrAluno || auth?.value?.identifier || "";

    const curso = reactive({});

    const handleFetchCurso = async (cursoID: string) => {
      if (aluno?.curso) return;
      const response = await alunoService.getCursoAlunoFromSiga(cursoID);
      curso.value = response;
    };

    const { data: dadosAluno } = useAsyncData<Aluno>("aluno", async () => {
      const response: Aluno = await alunoService.getAlunoFromSiga(grr);
      setAluno(response);

      await handleFetchCurso(response?.idPrograma);
      return response;
    });

    // parse da data para o formato dd/mm/yyyy e adiciona 3 horas devido ao fuso
    const parseDataNascimento = (data: string) => {
      if (!data) return "--/--/----";
      return dayjs(data).add(3, "hour").format("DD/MM/YYYY");
    };

    const getAge = (data: string) => {
      if (!data) return "--";
      return dayjs(new Date()).diff(new Date(data), "year");
    };

    return {
      aluno: dadosAluno,
      curso,
      dayjs,
      parseDataNascimento,
      getAge,
      showDadosAuxiliares,
    };
  },
});
</script>

<template>
  <div>
    <div class="card">
      <h5>Dados do Aluno</h5>

      <div class="grid">
        <div class="col-4">
          <strong>Nome Completo</strong>
          <p>{{ aluno?.nome }}</p>
        </div>
        <div class="col-4">
          <strong>Pessoa com Deficiência</strong>
          <p>{{ aluno?.isPcD ? "Sim" : "Não" }}</p>
        </div>
        <div class="col-4">
          <strong>Data de Nascimento</strong>
          <!-- get age -->
          <p>
            {{ parseDataNascimento(aluno?.dataNascimento) }}
            ({{ getAge(aluno?.dataNascimento) }}
            anos)
          </p>
        </div>
        <div class="col-4">
          <strong>Registro Geral (RG)</strong>
          <p>{{ aluno?.rg }}</p>
        </div>
        <div class="col-4">
          <strong>Matrícula</strong>
          <p>{{ aluno?.matricula }}</p>
        </div>

        <div class="col-4">
          <strong>Email</strong>
          <p>{{ aluno?.email }}</p>
        </div>

        <div class="col-4">
          <strong>Período Atual</strong>
          <p>{{ aluno?.periodoAtual }}</p>
        </div>

        <div class="col-4">
          <strong>Índice de Rendimento Acadêmico</strong>
          <p>{{ aluno?.ira }}</p>
        </div>

        <div class="col-4">
          <strong>Turno</strong>
          <p>{{ aluno?.turno }}</p>
        </div>

        <div class="col-6">
          <strong>Curso</strong>
          <p>{{ curso?.value?.nome }}</p>
        </div>
      </div>
    </div>
    <template v-if="showDadosAuxiliares">
      <div class="card">
        <h5>Dados Auxiliares</h5>
        <div class="grid">
          <div class="col-4">
            <strong>Nome Pai</strong>
            <p>{{ aluno?.dadosAuxiliares?.nomePai }}</p>
          </div>
          <div class="col-4">
            <strong>Estado Civil</strong>
            <p>{{ aluno?.dadosAuxiliares?.estadoCivil }}</p>
          </div>
          <div class="col-4">
            <strong>Dependentes</strong>
            <p>{{ aluno?.dadosAuxiliares?.dependentes }}</p>
          </div>
          <div class="col-4">
            <strong>Grupo Sanguíneo</strong>
            <p>{{ aluno?.dadosAuxiliares?.grupoSanguineo }}</p>
          </div>
          <div class="col-4">
            <strong>Cor da Pele</strong>
            <p>{{ aluno?.dadosAuxiliares?.corRaca }}</p>
          </div>
          <div class="col-4">
            <strong>Sexo</strong>
            <p>{{ aluno?.dadosAuxiliares?.sexo }}</p>
          </div>
          <div class="col-4">
            <strong>Nome Mãe</strong>
            <p>{{ aluno?.dadosAuxiliares?.nomeMae }}</p>
          </div>
          <div class="col-4">
            <strong>Nacionalidade</strong>
            <p>{{ aluno?.dadosAuxiliares?.nacionalidade }}</p>
          </div>
          <div
            class="col-4"
            v-if="aluno?.dadosAuxiliares?.nacionalidade?.trim() != 'BRASILEIRO'"
          >
            <strong>Data de Chegada no País</strong>
            <p>{{ parseDate(aluno?.dadosAuxiliares?.dataDeChegadaNoPais) }}</p>
          </div>
          <div class="col-4">
            <strong>Órgão Emissor</strong>
            <p>{{ aluno?.dadosAuxiliares?.orgaoEmissor }}</p>
          </div>
          <div class="col-4">
            <strong>UF</strong>
            <p>{{ aluno?.dadosAuxiliares?.uf }}</p>
          </div>
          <div class="col-4">
            <strong>Data de Expedição</strong>
            <p>{{ parseDate(aluno?.dadosAuxiliares?.dataExpedicao) }}</p>
          </div>
          <div class="col-4">
            <strong>Título Eleitoral</strong>
            <p>{{ aluno?.dadosAuxiliares?.tituloEleitoral }}</p>
          </div>
          <div class="col-4">
            <strong>Zona</strong>
            <p>{{ aluno?.dadosAuxiliares?.zona }}</p>
          </div>
          <div class="col-4">
            <strong>Seção</strong>
            <p>{{ aluno?.dadosAuxiliares?.secao }}</p>
          </div>

          <template v-if="aluno?.dadosAuxiliares?.sexo === 'M'">
            <div class="col-4">
              <strong>Certificado Militar</strong>
              <p>{{ aluno?.dadosAuxiliares?.certificadoMilitar }}</p>
            </div>
            <div class="col-4">
              <strong>Órgão de Expedição Cert. Militar</strong>
              <p>{{ aluno?.dadosAuxiliares?.orgaoDeExpedicao }}</p>
            </div>

            <div class="col-4">
              <strong>Série Cert. Militar</strong>
              <p>{{ aluno?.dadosAuxiliares?.serie }}</p>
            </div>
          </template>
        </div>
      </div>
      <div class="card">
        <h5>Dados Bancários</h5>
        <div class="grid">
          <div class="col-4">
            <strong>Banco</strong>
            <p>{{ aluno?.dadosBancarios?.banco }}</p>
          </div>
          <div class="col-4">
            <strong>Número da Agência</strong>
            <p>{{ aluno?.dadosBancarios?.numeroDaAgencia }}</p>
          </div>
          <div class="col-4">
            <strong>Número da Conta</strong>
            <p>{{ aluno?.dadosBancarios?.numeroDaConta }}</p>
          </div>
          <div class="col-4">
            <strong>Nome da Agência</strong>
            <p>{{ aluno?.dadosBancarios?.nomeDaAgencia }}</p>
          </div>
          <div class="col-4">
            <strong>Cidade da Agência</strong>
            <p>{{ aluno?.dadosBancarios?.cidadeDaAgencia }}</p>
          </div>
          <div class="col-4">
            <strong>Endereço da Agência</strong>
            <p>{{ aluno?.dadosBancarios?.enderecoDaAgencia }}</p>
          </div>
          <div class="col-4">
            <strong>Bairro da Agência</strong>
            <p>{{ aluno?.dadosBancarios?.bairroDaAgencia }}</p>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<style></style>
