import BaseService from "./BaseService";

export default class CoeService extends BaseService {
  async aprovarTermo(id: number) {
    return await $fetch(`/coe/termo/${id}/aprovar`, {
      method: "PUT",
    });
  }

  async reprovarTermo(id: number, justificativa: string) {
    return await $fetch(`/coe/termo/${id}/indeferir`, {
      method: "PUT",
      body: {
        justificativa,
      },
    });
  }

  async solicitarAjustesTermo(id: number, descricaoAjustes: string) {
    return await $fetch(`/coe/termo/${id}/solicitarAjustes`, {
      method: "PUT",
      body: {
        descricaoAjustes,
      },
    });
  }

  async aprovarCertificado(id: number) {
    return await $fetch(`/coe/certificado/${id}/aprovar`, {
      method: "PUT",
    });
  }

  async reprovarCertificado(id: number, justificativa: string) {
    return await $fetch(`/coe/certificado/${id}/reprovar`, {
      method: "PUT",
      body: {
        justificativa,
      },
    });
  }
  // http://localhost:5000/coe/termoDeRescisao/2/darCiencia
  async cienciaTermoRescisao(termo: number) {
    return await $fetch(`/coe/termoDeRescisao/${termo}/darCiencia`, {
      method: "PUT",
    });
  }
}
