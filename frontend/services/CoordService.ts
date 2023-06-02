import BaseService from "./BaseService";

export default class CoordService extends BaseService {
  async aprovarTermo(id: number) {
    return await $fetch(this.BASE_URL + `/coordenacao/termo/${id}/aprovar`, {
      method: "PUT",
    });
  }

  async reprovarTermo(id: number, justificativa: string) {
    return await $fetch(this.BASE_URL + `/coordenacao/termo/${id}/indeferir`, {
      method: "PUT",
      body: {
        justificativa,
      },
    });
  }

  async solicitarAjustesTermo(id: number, descricaoAjustes: string) {
    return await $fetch(
      this.BASE_URL + `/coordenacao/termo/${id}/solicitarAjustes`,
      {
        method: "PUT",
        body: {
          descricaoAjustes,
        },
      }
    );
  }

  async darCienciaFormacaoSupervisor(id: number) {
    return await $fetch(
      this.BASE_URL + `/coordenacao/termo/${id}/cienciaFormacaoSupervisor`,
      {
        method: "PUT",
      }
    );
  }

  async darCienciaIRA(id: number) {
    return await $fetch(this.BASE_URL + `/coordenacao/termo/${id}/cienciaIra`, {
      method: "PUT",
    });
  }

  async darCienciaPlanoDeAtividades(id: number) {
    return await $fetch(
      this.BASE_URL + `/coordenacao/termo/${id}/cienciaPlanoAtividades`,
      {
        method: "PUT",
      }
    );
  }

  async cienciaTermoRescisao(termo: number) {
    return await $fetch(
      this.BASE_URL + `/coordenacao/termoDeRescisao/${termo}/darCiencia`,
      {
        method: "PUT",
      }
    );
  }
}
