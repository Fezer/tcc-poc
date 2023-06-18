type Auth = {
  tipoUsuario: "COE" | "COAFE" | "Coordenacao" | "Orientador" | "aluno";
  token: string;
  // id do orientador ou grr do aluno
  id: string;
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
