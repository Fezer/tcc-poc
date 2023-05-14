type DadosAuxiliares = {
  id: number;
  nomePai: string;
  estadoCivil: string | null;
  dependentes: number;
  grupoSanguineo: string | null;
  corDaPele: string | null;
  sexo: string;
  nomeMae: string;
  nacionalidade: string;
  dataDeChegadaNoPais: string | null;
  orgaoEmissor: string;
  uf: string;
  dataExpedicao: string | null;
  tituloEleitoral: string | null;
  zona: number;
  secao: number;
  certificadoMilitar: string | null;
  orgaoDeExpedicao: string | null;
  serie: string | null;
  dataDeEmissao: string;
  estadoNascimento: string;
  cidadeNascimento: string;
  corRaca: string;
  expressaoGenero: string;
  autoIdentificacaoGenero: string;
  orientacaoSexual: string;
  emailInstitucional: string | null;
};

type Endereco = {
  id: number;
  rua: string;
  numero: number;
  complemento: string;
  cidade: string;
  uf: string | null;
  cep: string;
};

type Aluno = {
  id: number;
  nome: string;
  telefone: string;
  endereco: Endereco;
  idDiscente: number;
  matricula: string;
  periodoAtual: number;
  rg: string;
  cpf: string;
  email: string;
  dataNascimento: string;
  turno: string;
  coordenador: string;
  idPrograma: string;
  ira: string;
  idCurso: number;
  dadosAuxiliares: DadosAuxiliares;
  pcd: boolean;
  matriculado: boolean;
};

export { Aluno, DadosAuxiliares, Endereco };
