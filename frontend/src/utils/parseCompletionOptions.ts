export default function parseCompletionOptions(
    value:
      | "Nao"
      | "Parcialmente"
      | "Integralmente"
  ): string {
    const parsedValues = {
      Nao: "Não",
      Parcialmente: "Parcialmente",
      Integralmente: "Integralmente",
    };
  
    return parsedValues[value] || "Opção não Reconhecida";
  }
  