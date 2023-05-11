export default interface RelatorioEstagio {
  avalAtividades: string;
  avalFormacaoProfissional: string;
  avalRelacoesInterpessoais: string;
  avalDesenvolvimentoAtividades: string;
  avalContribuicaoEstagio: string;
  avalEfetivacao: string;
  consideracoes: string;
  tipo: "RelatorioParcial" | "RelatorioFinal";

  cienciaOrientador?: boolean;

  estagio?: {
    id: number;
  };
}
