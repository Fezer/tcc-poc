<script lang="ts">
import { defineComponent } from "vue";
import { ofetch } from "ofetch";
import AlunoService from "~~/services/AlunoService";

export default defineComponent({
  setup() {
    const { auth, setAuth } = useAuth();
    const route = useRoute();
    const { aluno, setAluno } = useAluno();
    const router = useRouter();
    const config = useRuntimeConfig();
    const alunoService = new AlunoService();

    const estagios = [
      {
        label: "Estágio atual",
        link: "/aluno",
      },
      {
        label: "Todos os estágios",
        link: "/aluno/processos/estagios",
      },
    ];

    const processos = [
      {
        label: "Termos de Compromisso",

        link: "/aluno/processos/termos-de-compromisso",
      },
      {
        label: "Termos Aditivos",

        link: "/aluno/processos/termos-aditivos",
      },
      {
        label: "Termos de Rescisão",

        link: "/aluno/processos/termos-de-rescisao",
      },
      {
        label: "Relatórios de Estágio",

        link: "/aluno/processos/relatorios",
      },
      {
        label: "Certificados",
        link: "/aluno/certificados",
      },
    ];

    const getIsTabActive = (
      tab: "aluno" | "aluno/processos" | "aluno/certificados"
    ) => {
      return route.path === tab;
    };

    onMounted(async () => {
      if (!auth?.value?.identifier) {
        const accessToken = localStorage.getItem("accessToken");

        if (!accessToken) return router.replace("/login");
        const grrData = await $fetch(`${config.BACKEND_URL}/siga/grr`, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });

        if (typeof grrData !== "string") {
          throw new Error(
            "Erro ao obter o GRR do aluno. Tente novamente mais tarde."
          );
        }

        globalThis.$fetch = ofetch.create({
          baseURL: config.BACKEND_URL || "http://localhost:5000",
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        });

        setAuth({
          token: accessToken,
          tipoUsuario: "aluno",
          identifier: JSON.parse(grrData)?.data?.grr,
        });

        if (!aluno?.value) {
          await alunoService
            .getAlunoFromSiga(auth?.value?.identifier)
            .then(async (res) => {
              setAluno(res);
              return res;
            });
        }
      }
    });

    return {
      aluno,
      estagios,
      processos,
      getIsTabActive,
    };
  },
});
</script>

<template>
  <div>
    <div class="h-full w-full flex items-center justify-center flex-col mb-4">
      <strong>{{ aluno?.nome }}</strong>
      <p>Discente</p>
    </div>

    <span>
      <span
        :class="`pi pi-briefcase pi-fw  mr-2 text-lg`"
        style="vertical-align: center"
      />
      <strong>ESTÁGIOS</strong>
    </span>
    <div v-for="item in estagios" :key="item.label">
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
