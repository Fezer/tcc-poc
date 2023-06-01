import RelatorioEstagio from "~~/src/types/RelatorioEstagio";
import BaseService from "./BaseService";
import { BaseTermo } from "~~/src/types/Termos";

export default class AlunoService extends BaseService {
  public async getAlunoFromSiga(grr: string) {
    return await $fetch(`/aluno/${grr}`).catch((err) => console.error(err));
  }

  public async getCursoAlunoFromSiga(idCurso: string) {
    return await $fetch(`/curso/${idCurso}`).catch((err) => console.error(err));
  }

  public async getAlunoFullFromSiga(grr: string) {
    return await $fetch(`/siga/aluno?grr=${grr}`).catch((err) =>
      console.error(err)
    );
  }

  public async getEstagioEmAndamento(grr: string) {
    return await $fetch(`/aluno/${grr}/estagio/emProgresso`).catch((err) =>
      console.error(err)
    );
  }

  public async novoRelatorioDeEstagio(grr: string, estagio: number) {
    return await $fetch(`/aluno/${grr}/estagio/${estagio}/relatorioDeEstagio`, {
      method: "POST",
    });
  }

  // http://localhost:5000/aluno/GRR20175486/estagio/17/relatorioDeEstagio/7/solicitarCiencia
  public async solicitarCienciaDeRelatorioDeEstagio(
    grr: string,
    estagio: number,
    relatorio: number
  ) {
    return await $fetch(
      `/aluno/${grr}/estagio/${estagio}/relatorioDeEstagio/${relatorio}/solicitarCiencia`,
      {
        method: "PUT",
      }
    );
  }

  public async atualizaDadosAuxiliares(
    grr: string,
    dados: {
      estadoCivil: string;
      dependente: number;
      grupoSanguineo: string;
      dataChegada: Date;
      dataExpedicao: Date;
      tituloEleitoral: string;
      zona: number;
      secao: number;
      certificadoMilitar: string;
      orgaoExpedicao: string;
      serie: string;
      emailInstitucional: string;
    }
  ) {
    return await $fetch(`/aluno/${grr}/dadosAuxiliares`, {
      method: "PUT",
      body: {
        ...dados,
      },
    });
  }

  public async criarFichaDeAvaliacao(grr: string, estagio: number) {
    return await $fetch(`/aluno/${grr}/estagio/${estagio}/fichaDeAvaliacao`, {
      method: "POST",
    });
  }

  public async solicitarCertificadoEstagio(grr: string, estagio: number) {
    return await $fetch(
      `/aluno/${grr}/estagio/${estagio}/certificadoDeEstagio`,
      {
        method: "POST",
      }
    );
  }

  public async criaDadosBancarios(grr: string, dados: any) {
    return await $fetch(`/aluno/${grr}/dadosBancarios`, {
      method: "POST",
      body: {
        ...dados,
      },
    });
  }

  public async uploadTermo(grr: string, termoData: FormData) {
    return await $fetch(`/aluno/${grr}/upload-termo`, {
      method: "POST",
      body: termoData,
    });
  }
  // http://localhost:5000/aluno/GRR20175486/estagio/termoCompromisso?statusTermo=EmRevisao

  public async getEstagioEmRevisao(grr: string) {
    return await $fetch(
      `/aluno/${grr}/estagio/termoCompromisso?statusTermo=EmRevisao`
    );
  }

  public async criarTermoAditivo(grr: string, estagio: number) {
    return await $fetch(`/aluno/${grr}/estagio/${estagio}/termoAditivo`, {
      method: "POST",
    });
  }

  public async cancelarTermoAditivo(
    grr: string,
    termo: number,
    estagio: number
  ) {
    return await $fetch(
      `/aluno/${grr}/estagio/${estagio}/termoAditivo/${termo}/cancelarTermoAditivo`,
      {
        method: "PUT",
      }
    );
  }

  public async getTermoAditivoAtivo(grr: string): Promise<boolean> {
    return await $fetch(`/aluno/${grr}/termoAditivo/`)
      .then((response: BaseTermo[]) => {
        return response?.some((termo: BaseTermo) =>
          ["EmPreenchimento", "EmRevisao", "EmAprovacao"].includes(
            termo.statusTermo
          )
        );
      })
      .catch((err) => {
        console.error(err);
        return false;
      });
  }

  public async criarTermoDeRecisao(grr: string, estagio: number) {
    return await $fetch(`/aluno/${grr}/estagio/${estagio}/termoDeRescisao`, {
      method: "POST",
    });
  }

  public async solicitarCienciaDeTermoDeRecisao(
    grr: string,
    estagio: number,
    termo: number
  ) {
    return await $fetch(
      `/aluno/${grr}/estagio/${estagio}/termoDeRescisao/${termo}/solicitarCiencia`,
      {
        method: "PUT",
      }
    );
  }
}
