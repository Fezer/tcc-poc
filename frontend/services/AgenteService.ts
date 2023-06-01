import Agente from "~~/src/types/Agente";
import BaseService from "./BaseService";

export default class AgenteService extends BaseService {
  async criarAgente(nome:string,cnpj:number,telefone:number) {
    const response = await $fetch(this.BASE_URL + "/agente-integrador/", {
      method: "POST",
      body: {
        nome : nome,
        cnpj : cnpj,
        telefone : telefone
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao criar novo agente");
    }else{
      return response;
    }
  }
  async atualizaAgente(id:number,nome:string,cnpj:string,telefone:string) {
    console.log(nome);
    console.log(cnpj);
    console.log(telefone);
    const response = await $fetch(this.BASE_URL + `/agente-integrador/${id}`, {
      method: "PUT",
      body: {
        cnpj: cnpj,   
        nome : nome,
        telefone: telefone,        
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao atualizar o gente");
    }else{
      console.log(response);
      return response;
    }
  }
  async getAgentes(){
    const response = await $fetch(this.BASE_URL+"/agente-integrador",{
      method: "GET",
      body: {},
    });
    if (!response){
      throw new Error("Erro ao recuperar Agentes de Integração");
    }else{
      console.log("chegou aqui! "+ response);
      return response;
    }
  }

}
