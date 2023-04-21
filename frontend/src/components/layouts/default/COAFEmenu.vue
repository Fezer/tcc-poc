<script lang="ts">
export default {
  data() {
    return {
      menu: [
        {
          label: "Pendências",
          link: "/coafe",
          icon: "pi-envelope",
        },
        {
          label: "Processos",
          link: "/coafe/coafeProcesses",
          icon: "pi-envelope",
        },
        {
          label: "Empresas",
          link: "/coafe/coafeEmpresas",
          icon: "pi-home",
        },
        {
          label: "Seguradoras",
          link: "/coafe/coafeSeguradoras",
          icon: "pi-home",
        },
        {
          label: "Agentes de Integração",
          link: "/coafe/coafeAgentes",
          icon: "pi-home",
        },
        {
          label: "Relatórios",
          link: "/coafe/coafeRelatorios",
          icon: "pi-file",
        },
      ],
    };
  },
  methods: {
    getIsTabActive(tab: "coafe") {
      return this.$route.path === tab;
    },
  },
};
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col">
      <strong class=" ">Administrador</strong>
      <p class=" ">COAFE</p>
    </div>
    <div v-for="item in menu" :key="item.label">
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
