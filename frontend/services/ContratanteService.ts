import Contratante from "~~/src/types/Contratante";
import BaseService from "./BaseService";

export default class ContratanteService extends BaseService {
  async criarContratante(contratante: Contratante, termoID: number) {
    const response = await $fetch(this.BASE_URL + "/contratante/", {
      method: "POST",
      body: {
        ...contratante,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar novo contratante");
    }

    return response;
  }

  async criarEnderecoContratante(
    contratanteID: number,
    endereco: {
      logradouro: string;
      numero: number;
      complemento?: string;
      cidade: string;
      estado: string;
      cep: string;
    }
  ) {
    const response = await $fetch(
      this.BASE_URL + `/contratante/${contratanteID}/endereco/`,
      {
        method: "POST",
        body: {
          ...endereco,
        },
      }
    );

    if (!response?.id) {
      throw new Error("Erro ao criar novo endereço");
    }

    return response;
  }

  async listContratantes() {
    const response = await $fetch(this.BASE_URL + "/contratante/");

    return response;
  }
}
