import BaseService from "./BaseService";

export default class CoordService extends BaseService {
  async aprovarTermo(id: number) {
    return await $fetch(`/coordenacao/termo/${id}/aprovar`, {
      method: "PUT",
    });
  }

  async reprovarTermo(id: number, justificativa: string) {
    return await $fetch(`/coordenacao/termo/${id}/indeferir`, {
      method: "PUT",
      body: {
        justificativa,
      },
    });
  }

  async solicitarAjustesTermo(id: number, descricaoAjustes: string) {
    return await $fetch(`/coordenacao/termo/${id}/solicitarAjustes`, {
      method: "PUT",
      body: {
        descricaoAjustes,
      },
    });
  }

  async darCienciaFormacaoSupervisor(id: number) {
    return await $fetch(`/coordenacao/termo/${id}/cienciaFormacaoSupervisor`, {
      method: "PUT",
    });
  }

  async darCienciaIRA(id: number) {
    return await $fetch(`/coordenacao/termo/${id}/cienciaIra`, {
      method: "PUT",
    });
  }

  async darCienciaPlanoDeAtividades(id: number) {
    return await $fetch(`/coordenacao/termo/${id}/cienciaPlanoAtividades`, {
      method: "PUT",
    });
  }

  async cienciaTermoRescisao(termo: number) {
    return await $fetch(`/coordenacao/termoDeRescisao/${termo}/darCiencia`, {
      method: "PUT",
    });
  }

  async cienciaIndeferimentoTermo(termo: number) {
    return await $fetch(`/coordenacao/termo/${termo}/cienciaIndeferimento`, {
      method: "PUT",
    });
  }
}
