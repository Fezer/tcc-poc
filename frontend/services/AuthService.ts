export default class AuthService {
  public async login({ email, password }: { email: string; password: string }) {
    return await $fetch(`/auth/login`, {
      method: "POST",
      body: {
        email,
        password,
      },
    });
  }
}
