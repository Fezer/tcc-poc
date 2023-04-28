import Apolice from "~~/src/types/Apolice";
import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class ApoliceService extends BaseService {
  async criarApolice(apolice: Apolice, seguradora: Seguradora) {
    const response = await $fetch(
      this.BASE_URL + `/seguradora/${seguradora.id}/apolice`,
      {
        method: "POST",
        body: {
          ...apolice,
        },
      }
    );

    if (!response?.id) {
      throw new Error("Erro ao criar nova apolice");
    }

    return response;
  }
}
