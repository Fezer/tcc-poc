export default function parseStatusProcessos(
  status:
    | "Aprovado"
    | "Reprovado"
    | "Cancelado"
    | "EmRevisao"
    | "EmAprovacao"
    | "EmPreenchimento"
    | "Rescindido"
    | "Iniciado"
    | "Concluido"
): string {
  const parsedStatus = {
    Aprovado: "Aprovado",
    Reprovado: "Reprovado",
    Cancelado: "Cancelado",
    EmRevisao: "Em Revisão",
    EmAprovacao: "Em Aprovação",
    EmPreenchimento: "Em Preenchimento",
    Rescindido: "Rescindido",
    Iniciado: "Iniciado",
    Concluido: "Concluído",
  };

  return parsedStatus[status] || "Status não rechonhecido";
}
