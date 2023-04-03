export default class ZodErrorsService {
  getTranslatedErrors() {
    return fetch("/data/zodErrors.json").then((res) => res.json());
  }
}
