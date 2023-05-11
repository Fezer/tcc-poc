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
  <div class="layout-topbar gap-3 text-white">
    <div class="flex-1 flex items-center gap-2">
      <img alt="Logo" :src="topbarImage()" class="h-3rem" />
      <span class="text-black">Módulo Estágios - SIGA</span>
    </div>

    <div class="flex gap-2">
      <NuxtLink to="/aluno">
        <Button class="p-button-secondary" label="Aluno (dev)" />
      </NuxtLink>
      <NuxtLink to="/coord">
        <Button class="p-button-secondary" label="Coord (dev)" />
      </NuxtLink>
      <NuxtLink to="/orientador">
        <Button class="p-button-secondary" label="Orientador (dev)" />
      </NuxtLink>
      <NuxtLink to="/coe">
        <Button class="p-button-secondary" label="COE (dev)" />
      </NuxtLink>
      <NuxtLink to="/coafe">
        <Button class="p-button-secondary" label="COAFE (dev)" />
      </NuxtLink>
      <Button
        icon="pi pi-sign-out"
        class="p-button-secondary"
        v-tooltip.bottom="'Sair'"
      />
    </div>
  </div>
</template>
