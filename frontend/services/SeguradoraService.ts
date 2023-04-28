import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class SeguradoraService extends BaseService {
  async criarSeguradora(seguradora: Seguradora, termoID: number) {
    const response = await $fetch(this.BASE_URL + "/seguradora/", {
      method: "POST",
      body: {
        ...seguradora,
        seguradoraUfpr: false,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar nova seguradora");
    }

    return response;
  }
}
