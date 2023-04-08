<script>
export default {
  props: {
    etapa: {
      type: String,
    },
    status: {
      type: String,
    },
    motivo: {
      type: String,
    },
  },
};
</script>

<template>
  <div class="card">
    <h4>Andamento do Estágio</h4>

    <ProgressBar
      :value="100"
      :show-value="false"
      style="height: 15px"
      class="mb-3"
    />

    <div>
      <div class="grid">
        <div class="text-box col-3">
          <strong>Status</strong>
          <p class="text-orange-500 font-bold">NECESSITA DE RELATÓRIO</p>
        </div>
        <div class="text-box col-3 flex flex-col">
          <strong>Data de Início do Estágio</strong>
          <span>01/01/2021</span>
        </div>
        <div class="text-box col-3 flex flex-col">
          <strong>Data de Início do Estágio</strong>
          <span>01/01/2021</span>
        </div>
        <div class="col-3 flex items-center justify-end">
          <Button
            label="Inserir relatório de Estágio"
            class="p-button-danger self-center bg-orange-500"
            icon="pi pi-file"
          />
        </div>
      </div>
      <div>
        <strong>Detalhes</strong>
        <p>
          {{
            motivo ||
            "Ut hendrerit nisl a varius euismod. Morbi tincidunt sodales augue, vel ullamcorper diam tincidunt ut. Quisque convallis dolor elit, eu porta odio iaculis et. Vivamus tincidunt fermentum egestas. Nam eu imperdiet elit. Sed lorem massa, rutrum quis feugiat ut, porta quis nibh. Morbi tempus magna at risus pellentesque sodales. Donec quis neque ac tellus mollis eleifend. Mauris nisi eros, congue nec imperdiet porta, accumsan vel orci. Curabitur elementum eleifend felis nec egestas. Vivamus sed dolor vitae turpis aliquet placerat. Vestibulum bibendum tellus eros, sed elementum neque venenatis et. Quisque sit amet venenatis ante, eu fringilla tellus. "
          }}
        </p>
      </div>
    </div>
  </div>
</template>

<style>
.text-red-500 {
  color: red !important;
}
</style>
