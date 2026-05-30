import { expect, Page } from "@playwright/test";

export class TransactionsPage {
  constructor(private readonly page: Page) {}

  async open(baseUrl?: string) {
    const targetUrl = baseUrl ? `${baseUrl}/transactions` : "/transactions";
    await this.page.goto(targetUrl, { waitUntil: "domcontentloaded" });
  }

  async filterByDateRange(range: string) {
    await this.page.getByLabel("Date range").selectOption(range);
    await this.page.getByRole("button", { name: "Apply filters" }).click();
  }

  async filterByLast30Days() {
    await this.filterByDateRange("last-30-days");
  }

  async expectDateRange(range: string) {
    await expect(this.page.getByLabel("Date range")).toHaveValue(range);
  }

  async expectFilteredResultsVisible() {
    await expect(this.page.getByRole("heading", { name: "Transactions" })).toBeVisible();
    await expect(this.page.getByTestId("transactions-table")).toBeVisible();
  }
}
