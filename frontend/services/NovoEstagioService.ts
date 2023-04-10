import { TipoEstagio } from "../src/types/NovoEstagio";

export default class NovoEstagioService {
  ALUNO = 0;

  private BASE_URL = "http://localhost:5000";

  async criarNovoEstagio(): Promise<{ id: number }> {
    const response = await $fetch(this.BASE_URL + "/estagio/novo", {
      method: "POST",
    });

    console.log(response, response?.id);

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
}
