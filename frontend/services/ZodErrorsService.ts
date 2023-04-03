export default class ZodErrorsService {
  getCountries() {
    return fetch("/data/zodErrors.json").then((res) => res.json());
  }
}
