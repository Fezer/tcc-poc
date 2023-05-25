import NovoEstagio from "./NovoEstagio";
import Seguradora from "./Seguradora";

type StatusTermo =
  | "EmAprovacao"
  | "Aprovado"
  | "Reprovado"
  | "EmRevisao"
  | "EmPreenchimento";

interface BaseTermo {
  id: number;
  tipoTermoDeEstagio: "TermoAditivo" | "TermoDeCompromisso" | "TermoDeRecisao";
  dataInicio: string;
  dataTermino: string;
  jornadaDiaria: number;
  jornadaSemanal: number;
  valorBolsa: number;
  valorTransporte: number;
  dataFimSuspensao: string | null;
  dataInicioRetomada: string | null;
  dataCriacao: string;
  statusTermo: StatusTermo;
  etapaFluxo: "COAFE" | "COE" | "Coordenacao";
  parecerCOE: string | null;
  parecerCOAFE: string | null;
  motivoIndeferimento: string | null;
  descricaoAjustes: string | null;
}

export { StatusTermo, BaseTermo };
