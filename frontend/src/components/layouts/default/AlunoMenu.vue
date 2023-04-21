<script lang="ts">
export default {
  data() {
    return {
      menu: [
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
      ],
    };
  },
  methods: {
    getIsTabActive(tab: "aluno" | "aluno/processos" | "aluno/certificados") {
      console.log(this.$route.path);

      return this.$route.path === tab;
    },
  },
};
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col">
      <strong>João Feliz da Silva</strong>
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
