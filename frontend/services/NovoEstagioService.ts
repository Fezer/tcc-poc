import { TipoEstagio } from "../src/types/NovoEstagio";
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
}
