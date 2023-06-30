<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  setup() {
    const route = useRoute();
    const getIsTabActive = (tab: "coord") => {
      return route.path === tab;
    };

    const processos = [
      {
        label: "Termos de Compromisso",
        link: "/coord/processos/termo",
      },
      {
        label: "Termos Aditivos",
        link: "/coord/processos/termoAditivo",
      },
      {
        label: "Termos de Rescisão",
        link: "/coord/termo-rescisao",
      },
    ];

    return {
      getIsTabActive,
      processos,
    };
  },
});
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col">
      <strong class=" ">Coordenação</strong>
      <p class=" ">Curso X</p>
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

    <span>
      <span
        :class="`pi pi-file-export pi-fw  mr-2 text-lg`"
        style="vertical-align: center"
      />
      <strong>PROCESSOS</strong>
    </span>

    <div v-for="item in processos" :key="item.label">
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
