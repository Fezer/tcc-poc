import Contratante from "~~/src/types/Contratante";
import BaseService from "./BaseService";

export default class ContratanteService extends BaseService {
  async criarContratante(contratante: Contratante) {
    const response = await $fetch(this.BASE_URL + "/contratante/novo", {
      method: "POST",
      body: {
        ...contratante,
        cnpj: contratante?.cnpj || null,
        cpf: contratante?.cpf || null,
        representanteEmpresa: "Teste",
        estagio: null,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar novo contratante");
    }

    return response;
  }
}
