export default interface NovoEstagio {
  id: number | null;
  dadosTipoEstagio: {
    tipoEstagio: TipoEstagio;
    localEstagio: "UFPR" | "EXTERNO";
  } | null;
  tipoContratante: {
    tipoContratante: "PF" | "PJ";
  } | null;
  dadosEstagio: DadoEstagio | null;
  dadosAuxiliares: DadosAuxiliares | null;
}

export type PlanoAtividades = {
  local: string;
  descricaoAtividades: string;
};

export type DadosAuxiliares = {
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
};

export type DadoEstagio = {
  dataInicio: string;
  dataFinal: string;
  jornadaDiaria: number;
  jornadaSemanal: number;
  bolsaAuxilio: number;
  auxilioTransporte: number;
  nomeSupervisor: string;
  telefoneSupervisor: string;
};

export type TipoEstagio = "Obrigatorio" | "NaoObrigatorio";
