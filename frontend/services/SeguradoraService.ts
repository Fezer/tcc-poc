import Seguradora from "~~/src/types/Seguradora";
import BaseService from "./BaseService";

export default class SeguradoraService extends BaseService {
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
      console.log("entrou no if false");
      return "success";
    }else{
      console.log("entrou no if true");
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
