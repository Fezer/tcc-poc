import BaseService from "./BaseService";

export default class CoeService extends BaseService {
  async aprovarTermo(id: number) {
    return await $fetch(this.BASE_URL + `/coe/termo/${id}/aprovar`, {
      method: "PUT",
    });
  }

  async reprovarTermo(id: number, justificativa: string) {
    return await $fetch(this.BASE_URL + `/coe/termo/${id}/indeferir`, {
      method: "PUT",
      body: {
        justificativa,
      },
    });
  }

  async solicitarAjustesTermo(id: number, descricaoAjustes: string) {
    return await $fetch(this.BASE_URL + `/coe/termo/${id}/solicitarAjustes`, {
      method: "PUT",
      body: {
        descricaoAjustes,
      },
    });
  }
}
