<script lang="ts">
export default {
  data() {
    return {
      processos: [
        {
          label: "Relatórios de Estágio",
          link: "/orientador/relatorio",
        },
        {
          label: "Termos de Rescisão",
          link: "/orientador/termo-rescisao",
        },
      ],
      estagios: [
        {
          label: "Estágios de Orientados",
          link: "/orientador/estagios",
        },
      ],
    };
  },
  methods: {
    getIsTabActive(tab: "orientador") {
      return this.$route.path === tab;
    },
  },
};
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col mb-4">
      <strong class=" ">Prof. Orientador Orientador</strong>
      <p class=" ">Orientador</p>
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
          'mb-4 mt-3 hover:opacity-70 transition-all '.concat(
            (getIsTabActive(item.link) &&
              'border-l-2 border-l-gray-500 pl-2') ||
              ''
          )
        "
      >
        <i
          :class="`${item.icon} pi pi-fw   mr-2 text-lg`"
          style="vertical-align: center"
        />
        <NuxtLink :to="item.link" class="text-lg text-black">
          {{ item.label }}
        </NuxtLink>
      </div>
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
          'mb-4 mt-3 hover:opacity-70 transition-all '.concat(
            (getIsTabActive(item.link) &&
              'border-l-2 border-l-gray-500 pl-2') ||
              ''
          )
        "
      >
        <i
          :class="`${item.icon} pi pi-fw   mr-2 text-lg`"
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
