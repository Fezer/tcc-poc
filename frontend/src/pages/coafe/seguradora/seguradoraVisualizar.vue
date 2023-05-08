<script lang="ts">
import { defineComponent, reactive } from "vue";

export default defineComponent({
  data() {
    return {
      seguradora: {
        seguradora: "Seguradora de Seguros",
        numeroApolice: "12421421214",
        cnpj: "124412142",
        situação: "ATIVO",
      },
    };
  },
});
</script>

<template>
  <div>Oi tela teste</div>
</template>

<style></style>
