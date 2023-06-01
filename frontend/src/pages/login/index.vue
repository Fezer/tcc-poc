<script lang="ts">
definePageMeta({
  layout: "empty",
});
export default defineComponent({
  setup() {
    const handleLogin = () => {
      const clientId = "estagios";
      const redirectUri = "http://localhost:3000/";
      const authorizationEndpoint =
        "https://login.ufpr.br/realms/master/protocol/openid-connect/auth";

      const params = new URLSearchParams();
      params.append("response_type", "code");
      params.append("client_id", clientId);
      params.append("redirect_uri", redirectUri);
      params.append("scope", "openid");

      // Redirecionar para a página de login
      window.location.href = `${authorizationEndpoint}?${params.toString()}`;
    };

    return { handleLogin };
  },
});
</script>

<template>
  <div class="h-screen w-full flex items-center justify-center bg-white">
    <div class="flex flex-col max-w-md w-full">
      <img alt="Logo" src="/images/ufpr.png" class="h-24 object-contain" />
      <h2 class="text-center font-bold mb-6">Módulo Estágios</h2>
      <Button
        @click="handleLogin"
        class="p-button-primary"
        label="Login"
      ></Button>
      <NuxtLink to="/login/perfis" class="w-full flex flex-col mt-2">
        <Button class="p-button-secondary" label="Não sou aluno"></Button>
      </NuxtLink>
    </div>
  </div>
</template>

<style></style>
