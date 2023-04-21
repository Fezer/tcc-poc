import BaseService from "./BaseService";

export default class AlunoService extends BaseService {
  public async getAlunoFromSiga(grr: string) {
    return await $fetch(`${this.BASE_URL}/siga/aluno?grr=${grr}`).catch((err) =>
      console.error(err)
    );
  }
}