<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  setup() {
    const route = useRoute();
    const getIsTabActive = (tab: "coe") => {
      console.log(route.path);

      return route.path === tab;
    };

    const processos = [
      {
        label: "Termos de Compromisso",

        link: "/coe/processos/termo",
      },
      {
        label: "Termos Aditivos",

        link: "/coe/processos/termoAditivo",
      },
      {
        label: "Termos de Rescisão",

        link: "/coe/termo-rescisao",
      },
      {
        label: "Certificados",

        link: "/coe/certificados",
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
    <div class="h-full w-full flex items-center justify-center flex-col mb-4">
      <strong class=" ">Administrador</strong>
      <p class=" ">COE</p>
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

    <span>
      <span
        :class="`pi pi-briefcase pi-fw  mr-2 text-lg`"
        style="vertical-align: center"
      />
      <strong>ESTÁGIOS</strong>
    </span>

    <div
      :class="
        'mb-4 mt-3 hover:opacity-70 transition-all  '.concat(
          (getIsTabActive('/coe/estagios') &&
            'border-l-2 border-l-gray-500 pl-2') ||
            ' '
        )
      "
    >
      <i :class="`pi pi-fw  mr-2 text-lg`" style="vertical-align: center" />
      <NuxtLink to="/coe/estagios" class="text-lg text-black">
        Todos os Estágios
      </NuxtLink>
    </div>
  </div>
</template>

<style></style>
