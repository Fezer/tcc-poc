<template>
  <div>
    <div class="flex justify-content-between">
      <h6>
        Seguradora
        <h3>{{ seguradora.seguradora }}</h3>
        <h5>Dados da seguradora</h5>
      </h6>
    </div>
    <div class="card flex-column">
      <div class="flex flex-row justify-content-between gap-10">
        <div class="flex align-items-left flex-column pb-4">
          <b>Numero da Apolice</b>
          <p>{{ seguradora.numeroApolice }}</p>
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>CNPJ</b>
          <p>{{ seguradora.cnpj }}</p>
        </div>
        <div class="flex align-items-left flex-column pb-4">
          <b>Status</b>
          <p>{{ seguradora.situação }}</p>
        </div>
      </div>
    </div>
    <div class="flex flex-row justify-content-end flex-wrap gap-2">
      <Button label="Inativar" severity="secondary" />
      <Button label="Editar" severity="info" />
    </div>
  </div>
</template>
<script lang="ts">
import { defineComponent, reactive } from "vue";
import Button from "primevue/button";

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
