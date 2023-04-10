export default interface NovoEstagio {
  id: number | null;
  dadosTipoEstagio: {
    tipoEstagio: TipoEstagio;
    localEstagio: "UFPR" | "EXTERNO";
  } | null;
  tipoContratante: {
    tipoContratante: "PF" | "PJ";
  } | null;
  dadosEstagio: {
    dataInicio: string;
    dataFinal: string;
    jornadaDiaria: number;
    jornadaSemanal: number;
    bolsaAuxilio: number;
    auxilioTransporte: number;
    coordenador: string;
    orientador: number;
    nomeSupervisor: string;
    telefoneSupervisor: string;
    atividades: string;
  } | null;
  dadosAuxiliares: {
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
  } | null;
}

export type TipoEstagio = "Obrigatorio" | "NaoObrigatorio";
