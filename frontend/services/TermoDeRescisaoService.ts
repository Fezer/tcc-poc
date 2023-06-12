export default class TermoDeRescisaoService {
  public async atualizaTermoDeRecisao(
    termo: number,
    dados: {
      dataRecisao: string;
      periodoTotalRecesso: number;
    }
  ) {
    return await $fetch(`/termoDeRescisao/${termo}`, {
      method: "PUT",
      body: dados,
    });
  }

  public async criaPeriodoDeRecesso(
    termo: number,
    estagio: number,
    grr: string
  ) {
    return await $fetch(
      `/aluno/${grr}/estagio/${estagio}/termoDeRescisao/${termo}/periodoRecesso`,
      {
        method: "POST",
      }
    );
  }

  public async atualizaPeriodoDeRecesso(
    periodo: number,
    dados: {
      dataInicio: string;
      dataFim: string;
    }
  ) {
    return await $fetch(`/periodoRecesso/${periodo}`, {
      method: "PUT",
      body: dados,
    });
  }

  public async removePeriodoDeRecesso(
    grr: string,
    termo: number,
    estagio: number,
    periodo: number
  ) {
    return await $fetch(
      `/aluno/${grr}/estagio/${estagio}/termoDeRescisao/${termo}/periodoRecesso/${periodo}`,
      {
        method: "DELETE",
      }
    );
  }

  // http://localhost:5000/aluno/GRR20175486/estagio/1/termoDeRescisao/1/cancelarTermoDeRescisao
  public async cancelaTermoRecisao(
    grr: string,
    estagio: number,
    termo: number
  ) {
    return await $fetch(
      `/aluno/${grr}/estagio/${estagio}/termoDeRescisao/${termo}/cancelarTermoDeRescisao`,
      {
        method: "PUT",
      }
    );
  }

  public async uploadTermoDeRescisao(grr: string, termoData: FormData) {
    return await $fetch(`/aluno/${grr}/upload-termo-de-rescisao`, {
      method: "POST",
      body: termoData,
    });
  }
}
