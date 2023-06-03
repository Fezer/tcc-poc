import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class SeguradoraService extends BaseService {
  async desativarSeguradora(id: number){
    const response = await $fetch(this.BASE_URL + "/seguradora/ativar-desativar/"+id, {
      method: "PUT",
      body:{
        ativa: "false"
      },
    });
    return response;
  }
  async ativarSeguradora(id: number){
    const response = await $fetch(this.BASE_URL + "/seguradora/ativar-desativar/"+id, {
      method: "PUT",
      body:{
        ativa: "true"
      },
    });
    return response;
  }
  async criarSeguradora(seguradora: Seguradora) {
    const response = await $fetch(this.BASE_URL + "/seguradora/", {
      method: "POST",
      body: {
        nome: seguradora.nome,
        seguradoraUfpr: seguradora.seguradoraUfpr,
        ativa: seguradora.ativa
      },
    });

    if (!response?.id) {
      throw new Error("Erro ao criar nova seguradora");
    }
    return response;
  }
  pegarAtivaSeverity(ativa:boolean){
    if(ativa){
      return "success";
    }else{
      return "danger";
    }
  }
  pegarAtivaValue(ativa:boolean){
    if(ativa){
      return "Sim";
    }else{
      return "Não";
    }
  }
}
