<script>
import { defineComponent } from "vue";

export default defineComponent({
  setup() {
    const evaluateOptions = ["Péssimo", "Ruim", "Razoável", "Bom", "Excelente"];

    return {};
  },
});
</script>
<template>
  <div>
    <Toast />
    <span>
      <small>Processos > Iniciar novo estágio</small>
      <h3>Novo termo de compromisso</h3>
    </span>
    <div class="grid">
      <div class="col-12">
        <div class="card p-fluid col-12">
          <h5>Periodo de Estágio</h5>
        </div>
      </div>
    </div>
  </div>
</template>

<style></style>
