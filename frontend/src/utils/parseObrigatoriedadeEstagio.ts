const parseObrigatoriedadeEstagio = (ob: "NaoObrigatorio" | "Obrigatorio") => {
  return ob === "Obrigatorio" ? "Obrigatório" : "Não Obrigatório";
};

export default parseObrigatoriedadeEstagio;
