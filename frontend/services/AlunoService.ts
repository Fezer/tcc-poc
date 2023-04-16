import BaseService from "./BaseService";

export default class AlunoService extends BaseService {
  async getAluno(grr: string) {
    await fetch(`${this.BASE_URL}/aluno/${grr}`);
  }
}
