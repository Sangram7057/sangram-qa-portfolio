import { expect, Page } from "@playwright/test";

export class LoginPage {
  constructor(private readonly page: Page) {}

  async open(baseUrl?: string) {
    const targetUrl = baseUrl ? `${baseUrl}/login` : "/login";
    await this.page.goto(targetUrl, { waitUntil: "domcontentloaded" });
  }

  async login(username: string, password: string) {
    await this.page.getByLabel("Username").fill(username);
    await this.page.getByLabel("Password").fill(password);
    await this.page.getByRole("button", { name: "Sign in" }).click();
  }

  async expectVisible() {
    await expect(this.page.getByRole("button", { name: "Sign in" })).toBeVisible();
  }
}
