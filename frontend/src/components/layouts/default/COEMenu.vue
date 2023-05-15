<script lang="ts">
export default {
  data() {
    return {
      menu: [
        {
          label: "Pendências",
          link: "/coe",
          icon: "pi-envelope",
        },
        {
          label: "Processos",
          link: "/coe/coeProcesses",
          icon: "pi-envelope",
        },
        {
          label: "Certificados",
          link: "/coe/certificados",
          icon: "pi-file",
        },
      ],
    };
  },
  methods: {
    getIsTabActive(tab: "coe") {
      console.log(this.$route.path);

      return this.$route.path === tab;
    },
  },
};
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col">
      <strong class=" ">Administrador</strong>
      <p class=" ">COE</p>
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
