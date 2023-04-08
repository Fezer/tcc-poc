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
    <h4>Andamento do processo</h4>

    <ProgressBar
      :value="status === 'EmAprovacao' ? 50 : 100"
      :show-value="false"
      style="height: 15px"
      class="mb-3"
    />

    <div>
      <div class="grid">
        <div class="text-box col-4">
          <strong>Status</strong>
          <p v-if="status === 'EmAprovacao'" class="text-blue-500">
            ANÁLISE {{ etapa || "" }}
          </p>
          <p
            v-else
            :class="
              status === 'Aprovado'
                ? 'text-green-500 font-bold'
                : 'text-red-500 font-bold'
            "
          >
            {{ status?.toUpperCase() || "" }}
          </p>
        </div>
        <div class="text-box col-4 flex flex-col">
          <strong>Data de Início do processo</strong>
          <span>01/01/2021</span>
        </div>
        <div class="col-4 flex items-center justify-end">
          <Button
            label="Ver documento"
            class="p-button-secondary self-center"
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
