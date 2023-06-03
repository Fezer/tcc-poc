import { StatusTermo } from "./Termos";

export default interface NovoEstagio
  extends BasicEstagio,
    PlanoAtividades,
    DadosAuxiliares,
    DadoEstagio {}

export interface Estagio extends BasicEstagio, PlanoAtividades, DadoEstagio {
  termoDeCompromisso: number;
  termoAdivito: number[];
  termoDeRescisao?: number;
  fichaDeAvaliacao?: number;
  certificadoDeEstagio?: number;
  dataCriacao: string;
  relatorioDeEstagio?: number[];

  statusEstagio?: StatusTermo;
}

export interface BasicEstagio {
  id: number | null;
  tipoEstagio: TipoEstagio;
  estagioUfpr: boolean;
  estagioSeed: boolean;
}

export interface PlanoAtividades {
  local: string;
  descricaoAtividades: string;
  nomeSupervisor: string;
  telefoneSupervisor: string;
  cpfSupervisor: string;
  formacaoSupervisor: string;
}
export interface DadosAuxiliares {
  tipoVaga: "amplaConcorrencia" | "negros" | "pcd";
  grupoSanguineo: string;
  banco: string;
  numAgencia: string;
  numConta: string;
  nomeAgencia: string;
  cidadeAgencia: string;
  enderecoAgencia: string;
  bairroAgencia: string;
  certificadoMilitar: string;
  orgaoExpedicaoCertMilitar: string;
  serieCertMilitar: string;
}

export interface DadoEstagio {
  dataInicio: string;
  dataFinal: string;
  dataTermino: string;
  jornadaDiaria: number;
  jornadaSemanal: number;
  bolsaAuxilio: number;
  auxilioTransporte: number;
  nomeSupervisor: string;
  telefoneSupervisor: string;
}

export type TipoEstagio = "Obrigatorio" | "NaoObrigatorio";

export type Supervisor = {
  nome: string;
  cpf: string;
  telefone: string;
  formacao: string;
};
