<script lang="ts">
export default {
  emits: ['topbar-menu-toggle', 'menu-toggle'],
  computed: {

  },
  methods: {
    onMenuToggle(event) {
      this.$emit('menu-toggle', event);
    },
    onTopbarMenuToggle(event) {
      this.$emit('topbar-menu-toggle', event);
    },
    topbarImage() {
      return '/images/ufpr.png';
    }
  }
};
</script>

<template>
  <div class="layout-topbar gap-3 text-white" style="background-color: #1A4768">
    <img alt="Logo" :src="topbarImage()" class="h-3rem">
    <span>Módulo Estágios - PoC</span>
  </div>
</template>
