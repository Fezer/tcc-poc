export default interface NovoEstagio
  extends PlanoAtividades,
    DadosAuxiliares,
    DadoEstagio {
  id: number | null;
  tipoEstagio: TipoEstagio;
  estagioUfpr: boolean;
  supervisor: Supervisor;
}

export interface PlanoAtividades {
  local: string;
  descricaoAtividades: string;
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
