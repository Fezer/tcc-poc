import Convenio from "~~/src/types/Convenio";
import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class ConvenioService extends BaseService {
  async criarConvenio(
    numero: number,
    descricao: string,
    dataInicio: Date,
    dataFim: Date,
    idagente: number
  ) {
    const response = await $fetch(`/agente-integrador/${idagente}/convenio`, {
      method: "POST",
      body: {
        numero: numero,
        descricao: descricao,
        dataInicio: dataInicio,
        dataFim: dataFim,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao adicionar convênio");
    }
    return response;
  }
  async atualizaConvenio(
    numero: number,
    descricao: string,
    dataInicio: string,
    dataFim: string,
    id: number,
    idagente: number
  ) {
    const response = await $fetch(`/convenio/${id}`, {
      method: "PUT",
      body: {
        numero: numero,
        descricao: descricao,
        dataInicio: dataInicio,
        dataFim: dataFim,
        agenteIntegrador: { id: idagente },
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao atualizar o Convênio");
    } else {
      console.log(response);
      return response;
    }
  }
  async deletaConvenio(id: number) {
    const response = await $fetch(`/convenio/${id}`, {
      method: "DELETE",
      body: {},
    });
    console.log("Resposta do servidor: " + response);
    if (response?.error) {
      throw new Error("Erro ao Deletar Convênio");
    } else {
      console.log(response);
      return response;
    }
  }
}
