<script lang="ts">
import { ofetch } from "ofetch";
import { defineComponent } from "vue";

definePageMeta({
  layout: "empty",
});

export default defineComponent({
  setup() {
    const config = useRuntimeConfig();
    const router = useRouter();
    const { auth, setAuth } = useAuth();

    onMounted(async () => {
      if (!localStorage) return;
      const urlParams = new URLSearchParams(window.location.search);
      const authorizationCode = urlParams.get("code");

      if (authorizationCode) {
        const tokenUrl = config.SIGA_TOKEN_URL;
        const data = new URLSearchParams();
        data.append("grant_type", "authorization_code");
        data.append("code", authorizationCode);
        data.append("client_id", config.SIGA_CLIENT_ID);
        data.append("client_secret", config.SIGA_CLIENT_SECRET);
        data.append("redirect_uri", config.SIGA_REDIRECT_URI);

        try {
          const response = await fetch(tokenUrl, {
            method: "POST",
            headers: {
              "Content-Type": "application/x-www-form-urlencoded",
            },
            body: data,
          });
          const tokenData = await response.json();
          const accessToken = tokenData.access_token;
          localStorage?.setItem("accessToken", accessToken);
          localStorage?.setItem("proofile", "aluno");

          globalThis.$fetch = ofetch.create({
            baseURL: config.BACKEND_URL || "http://localhost:5000",
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          });

          // get aluno grr
          const grrData = await $fetch(`/siga/grr`, {
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

          setAuth({
            token: accessToken,
            tipoUsuario: "aluno",
            identifier: JSON.parse(grrData)?.data?.grr,
          });

          console.log(JSON.parse(grrData)?.data?.grr);

          console.log("Token de acesso obtido com sucesso!");
          router.push("/aluno");
        } catch (error) {
          localStorage?.removeItem("accessToken");
          localStorage?.removeItem("perfil");

          router.replace("/login");
          console.error("Erro ao obter o token de acesso:", error);
        }
      } else {
        router.replace("/login");
      }
    });

    return {};
  },
});
</script>
<template>
  <div class="h-screen w-full flex items-center justify-center">
    <h3>Carregando...</h3>
  </div>
</template>

<style></style>
