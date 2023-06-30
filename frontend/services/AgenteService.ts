import Agente from "~~/src/types/Agente";
import BaseService from "./BaseService";

export default class AgenteService extends BaseService {
  async criarAgente(nome: string, cnpj: number, telefone: number) {
    const response = await $fetch("/agente-integrador/", {
      method: "POST",
      body: {
        nome: nome,
        cnpj: cnpj,
        telefone: telefone,
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao criar novo agente");
    } else {
      return response;
    }
  }
  async atualizaAgente(
    id: number,
    nome: string,
    cnpj: string,
    telefone: string
  ) {
    const response = await $fetch(`/agente-integrador/${id}`, {
      method: "PUT",
      body: {
        cnpj: cnpj,
        nome: nome,
        telefone: telefone,
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao atualizar o Agente");
    } else {
      return response;
    }
  }
  async getAgentes() {
    const response = await $fetch("/agente-integrador", {
      method: "GET",
      body: {},
    });
    if (!response) {
      throw new Error("Erro ao recuperar Agentes de Integração");
    } else {
      return response;
    }
  }
  async deletaAgente(id: number) {
    const response = await $fetch(`/agente-integrador/${id}`, {
      method: "DELETE",
      body: {},
    });
    if (response?.error) {
      throw new Error("Erro ao Deletar Agente");
    } else {
      return response;
    }
  }
  async baixarRelatorioEstagioPdfEspecifico(id: number) {
    return $fetch(`/coafe/gerar-relatorio-agenteIntegrador/${id}`, {
      method: "GET",
    });
  }
  async baixarRelatorioEstagioExcelEspecifico(id: number) {
    return $fetch(`/coafe/gerar-relatorio-agenteIntegrador-excel/${id}`, {
      method: "GET",
      responseType: "blob",
    });
  }
}
