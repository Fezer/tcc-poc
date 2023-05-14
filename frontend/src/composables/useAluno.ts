import { Aluno } from "../types/Aluno";

const useAluno = () => {
  const aluno = useState<Aluno | void>("aluno", () => {});

  const setAluno = (alunoData: Aluno) => {
    // console.log("SETTING ALUNO");
    // console.log(alunoData);

    aluno.value = alunoData;
  };

  return {
    aluno,
    setAluno,
  };
};

export default useAluno;
