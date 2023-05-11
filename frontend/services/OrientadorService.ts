import BaseService from "./BaseService";

export default class OrientadorService extends BaseService {
  // http://localhost:5000/orientador/452/relatorioDeEstagio/7/darCiencia
  public async cienciaRelatorioEstagio(orientador: number, relatorio: number) {
    return await $fetch(
      `${this.BASE_URL}/orientador/${orientador}/relatorioDeEstagio/${relatorio}/darCiencia`,
      {
        method: "PUT",
      }
    );
  }
}
