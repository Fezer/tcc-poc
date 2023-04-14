import { TipoEstagio } from "../src/types/NovoEstagio";
import BaseService from "./BaseService";

export default class NovoEstagioService extends BaseService {
  ALUNO = 0;

  async criarNovoEstagio(): Promise<{ id: number }> {
    const response = await $fetch(this.BASE_URL + "/estagio/novo", {
      method: "POST",
    });

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
}
