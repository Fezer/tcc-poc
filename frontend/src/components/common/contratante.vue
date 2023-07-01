<script>
export default {
  props: {
    termo: {
      type: Object,
      required: true,
    },
  },
  methods: {
    parseTipoContratante(tipo) {
      switch (tipo) {
        case "PessoaFisica":
          return "Pessoa Física";
        case "PessoaJuridica":
          return "Pessoa Jurídica";
        default:
          return tipo;
      }
    },
  },
};
</script>

<template>
  <div class="card">
    <h5>Dados do Contratante</h5>

    <div class="grid">
      <template v-if="!termo?.estagio?.estagioUfpr && !termo?.estagioUfpr">
        <div class="col-4">
          <strong>Nome do Contratante</strong>
          <p>{{ termo?.contratante?.nome }}</p>
        </div>
        <div class="col-4">
          <strong>Tipo do Contratante</strong>
          <p>{{ parseTipoContratante(termo?.contratante?.tipo) }}</p>
        </div>
        <div class="col-4" v-if="termo?.contratante?.tipo === 'PessoaJuridica'">
          <strong>CNPJ</strong>
          <p>{{ termo?.contratante?.cnpj }}</p>
        </div>
        <div class="col-4" v-else>
          <strong>Cadastro de Pessoa Física (CPF)</strong>
          <p>{{ termo?.contratante?.cpf }}</p>
        </div>
        <div class="col-4">
          <strong>Telefone do Contratante</strong>
          <p>{{ termo?.contratante?.telefone }}</p>
        </div>
      </template>
      <div class="col-4">
        <strong>Seguradora</strong>
        <p>{{ termo?.seguradora?.nome }}</p>
      </div>
      <div class="col-4">
        <strong>Número da Apólice</strong>
        <p>{{ termo?.apolice?.numero }}</p>
      </div>
      <div class="col-4" v-if="termo?.agenteIntegrador">
        <strong>Agente Integrador</strong>
        <p>{{ termo?.agenteIntegrador?.nome }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
