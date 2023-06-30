export default class BaseService {
  protected BASE_URL = "http://localhost:5000";

  protected async authFetch(url: string, options: any = {}) {
    const accessToken = localStorage.getItem("accessToken");

    return await $fetch(url, {
      ...options,
      headers: {
        ...options.headers,
        Authorization: `Bearer ${accessToken}`,
      },
    });
  }

  protected ALUNO = "GRR20200141";
}
