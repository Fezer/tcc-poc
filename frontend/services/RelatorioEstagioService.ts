import RelatorioEstagio from "~~/src/types/RelatorioEstagio";
import BaseService from "./BaseService";

export default class RelatorioEstagioService extends BaseService {
  public async atualizarRelatorioDeEstagio(
    id: number,

    relatorio: RelatorioEstagio
  ) {
    return $fetch(`/relatorioDeEstagio/${id}`, {
      method: "PUT",
      body: {
        ...relatorio,
      },
    });
  }

  public async definirTipoRelatorio(
    id: number,
    tipo: Pick<RelatorioEstagio, "tipo">
  ) {
    return $fetch(
      `/relatorioDeEstagio/${id}/definirTipo?tipoRelatorio=${tipo}`,
      {
        method: "PUT",
      }
    );
  }

  public async cancelarRelatorioDeEstagio(id: number) {
    return $fetch(`/relatorioDeEstagio/${id}`, {
      method: "DELETE",
    });
  }
}
