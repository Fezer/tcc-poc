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
  // http://localhost:5000/aluno/GRR20204481/relatorio/1/gerar-relatorio
  public async baixarRelatorioBase(grr: string, id: number) {
    return $fetch(`/aluno/${grr}/relatorio/${id}/gerar-relatorio`, {
      method: "GET",
    });
  }

  // http://localhost:5000/aluno/GRR20204481/upload-relatorio
  public async uploadRelatorio(grr: string, dados: FormData) {
    return $fetch(`/aluno/${grr}/upload-relatorio`, {
      method: "POST",
      body: dados,
    });
  }
  async baixarRelatorioEstagioPdf() {
    return $fetch(`/coafe/gerar-relatorios-relatorioDeEstagio`, {
      method: "GET",
    });
  }
  async baixarRelatorioEstagioPdfEspecifico(id:number) {
    return $fetch(`/coafe/gerar-relatorio-relatorioDeEstagio/${id}`, {
      method: "GET",
    });
  }
  async baixarRelatorioEstagioExcelEspecifico(id:number) {
    return $fetch(`/coafe/gerar-relatorio-relatorioDeEstagio-excel/${id}`, {
      method: "GET",
      responseType: "blob",
    });
  }
  async baixarRelatorioEstagioExcel() {
    return $fetch(`/coafe/gerar-relatorios-relatorioDeEstagio-excel`, {
      method: "GET",
      responseType: "blob",
    });
  }
}
