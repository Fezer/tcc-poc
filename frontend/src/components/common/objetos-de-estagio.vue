<script lang="ts">
import { useToast } from "primevue/usetoast";
import { Estagio } from "~~/src/types/NovoEstagio";

export default defineComponent({
  props: {
    estagio: {
      type: Object,
      required: true,
    },
    perfil: {
      type: String,
    },
  },
  setup({
    estagio,
    perfil,
  }: {
    estagio: Estagio;
    perfil: "aluno" | "coe" | "coafe" | "coord" | "orientador";
  }) {
    const { setTermo } = useTermo();
    const router = useRouter();
    const toast = useToast();

    const { auth } = useAuth();

    const handleRedirectToTermoAditivo = (id: string) => {
      if (perfil === "aluno") {
        setTermo(null);
        return router.push(`/aluno/termo-aditivo/${id}`);
      }

      return router.push(`/${perfil}/termo/${id} `);
    };

    const handleDownloadCertificado = async () => {
      try {
        let url = `/orientador/${auth?.value?.identifier}/certificado/${estagio?.certificadoDeEstagio}/imprimir-certificado`;
        if (perfil === "aluno") {
          url = `/aluno/${auth?.value?.identifier}/certificado-de-estagio/${estagio?.certificadoDeEstagio}/gerar`;
        }

        const file = await $fetch(url, {
          method: "GET",
        });

        const fileURL = URL.createObjectURL(file);

        return window.open(fileURL, "_blank");
      } catch (err) {
        toast.add({
          severity: "error",
          summary: "Erro ao baixar certificado",
          detail:
            err?.response?._data?.error ||
            "Ocorreu um erro ao baixar o certificado, tente novamente mais tarde",
        });
      }
    };

    const handleDownloadFicha = async () => {
      try {
        let url = `/coafe/${estagio?.aluno?.matricula}/ficha-de-avaliacao-parcial/${estagio?.fichaDeAvaliacao}/download`;
        if (perfil === "aluno") {
          return router.push(`/aluno/estagio/avaliacao/${estagio?.id}`);
        }

        const file = await $fetch(url, {
          method: "GET",
        });

        const fileURL = URL.createObjectURL(file);

        return window.open(fileURL, "_blank");
      } catch (err) {
        toast.add({
          severity: "error",
          summary: "Erro ao baixar certificado",
          detail:
            err?.response?._data?.error ||
            "Ocorreu um erro ao baixar o certificado, tente novamente mais tarde",
        });
      }
    };

    return {
      estagio,
      perfil,
      handleRedirectToTermoAditivo,
      handleDownloadCertificado,
      handleDownloadFicha,
    };
  },
});
</script>

<template>
  <div>
    <h3>Termo de Compromisso</h3>

    <div class="card flex items-center justify-between">
      <h5>Termo de Compromisso</h5>
      <NuxtLink :to="`/${perfil}/termo/${estagio?.termoDeCompromisso}`">
        <Button label="Ver termo" class="p-button-secondary"></Button>
      </NuxtLink>
    </div>
    <template v-if="perfil !== 'orientador'">
      <Divider />

      <template v-if="!!estagio?.fichaDeAvaliacao">
        <Divider />

        <h3>Ficha de Avaliação</h3>

        <div class="card flex items-center justify-between">
          <h5>Ficha de Avaliação</h5>
          <Button
            label="Ver ficha de avaliação"
            class="p-button-secondary"
            @click="handleDownloadFicha"
          ></Button>
        </div>
      </template>

      <Divider />

      <h3 v-if="estagio?.termoAdivito?.length">Termos Aditivos</h3>

      <div
        class="card flex items-center justify-between"
        v-for="idTermoAditivo in estagio?.termoAdivito"
        :key="idTermoAditivo"
      >
        <h5>Termo Aditivo #{{ idTermoAditivo }}</h5>
        <Button
          label="Ver termo aditivo"
          class="p-button-secondary"
          @click="() => handleRedirectToTermoAditivo(idTermoAditivo)"
        ></Button>
      </div>
    </template>

    <template
      v-if="
        !!estagio?.certificadoDeEstagio &&
        (perfil === 'orientador' || perfil === 'aluno')
      "
    >
      <Divider />

      <h3>Certificado de Estágio</h3>

      <div class="card flex items-center justify-between">
        <h5>Certificado de Estágio</h5>
        <Button
          label="Baixar Certificado"
          class="p-button-secondary"
          @click="handleDownloadCertificado"
        ></Button>
      </div>
    </template>

    <Divider />

    <h3 v-if="estagio?.relatorioDeEstagio?.length">Relatórios de Estágio</h3>

    <div
      class="card flex items-center justify-between"
      v-for="idRelatorio in estagio?.relatorioDeEstagio"
      :key="idRelatorio"
    >
      <h5>Relatório #{{ idRelatorio }}</h5>
      <NuxtLink
        :to="`${perfil === 'aluno' ? '/aluno' : ''}/relatorio/${idRelatorio}`"
      >
        <Button label="Ver Relatório" class="p-button-secondary"></Button>
      </NuxtLink>
    </div>

    <template v-if="!!estagio?.termoDeRescisao">
      <Divider />

      <h3>Termo de Rescisão</h3>

      <div class="card flex items-center justify-between">
        <h5>Termo de Rescisão</h5>
        <NuxtLink :to="`/${perfil}/termo-rescisao/${estagio?.termoDeRescisao}`">
          <Button
            label="Ver Termo de Rescisao"
            class="p-button-secondary"
          ></Button>
        </NuxtLink>
      </div>
    </template>
  </div>
</template>
