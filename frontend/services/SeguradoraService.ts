import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class SeguradoraService {
  async desativarSeguradora(id: number) {
    const response = await $fetch("/seguradora/ativar-desativar/" + id, {
      method: "PUT",
      body: {
        ativa: "false",
      },
    });
    return response;
  }
  async ativarSeguradora(id: number) {
    const response = await $fetch("/seguradora/ativar-desativar/" + id, {
      method: "PUT",
      body: {
        ativa: "true",
      },
    });
    return response;
  }
  async criarSeguradora(seguradora: Seguradora) {
    const response = await $fetch("/seguradora/", {
      method: "POST",
      body: {
        nome: seguradora.nome,
        seguradoraUfpr: seguradora.seguradoraUfpr,
        ativa: seguradora.ativa,
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar nova seguradora");
    }
    return response;
  }
  pegarAtivaSeverity(ativa: boolean) {
    if (ativa) {
      return "success";
    } else {
      return "danger";
    }
  }
  pegarAtivaValue(ativa: boolean) {
    if (ativa) {
      return "Sim";
    } else {
      return "Não";
    }
  }
  async atualizaSeguradora(id: number, nome: string, ativa: boolean) {
    const response = await $fetch(`/seguradora/${id}`, {
      method: "PUT",
      body: {
        nome: nome,
        ativa: ativa,
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao atualizar a Seguradora");
    } else {
      console.log(response);
      return response;
    }
  }
  async deletaSeguradora(id: number) {
    const response = await $fetch(`/seguradora/${id}`, {
      method: "DELETE",
      body: {},
    });
    if (response?.error) {
      throw new Error("Erro ao Deletar Seguradora");
    } else {
      console.log(response);
      return response;
    }
  }
 async baixarRelatorioPdf() {
    return $fetch(`/coafe/gerar-relatorio-seguradora-ufpr`, {
      method: "GET",
    });
  }
  async baixarRelatorioExcel() {
    return $fetch(`/coafe/gerar-relatorio-seguradora-ufpr-excel`, {
      method: "GET",
      responseType: "blob",
    });
  }
}
