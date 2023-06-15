export default interface Contratante {
  id?: number;
  nome?: string;
  tipo?: "PessoaFisica" | "PessoaJuridica";
  cpf?: string;
  cnpj?: string;
  representanteEmpresa?: string;
}
