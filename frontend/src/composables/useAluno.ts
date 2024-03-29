import { Aluno } from "../types/Aluno";

const useAluno = (): {
  aluno: Aluno | void;
  setAluno: (aluno: Aluno) => void;
} => {
  const aluno = useState<Aluno | void>("aluno", () => {});

  const setAluno = (alunoData: Aluno) => {
    aluno.value = alunoData;
  };

  return {
    aluno,
    setAluno,
  };
};

export default useAluno;
