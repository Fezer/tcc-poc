import Apolice from "~~/src/types/Apolice";
import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class ApoliceService extends BaseService {
  async criarApolice(apolice: Apolice, seguradora: Seguradora) {
    const response = await $fetch(`/seguradora/${seguradora.id}/apolice`, {
      method: "POST",
      body: {
        ...apolice,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar nova apolice");
    }

    return response;
  }
  async atualizaApolice(
    numero: number,
    dataInicio: string,
    dataFim: string,
    id: number,
    idseguradora: number
  ) {
    const response = await $fetch(`/apolice/${id}`, {
      method: "PUT",
      body: {
        numero: numero,
        dataInicio: dataInicio,
        dataFim: dataFim,
        seguradora: { id: idseguradora },
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao atualizar a Apólice");
    } else {
      return response;
    }
  }
  async deletaApolice(id: number) {
    const response = await $fetch(`/apolice/${id}`, {
      method: "DELETE",
      body: {},
    });
    if (response?.error) {
      throw new Error("Erro ao Deletar Apólice");
    } else {
      return response;
    }
  }
}
