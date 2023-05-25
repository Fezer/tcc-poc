export default function parseTipoTermo(
  tipo: "TermoDeCompromisso" | "TermoAditivo"
) {
  const tipos = {
    TermoDeCompromisso: "Termo de Compromisso",
    TermoAditivo: "Termo Aditivo",
  };

  return tipos[tipo] || "Tipo não rechonhecido";
}
