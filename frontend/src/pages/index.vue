<script lang="ts">
import { ofetch } from "ofetch";
import { defineComponent } from "vue";

definePageMeta({
  layout: "empty",
});

export default defineComponent({
  setup() {
    const router = useRouter();

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
          console.log(accessToken);
          localStorage.setItem("accessToken", accessToken);

          // redirect to aluno page

          globalThis.$fetch = ofetch.create({
            baseURL: "http://localhost:5000",
            headers: {
              Authorization: `Bearer ${accessToken}`,
            },
          });

          router.push("/aluno");
        } catch (error) {
          router.replace("/login");
          console.error("Erro ao obter o token de acesso:", error);
        }
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
