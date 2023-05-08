import BaseService from "./BaseService";

export default class CoafeService extends BaseService {
  async aprovarTermo(id: number) {
    return await $fetch(this.BASE_URL + `/coafe/termo/${id}/aprovar`, {
      method: "PUT",
    });
  }

  async reprovarTermo(id: number, justificativa: string) {
    return await $fetch(this.BASE_URL + `/coafe/termo/${id}/indeferir`, {
      method: "PUT",
      body: {
        justificativa,
      },
    });
  }

  async solicitarAjustesTermo(id: number, descricaoAjustes: string) {
    return await $fetch(this.BASE_URL + `/coafe/termo/${id}/solicitarAjustes`, {
      method: "PUT",
      body: {
        descricaoAjustes,
      },
    });
  }
}
