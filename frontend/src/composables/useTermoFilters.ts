export default function useTermoFilters() {
  const statusOptions = [
    {
      label: "Em Preenchimento",
      value: "EmPreenchimento",
    },
    {
      label: "Em Aprovação",
      value: "EmAprovacao",
    },
    {
      label: "Aprovado",
      value: "Aprovado",
    },
    {
      label: "Cancelado",
      value: "Cancelado",
    },
    {
      label: "Reprovado",
      value: "Reprovado",
    },
    {
      label: "Em Revisão",
      value: "EmRevisao",
    },
    {
      label: "Qualquer",
      value: "",
    },
  ];

  const etapaOptions = [
    {
      label: "Aluno",
      value: "Aluno",
    },
    {
      label: "Coordenação",
      value: "Coordenacao",
    },
    {
      label: "COE",
      value: "COE",
    },
    {
      label: "COAFE",
      value: "COAFE",
    },
    {
      label: "Orientador",
      value: "Orientador",
    },
    {
      label: "Qualquer",
      value: "",
    },
  ];

  const tipoOptions = [
    {
      label: "Obrigatório",
      value: "Obrigatorio",
    },
    {
      label: "Não Obrigatório",
      value: "NaoObrigatorio",
    },
    {
      label: "Qualquer",
      value: "",
    },
  ];

  return {
    statusOptions,
    etapaOptions,
    tipoOptions,
  };
}
