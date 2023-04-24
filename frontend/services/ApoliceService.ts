import Apolice from "~~/src/types/Apolice";
import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class ApoliceService extends BaseService {
  async criarApolice(
    apolice: Apolice,
    seguradora: Seguradora,
    termoID: number
  ) {
    const response = await $fetch(this.BASE_URL + "/apolice/novo", {
      method: "POST",
      body: {
        ...apolice,
        seguradora: seguradora,
        termoDeEstagio: {
          id: termoID,
          nome: "Termo de Estágio",
        },
        estagio: {
          id: termoID,
          nome: "Estágio",
        },
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar nova apolice");
    }

    return response;
  }
}
