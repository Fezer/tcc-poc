<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  setup() {
    const route = useRoute();
    const { aluno } = useAluno();
    const menu = [
      {
        label: "Estágio",
        link: "/aluno",
        icon: "pi-briefcase",
      },
      {
        label: "Processos",
        link: "/aluno/processos",
        icon: "pi-envelope",
      },
      {
        label: "Certificados",
        link: "/aluno/certificados",
        icon: "pi-file",
      },
    ];

    const getIsTabActive = (
      tab: "aluno" | "aluno/processos" | "aluno/certificados"
    ) => {
      console.log(route.path);

      return route.path === tab;
    };

    return {
      aluno,
      menu,
      getIsTabActive,
    };
  },
});
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col">
      <strong>{{ aluno?.nome }}</strong>
      <p>Discente</p>
    </div>
    <div v-for="item in menu" :key="item.label">
      <div
        :class="
          'mb-4 mt-3 hover:opacity-70 transition-all  '.concat(
            (getIsTabActive(item.link) &&
              'border-l-2 border-l-gray-500 pl-2') ||
              ''
          )
        "
      >
        <i
          :class="`${item.icon} pi pi-fw  mr-2 text-lg`"
          style="vertical-align: center"
        />
        <NuxtLink :to="item.link" class="text-lg text-black">
          {{ item.label }}
        </NuxtLink>
      </div>
    </div>
  </div>
</template>

<style></style>
