<script>
export default {
  props: {
    termo: {
      type: Object,
      required: true,
    },
  },
};
</script>

<template>
  <div class="card" v-if="!termo?.estagio?.estagioUfpr">
    <h5>Dados do Contratante</h5>

    <div class="grid">
      <div class="col-4">
        <strong>Nome do Contratante</strong>
        <p>{{ termo?.nomeContratante }}</p>
      </div>
      <div class="col-4">
        <strong>Tipo do Contratante</strong>
        <p>{{ termo?.tipoContratante }}</p>
      </div>
      <div class="col-4">
        <strong>CNPJ</strong>
        <p>{{ termo?.cnpjContratente }}</p>
      </div>
      <div class="col-4">
        <strong>Telefone do Contratante</strong>
        <p>{{ termo?.telefoneContratante }}</p>
      </div>
      <div class="col-4">
        <strong>Número da Apólice</strong>
        <p>{{ termo?.numeroApolice }}</p>
      </div>
      <div class="col-4">
        <strong>Endereço</strong>
        <p>{{ termo?.enderecoContratente }}</p>
      </div>
      <div class="col-4">
        <strong>Cidade</strong>
        <p>{{ termo?.cidadeContratante }}</p>
      </div>
      <div class="col-4">
        <strong>Estado</strong>
        <p>{{ termo?.estadoContratante }}</p>
      </div>
    </div>
  </div>
</template>

<style></style>
