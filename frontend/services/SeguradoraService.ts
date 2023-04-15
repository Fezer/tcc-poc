import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class SeguradoraService extends BaseService {
  async criarSeguradora(seguradora: Seguradora) {
    const response = await $fetch(this.BASE_URL + "/seguradora/novo", {
      method: "POST",
      body: {
        ...seguradora,
        termoDeEstagio: [
          {
            id: 1,
            nome: "Termo de Estágio",
          },
        ],
        estagio: [
          {
            id: 1,
            nome: "Estágio",
          },
        ],
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar nova seguradora");
    }

    return response;
  }
}
