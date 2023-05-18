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
      console.log('deu erro :( ')
      throw new Error("Erro ao criar novo agente");
    }else{
      return response;
    }
  }
  async atualizaAgente(agente:Agente) {
    const response = await $fetch(this.BASE_URL + `/agente-integrador/${agente.id}`, {
      method: "PUT",
      body: {
        nome : agente.nome,
        convenio: agente.convenio,
      },
    });
    if (!response?.id) {
      throw new Error("Erro ao criar novo agente");
    }else{
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
