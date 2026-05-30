import { expect, Page } from "@playwright/test";

export class TransactionsPage {
  constructor(private readonly page: Page) {}

  async open(baseUrl?: string) {
    const targetUrl = baseUrl ? `${baseUrl}/transactions` : "/transactions";
    await this.page.goto(targetUrl, { waitUntil: "domcontentloaded" });
  }

  async filterByLast30Days() {
    await this.page.getByLabel("Date range").selectOption("last-30-days");
    await this.page.getByRole("button", { name: "Apply filters" }).click();
  }

  async expectFilteredResultsVisible() {
    await expect(this.page.getByRole("heading", { name: "Transactions" })).toBeVisible();
    await expect(this.page.getByTestId("transactions-table")).toBeVisible();
  }
}
