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
      <strong class="text-white">João Feliz da Silva</strong>
      <p class="text-white">Discente</p>
    </div>
    <div v-for="item in menu" :key="item.label">
      <div
        :class="
          'mb-4 mt-3 hover:opacity-70 transition-all '.concat(
            (getIsTabActive(item.link) && 'border-l-2 border-l-white pl-2') ||
              ''
          )
        "
      >
        <i
          :class="`${item.icon} pi pi-fw text-white mr-2 text-lg`"
          style="vertical-align: center"
        />
        <NuxtLink :to="item.link" class="text-white text-lg">
          {{ item.label }}
        </NuxtLink>
      </div>
    </div>
  </div>
</template>

<style></style>
