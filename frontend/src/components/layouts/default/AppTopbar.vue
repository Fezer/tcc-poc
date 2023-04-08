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
    topbarImage() {
      return "/images/ufpr.png";
    },
  },
  components: { Button },
};
</script>

<template>
  <div class="layout-topbar gap-3 text-white" style="background-color: #1a4768">
    <div class="flex-1 flex items-center gap-2">
      <img alt="Logo" :src="topbarImage()" class="h-3rem" />
      <span>Módulo Estágios - SIGA</span>
    </div>

    <Button
      icon="pi pi-sign-out"
      class="p-button-secondary"
      v-tooltip.bottom="'Sair'"
    />
  </div>
</template>
