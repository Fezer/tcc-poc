<script lang="ts">
import { useToast } from "primevue/usetoast";
import AuthService from "~~/services/AuthService";

definePageMeta({
  layout: "empty",
});
export default defineComponent({
  setup() {
    const authService = new AuthService();
    const toast = useToast();
    const router = useRouter();
    const { setAuth } = useAuth();
    const state = reactive({
      email: "",
      password: "",
    });

    const getHomeRouteByPerfil = (
      perfil: "coe" | "coafe" | "orientador" | "coordenacao"
    ) => {
      const rotas = {
        coe: "/coe",
        coafe: "/coafe",
        orientador: "/orientador",
        coordenacao: "/coord",
      };
      return rotas[perfil] || "/login";
    };

    const handleLogin = async () => {
      const { email, password } = state;
      if (!email || !password) {
        return toast.add({
          severity: "error",
          summary: "Erro ao fazer login!",
          detail: "Preencha todos os campos",
        });
      }

      try {
        await authService
          .login({
            email,
            password,
          })
          .then((res: { perfil: string; token: string }) => {
            setAuth(res);

            const route = getHomeRouteByPerfil(res?.perfil);

            router.push(route);
          });
      } catch (err) {
        console.error(err);
        toast.add({
          severity: "error",
          summary: "Erro ao fazer login!",
          detail: err?.response?._data?.error || "Email ou senha incorretos",
        });
      }
    };

    return { handleLogin, state };
  },
});
</script>

<template>
  <div class="h-screen w-full flex items-center justify-center bg-white">
    <div class="flex flex-col max-w-md w-full">
      <img alt="Logo" src="/images/ufpr.png" class="h-24 object-contain" />
      <h2 class="text-center font-bold mb-2">Módulo Estágios</h2>

      <label for="value" class="mt-2">Email</label>
      <InputText type="email" class="mb-2" v-model="state.email" />

      <label for="value" class="mt-2">Senha</label>
      <InputText type="password" class="mb-4" v-model="state.password" />

      <div class="flex items-center justify-end gap-2">
        <NuxtLink to="/login">
          <Button
            class="p-button-secondary"
            label="Voltar"
            icon="pi pi-arrow-left"
          ></Button>
        </NuxtLink>
        <Button
          class="p-button-primary"
          label="Login"
          icon="pi pi-sign-in"
          @click="handleLogin"
        ></Button>
      </div>
    </div>
  </div>
</template>

<style></style>

<style></style>
