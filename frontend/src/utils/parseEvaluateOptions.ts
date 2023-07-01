export default function parseEvaluateOptions(
    value:
      | "Pessimo"
      | "Ruim"
      | "Razoavel"
      | "Bom"
      | "Excelente"

  ): string {
    const parsedValues = {
      Pessimo: "Péssimo",
      Ruim: "Ruim",
      Razoavel: "Razoável",
      Bom: "Bom",
      Excelente: "Excelente",

    };
  
    return parsedValues[value] || "Opção não Reconhecida";
  }
  