<script>
export default {
  data() {
    return {
      menu: [
        {
          label: "PoC",
          link: "/",
          icon: "pi-file",
        },
        {
          label: "Aluno",
          link: "/aluno",
          icon: "pi-user",
        },
      ],
    };
  },
};
</script>

<template>
  <div>
    <strong class="text-white">MENU</strong>
    <div v-for="item in menu" :key="item.label">
      <div class="mb-4 mt-3">
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
