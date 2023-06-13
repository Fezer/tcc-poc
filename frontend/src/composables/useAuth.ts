type Auth = {
  perfil: "coe" | "coafe" | "coordenacao" | "orientador" | "aluno";
  token: string;
  grr?: string;
  idOrientador?: string;
};

export default function useAuth() {
  const auth = useState<Auth>("auth", () => {});

  const setAuth = (authData: Auth) => {
    auth.value = authData;
  };

  return {
    auth,
    setAuth,
  };
}
