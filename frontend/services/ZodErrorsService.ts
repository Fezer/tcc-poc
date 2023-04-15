export default class ZodErrorsService {
  getTranslatedErrors(): Promise<Record<string, string>> {
    return fetch("/data/zodErrors.json").then((res) => res.json());
  }
}
