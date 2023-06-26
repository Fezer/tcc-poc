export default function parseStatusProcessos(
  status:
    | "Aprovado"
    | "Reprovado"
    | "Cancelado"
    | "EmRevisao"
    | "EmAprovacao"
    | "EmPreenchimento"
    | "Rescindido"
): string {
  const parsedStatus = {
    Aprovado: "Aprovado",
    Reprovado: "Reprovado",
    Cancelado: "Cancelado",
    EmRevisao: "Em Revisão",
    EmAprovacao: "Em Aprovação",
    EmPreenchimento: "Em Preenchimento",
    Rescindido: "Rescindido",
  };

  return parsedStatus[status] || "Status não rechonhecido";
}
