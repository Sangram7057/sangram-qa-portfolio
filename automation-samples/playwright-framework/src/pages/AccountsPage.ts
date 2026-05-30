import { expect, Page } from "@playwright/test";

export class AccountsPage {
  constructor(private readonly page: Page) {}

  async open(baseUrl?: string) {
    const targetUrl = baseUrl ? `${baseUrl}/accounts` : "/accounts";
    await this.page.goto(targetUrl, { waitUntil: "domcontentloaded" });
  }

  async search(term: string) {
    await this.page.getByLabel("Search accounts").fill(term);
  }

  async expectSearchTerm(term: string) {
    await expect(this.page.getByLabel("Search accounts")).toHaveValue(term);
  }

  async expectResultsVisible() {
    await expect(this.page.getByRole("heading", { name: "Accounts" })).toBeVisible();
    await expect(this.page.getByTestId("account-grid")).toBeVisible();
  }
}
