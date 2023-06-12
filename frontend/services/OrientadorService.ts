import BaseService from "./BaseService";

export default class OrientadorService extends BaseService {
  // http://localhost:5000/orientador/452/relatorioDeEstagio/7/darCiencia
  public async cienciaRelatorioEstagio(orientador: number, relatorio: number) {
    return await $fetch(
      `/orientador/${orientador}/relatorioDeEstagio/${relatorio}/darCiencia`,
      {
        method: "PUT",
      }
    );
  }

  async cienciaTermoRescisao(orientador: number, termo: number) {
    return await $fetch(
      `/orientador/${orientador}/termoDeRescisao/${termo}/darCiencia`,
      {
        method: "PUT",
      }
    );
  }
}
