<script lang="ts">
import { defineComponent } from "vue";
import { StatusTermo } from "~~/src/types/Termos";

export default defineComponent({
  props: {
    status: {
      type: String,
      required: true,
    },
  },
  setup({ status }: { status: StatusTermo }) {
    const termoParsedString = parseStatusProcessos(status);

    const colors: {
      [key in StatusTermo]: string;
    } = {
      EmPreenchimento: "",
      EmAprovacao: "info",
      Aprovado: "success",
      EmRevisao: "warning",
      Reprovado: "danger",
      Cancelado: "danger",
    };

    const tagColor = colors[status] || "info";

    return {
      termoParsedString,
      tagColor,
    };
  },
});
</script>
<template>
  <Tag
    class="mr-2 p-2 w-10 text-sm flex items-center justify-center"
    :severity="tagColor"
    :value="termoParsedString"
  />
</template>

<style></style>
