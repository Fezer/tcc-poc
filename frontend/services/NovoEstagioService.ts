import { PlanoAtividades, TipoEstagio } from "../src/types/NovoEstagio";
import BaseService from "./BaseService";

export default class NovoEstagioService extends BaseService {
  ALUNO = "GRR20201212";

  async criarNovoEstagio(): Promise<{ id: number }> {
    const response = await $fetch(
      this.BASE_URL + `/aluno/${this.ALUNO}/estagio`,
      {
        method: "POST",
      }
    );

    if (!response?.id) {
      throw new Error("Erro ao criar novo estágio");
    }

    return response;
  }

  async setTipoEstagio(id: number, tipoEstagio: TipoEstagio) {
    console.log("setTipoEstagio", id, tipoEstagio);
    if (!id || !tipoEstagio) {
      throw new Error("Id e tipo de estágio são obrigatórios");
    }
    return await $fetch(this.BASE_URL + `/estagio/tipo/${id}`, {
      method: "PUT",
      params: {
        tipoEstagio,
      },
    });
  }

  async setEstagioUfpr(id: number, isEstagioUfpr: boolean) {
    console.log("updateEstagio", id, isEstagioUfpr);
    if (!id || (!isEstagioUfpr && isEstagioUfpr !== false)) {
      throw new Error("Id e dados do estágio são obrigatórios");
    }
    return await $fetch(this.BASE_URL + `/estagio/ufpr/${id}`, {
      method: "PUT",
      params: {
        estagioUfpr: isEstagioUfpr,
      },
    });
  }

  public async getTermoEmPreenchimento(grr: string) {
    return await $fetch(
      `http://localhost:5000/aluno/${grr}/estagio/emPreenchimento`
    ).catch((err) => {
      console.error(err);
    });
  }

  public async setDadosEstagio(
    id: number,
    dadosEstagio: {
      dataInicio: string;
      dataFim: string;
      jornadaDiaria: number;
      jornadaSemanal: number;
      valorBolsa: number;
      valorTransporte: number;
    }
  ) {
    return await $fetch(this.BASE_URL + `/termo/${id}`, {
      method: "PUT",
      body: dadosEstagio,
    });
  }

  public async setAtividadesEstagio(id: string, atividades: PlanoAtividades) {
    return await $fetch(this.BASE_URL + `/termo/${id}/planoAtividades`, {
      method: "PUT",
      body: atividades,
    });
  }

  public async setOrientador(id: string, orientador: string) {
    return await $fetch(
      this.BASE_URL +
        `http://localhost:5000/termo/${id}/associarOrientador/${orientador}`,
      {
        method: "PUT",
      }
    );
  }

  public async setSupervisor(
    id: string,
    supervisor: {
      nome: string;
      telefone: string;
      formacao: string;
      cpf: string;
    }
  ) {
    return await $fetch("http://localhost:5000/supervisor/", {
      method: "POST",

      body: supervisor,
    }).then(async (res) => {
      console.log(res);
      if (!res?.id) throw new Error("Erro ao criar novo supervisor - No ID!");
      return await $fetch(
        this.BASE_URL + `/termo/${id}/associarSupervisor/${res?.id}`,

        {
          method: "PUT",
        }
      );
    });
  }

  public async solicitarAprovacaoTermo(id: string) {
    const grr = "GRR20201212";

    return await $fetch(
      this.BASE_URL + `/aluno/${grr}/termo/${id}/solicitarAprovacaoTermo`,
      {
        method: "PUT",
      }
    );
  }
}
