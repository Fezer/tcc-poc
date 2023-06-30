import Contratante from "~~/src/types/Contratante";
import BaseService from "./BaseService";

export default class ContratanteService extends BaseService {
  async criarContratante(contratante: Contratante, termoID: number) {
    const response = await $fetch("/contratante/", {
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
    const response = await $fetch(`/contratante/${contratanteID}/endereco/`, {
      method: "POST",
      body: {
        ...endereco,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar novo endereço");
    }

    return response;
  }

  async listContratantes() {
    const response = await $fetch("/contratante/");

    return response;
  }

  async getContratantePerNome(nome: string) {
    const response = await $fetch(`/contratante/nome/contendo/${nome}`);

    return response;
  }

  async atualizaContratante(
    id?: number,
    nome?: string,
    tipo?: string,
    cpf?: string,
    cnpj?: string,
    representanteEmpresa?: string,
    telefone?: string
  ) {
    const response = await $fetch(`/contratante/${id}`, {
      method: "PUT",
      body: {
        nome,
        tipo,
        cpf,
        cnpj,
        representanteEmpresa,
        telefone,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao atualizar contratante");
    }

    return response;
  }

  async getContratantePerId(id: number) {
    const response = await $fetch(`/contratante/${id}`);

    return response;
  }
  async baixarRelatorioEstagioPdfEspecifico(id: number) {
    return $fetch(`/coafe/gerar-relatorio-empresa/${id}`, {
      method: "GET",
    });
  }
  async baixarRelatorioEstagioExcelEspecifico(id: number) {
    return $fetch(`/coafe/gerar-relatorio-empresa-excel/${id}`, {
      method: "GET",
      responseType: "blob",
    });
  }
}
