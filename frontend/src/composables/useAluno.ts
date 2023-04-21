const useAluno = () => {
  const aluno = useState("aluno", () => {});

  const setAluno = (alunoData: any) => {
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
