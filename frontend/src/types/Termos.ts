import Agente from "./Agente";
import Apolice from "./Apolice";
import Contratante from "./Contratante";
import { BasicEstagio, PlanoAtividades } from "./NovoEstagio";
import Orientador from "./Orientador";
import Seguradora from "./Seguradora";

type StatusTermo =
  | "EmAprovacao"
  | "Aprovado"
  | "Reprovado"
  | "EmRevisao"
  | "EmPreenchimento"
  | "Cancelado";

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

  orientador?: Orientador;
  agenteIntegrador?: Agente;
  planoAtividades?: PlanoAtividades;
  cienciaCoordenacao?: {
    id: number;
    cienciaIRA: boolean;
    cienciaPlanoAtividades: boolean;
    cienciaFormacaoSupervisor: boolean;
  };

  contratante?: Contratante;

  apolice?: Apolice;
  seguradora?: Seguradora;
  estagio?: BasicEstagio;
}

interface TermoRescisao {
  id: number;
  dataTermino: string;
  periodoTotalRecesso: number;

  periodoRecesso: PeriodoRecesso[];

  estagio: BasicEstagio;

  cienciaCOE?: boolean;
  cienciaCoordenacao?: boolean;
  cienciaCOAFE?: boolean;
  cienciaOrientador?: boolean;
}

type PeriodoRecesso = {
  id: number;
  dataInicio: string;
  dataFim: string;
};

type PaginatedTermo = {
  content: BaseTermo[];
  totalElements: number;
  totalPages: number;
};

export {
  StatusTermo,
  BaseTermo,
  TermoRescisao,
  PeriodoRecesso,
  PaginatedTermo,
};
