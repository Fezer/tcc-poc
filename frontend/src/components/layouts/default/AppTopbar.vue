<script lang="ts">
import Button from "primevue/button";

export default {
  emits: ["topbar-menu-toggle", "menu-toggle"],
  computed: {},
  methods: {
    onMenuToggle(event) {
      this.$emit("menu-toggle", event);
    },
    onTopbarMenuToggle(event) {
      this.$emit("topbar-menu-toggle", event);
    },
    handleLogout() {
      if (!process?.client) return;
      localStorage.removeItem("accessToken");
      localStorage.removeItem("profile");
      window.location.href = "/login";
    },
  },
  components: { Button },
};
</script>

<template>
  <div class="layout-topbar gap-3 text-white">
    <div class="flex-1 flex items-center gap-2">
      <img alt="Logo" src="/images/ufpr.png" class="h-3rem" />
      <span class="text-black">Módulo Estágios - SIGA</span>
    </div>

    <div class="flex gap-2">
      <Button
        @click="handleLogout"
        icon="pi pi-sign-out"
        class="p-button-secondary"
        v-tooltip.bottom="'Sair'"
      ></Button>
    </div>
  </div>
</template>
