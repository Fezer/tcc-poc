<script lang="ts">
definePageMeta({
  layout: "empty",
});
export default defineComponent({
  setup() {
    const handleLogin = () => {
      const clientId = "estagios";
      const redirectUri = "http://localhost:3000/login/callback";
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

      <NuxtLink to="/coe/processos/termo" class="w-full flex flex-col mb-2">
        <Button class="p-button-secondary" label="COE"></Button>
      </NuxtLink>
      <NuxtLink class="w-full flex flex-col mb-2" to="/coafe/processos/termo">
        <Button class="p-button-secondary" label="COAFE"></Button>
      </NuxtLink>
      <NuxtLink class="w-full flex flex-col mb-2" to="/orientador">
        <Button class="p-button-secondary" label="Orientador(a)"></Button>
      </NuxtLink>
      <NuxtLink class="w-full flex flex-col mb-2" to="/coord/processos/termo">
        <Button class="p-button-secondary" label="Coordenação"></Button>
      </NuxtLink>

      <NuxtLink to="/login">
        <Button
          class="p-button-secondary"
          label="Voltar"
          icon="pi pi-arrow-left"
        ></Button>
      </NuxtLink>
    </div>
  </div>
</template>

<style></style>

<style></style>
