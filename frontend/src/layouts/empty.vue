<script lang="ts" setup>
import { ofetch } from "ofetch";

const config = useRuntimeConfig();

globalThis.$fetch = ofetch.create({
  baseURL: config.BACKEND_URL || "http://localhost:5000",
});
</script>

<template>
  <div class="layout-empty">
    <Toast />
    <slot />
  </div>
</template>
