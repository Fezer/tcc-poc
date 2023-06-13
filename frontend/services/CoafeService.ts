import BaseService from "./BaseService";

export default class CoafeService extends BaseService {
  async aprovarTermo(id: number) {
    return await $fetch(`/coafe/termo/${id}/aprovar`, {
      method: "PUT",
    });
  }

  async reprovarTermo(id: number, justificativa: string) {
    return await $fetch(`/coafe/termo/${id}/indeferir`, {
      method: "PUT",
      body: {
        justificativa,
      },
    });
  }

  async solicitarAjustesTermo(id: number, descricaoAjustes: string) {
    return await $fetch(`/coafe/termo/${id}/solicitarAjustes`, {
      method: "PUT",
      body: {
        descricaoAjustes,
      },
    });
  }

  // http://localhost:5000/termo/13/associarAgenteIntegrador/53

  async associarAgenteIntegradorAoEstagio(
    estagio: number,
    agenteIntegrador: number
  ) {
    return await $fetch(
      `/termo/${estagio}/associarAgenteIntegrador/${agenteIntegrador}`,
      {
        method: "PUT",
      }
    );
  }

  async cienciaTermoRescisao(termo: number) {
    return await $fetch(`/coafe/termoDeRescisao/${termo}/darCiencia`, {
      method: "PUT",
    });
  }
}
