import RelatorioEstagio from "~~/src/types/RelatorioEstagio";
import BaseService from "./BaseService";

export default class RelatorioEstagioService extends BaseService {
  public async atualizarRelatorioDeEstagio(
    id: number,

    relatorio: RelatorioEstagio
  ) {
    return $fetch(`${this.BASE_URL}/relatorioDeEstagio/${id}`, {
      method: "PUT",
      body: {
        relatorio,
      },
    });
  }

  public async definirTipoRelatorio(
    id: number,
    tipo: Pick<RelatorioEstagio, "tipo">
  ) {
    return $fetch(
      `${this.BASE_URL}/relatorioDeEstagio/${id}/definirTipo?tipoRelatorio=${tipo}`,
      {
        method: "PUT",
      }
    );
  }
}
