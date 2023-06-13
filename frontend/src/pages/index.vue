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
    const { setGRR } = useGRR();

    onMounted(async () => {
      const urlParams = new URLSearchParams(window.location.search);
      const authorizationCode = urlParams.get("code");

      if (authorizationCode) {
        const tokenUrl =
          "https://login.ufpr.br/realms/master/protocol/openid-connect/token";
        const data = new URLSearchParams();
        data.append("grant_type", "authorization_code");
        data.append("code", authorizationCode);
        data.append("client_id", "estagios");
        data.append("client_secret", "2xWHPudAW4hmAsnOYzW9eg4oUUKUHTLu");
        data.append("redirect_uri", "http://localhost:3000/");

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
          localStorage.setItem("accessToken", accessToken);

          const decodedToken = parseJWT(accessToken);

          const email = decodedToken["email"];

          console.log(decodedToken);

          // redirect to aluno page

          globalThis.$fetch = ofetch.create({
            baseURL: config.BACKEND_URL || "http://localhost:5000",
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          });

          // // get aluno grr
          // if (email) {
          //   const grr = await $fetch(
          //     `http://siga.ufpr.br:8380/siga/api/graduacao/discente?email=${email}`,
          //     {
          //       method: "GET",
          //       headers: {
          //         origin: "http://localhost:3000",
          //         Authorization: `Bearer ${accessToken}`,
          //       },
          //     }
          //   );
          //   console.log(grr);
          //   setGRR(grr);
          // }

          console.log("Token de acesso obtido com sucesso!");
          router.push("/aluno");
        } catch (error) {
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
