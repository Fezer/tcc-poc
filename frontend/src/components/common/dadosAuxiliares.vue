<script lang="ts">
import { defineComponent } from "vue";
import NovoEstagio, { TipoEstagio } from "../../types/NovoEstagio";
import { DadosAuxiliares } from "../../types/Aluno";
import parseDate from "~/utils/parseDate";

export default defineComponent({
  props: {
    termo: {
      type: Object,
      required: true,
    },
  },
  setup({ termo }: { termo: NovoEstagio }) {
    const { aluno } = useAluno();

    const formatMoney = (value: number) => {
      return Intl.NumberFormat("pt-BR", {
        style: "currency",
        currency: "BRL",
      }).format(value);
    };

    const parseTipoEstagio = (tipo: TipoEstagio) =>
      tipo === "Obrigatorio" ? "Obrigatório" : "Não Obrigatório";

    const dadosAuxiliares: DadosAuxiliares | null =
      aluno.value?.dadosAuxiliares ||
      termo?.value?.aluno?.dadosAuxiliares ||
      null;

    const dadosBancarios =
      aluno.value?.dadosBancarios ||
      termo?.value?.aluno?.dadosBancarios ||
      null;

    const shouldShowDadosAuxiliares =
      ((!!termo?.estagio?.estagioUfpr || !!termo?.estagioUfpr) &&
        termo?.estagio?.tipoEstagio == "NaoObrigatorio") ||
      (termo?.estagioUfpr && termo?.tipoEstagio == "NaoObrigatorio");

    return {
      formatMoney,
      parseDate,
      parseTipoEstagio,
      termo,
      aluno,
      dadosAuxiliares,
      dadosBancarios,
      shouldShowDadosAuxiliares,
    };
  },
});
</script>

<template>
  <div>
    <div class="card" v-if="shouldShowDadosAuxiliares">
      <h5>Dados Auxiliares</h5>
      <div class="grid">
        <div class="col-4">
          <strong>Nome do Pai</strong>
          <p>{{ dadosAuxiliares?.nomePai }}</p>
        </div>
        <div class="col-4">
          <strong>Estado Civil</strong>
          <p>{{ dadosAuxiliares?.estadoCivil }}</p>
        </div>
        <div class="col-4">
          <strong>Quantidade de Dependentes</strong>
          <p>{{ dadosAuxiliares?.dependentes }}</p>
        </div>
        <div class="col-4">
          <strong>Grupo Sanguíneo</strong>
          <p>{{ dadosAuxiliares?.grupoSanguineo }}</p>
        </div>
        <div class="col-4">
          <strong>Cor da Pele</strong>
          <p>{{ dadosAuxiliares?.corRaca }}</p>
        </div>
        <div class="col-4">
          <strong>Sexo</strong>
          <p>{{ dadosAuxiliares?.sexo }}</p>
        </div>
        <div class="col-4">
          <strong>Nome da Mãe</strong>
          <p>{{ dadosAuxiliares?.nomeMae }}</p>
        </div>
        <div class="col-4">
          <strong>Nacionalidade</strong>
          <p>{{ dadosAuxiliares?.nacionalidade }}</p>
        </div>
        <div
          class="col-4"
          v-if="dadosAuxiliares?.nacionalidade?.trim() != 'BRASILEIRO'"
        >
          <strong>Data de Chegada no País</strong>
          <p>{{ parseDate(dadosAuxiliares?.dataDeChegadaNoPais) }}</p>
        </div>
        <div class="col-4">
          <strong>Órgão Emissor</strong>
          <p>{{ dadosAuxiliares?.orgaoEmissor }}</p>
        </div>
        <div class="col-4">
          <strong>Unidade Federativa</strong>
          <p>{{ dadosAuxiliares?.uf }}</p>
        </div>
        <div class="col-4">
          <strong>Data de Expedição</strong>
          <p>{{ parseDate(dadosAuxiliares?.dataExpedicao) }}</p>
        </div>
        <div class="col-4">
          <strong>Título Eleitoral</strong>
          <p>{{ dadosAuxiliares?.tituloEleitoral }}</p>
        </div>
        <div class="col-4">
          <strong>Zona</strong>
          <p>{{ dadosAuxiliares?.zona }}</p>
        </div>
        <div class="col-4">
          <strong>Seção</strong>
          <p>{{ dadosAuxiliares?.secao }}</p>
        </div>

        <template v-if="dadosAuxiliares?.sexo === 'M'">
          <div class="col-4">
            <strong>Certificado Militar</strong>
            <p>{{ dadosAuxiliares?.certificadoMilitar }}</p>
          </div>
          <div class="col-4">
            <strong>Órgão de Expedição Certificado Militar</strong>
            <p>{{ dadosAuxiliares?.orgaoDeExpedicao }}</p>
          </div>

          <div class="col-4">
            <strong>Série do Certificado Militar</strong>
            <p>{{ dadosAuxiliares?.serie }}</p>
          </div>
        </template>
      </div>
    </div>
    <div class="card" v-if="shouldShowDadosAuxiliares">
      <h5>Dados Bancários</h5>
      <div class="grid">
        <div class="col-4">
          <strong>Banco</strong>
          <p>{{ dadosBancarios?.banco }}</p>
        </div>
        <div class="col-4">
          <strong>Número da Agência</strong>
          <p>{{ dadosBancarios?.numeroDaAgencia }}</p>
        </div>
        <div class="col-4">
          <strong>Número da Conta</strong>
          <p>{{ dadosBancarios?.numeroDaConta }}</p>
        </div>
        <div class="col-4">
          <strong>Nome da Agência</strong>
          <p>{{ dadosBancarios?.nomeDaAgencia }}</p>
        </div>
        <div class="col-4">
          <strong>Cidade da Agência</strong>
          <p>{{ dadosBancarios?.cidadeDaAgencia }}</p>
        </div>
        <div class="col-4">
          <strong>Endereço da Agência</strong>
          <p>{{ dadosBancarios?.enderecoDaAgencia }}</p>
        </div>
        <div class="col-4">
          <strong>Bairro da Agência</strong>
          <p>{{ dadosBancarios?.bairroDaAgencia }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
