<script lang="ts">
import dayjs from "dayjs";
import { defineComponent } from "vue";
export default defineComponent({
  props: {
    estagio: Object,
  },
  setup({ estagio }: { estagio: any }) {
    const dateFilter = (date: string) => {
      if (!date) return "";
      return dayjs(date).format("DD/MM/YYYY");
    };

    return {
      dateFilter,
    };
  },
});
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
          <p class="text-orange-500 font-bold">{{ estagio?.statusEstagio }}</p>
        </div>
        <div class="text-box col-3 flex flex-col">
          <strong>Data de Início do Estágio</strong>
          <span>{{ dateFilter(estagio?.dataInicio) }}</span>
        </div>
        <div class="text-box col-3 flex flex-col" v-if="estagio?.dataFinal">
          <strong>Data de Início do Estágio</strong>
          <span>{{ dateFilter(estagio?.dataFinal) }}</span>
        </div>
        <div class="col-3" v-else></div>
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
            estagio?.motivo ||
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
