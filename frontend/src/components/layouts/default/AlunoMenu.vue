<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  setup() {
    const route = useRoute();
    const { aluno } = useAluno();
    const estagios = [
      {
        label: "Estágio atual",
        link: "/aluno",
      },
      {
        label: "Todos os estágios",
        link: "/aluno/processos",
      },
    ];

    const processos = [
      {
        label: "Termos de Compromisso",

        link: "/aluno/processos",
      },
      {
        label: "Termos Aditivos",

        link: "/aluno/processos",
      },
      {
        label: "Termos de Recisão",

        link: "/aluno/processos",
      },
      {
        label: "Relatórios de Estágio",

        link: "/aluno/processos",
      },
      {
        label: "Certificados",
        link: "/aluno/certificados",
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
      estagios,
      processos,
      getIsTabActive,
    };
  },
});
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col mb-4">
      <strong>{{ aluno?.nome }}</strong>
      <p>Discente</p>
    </div>

    <span>
      <span
        :class="`pi pi-briefcase pi-fw  mr-2 text-lg`"
        style="vertical-align: center"
      />
      <strong>ESTÁGIOS</strong>
    </span>
    <div v-for="item in estagios" :key="item.label">
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

    <span>
      <span
        :class="`pi pi-file-export pi-fw  mr-2 text-lg`"
        style="vertical-align: center"
      />
      <strong>PROCESSOS</strong>
    </span>

    <div v-for="item in processos" :key="item.label">
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
