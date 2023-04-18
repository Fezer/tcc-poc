export default interface Contratante {
  id: number;
  nome: string;
  tipoContratante: "PessoaFisica" | "PessoaJuridica";
  cpf?: string;
  cnpj?: string;
  representanteEmpresa: string;
}
