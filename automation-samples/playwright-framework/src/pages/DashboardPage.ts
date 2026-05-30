import { expect, Page } from "@playwright/test";

export class DashboardPage {
  constructor(private readonly page: Page) {}

  async expectLoaded() {
    await expect(this.page.getByRole("heading", { name: "Dashboard" })).toBeVisible();
    await expect(this.page.getByTestId("account-summary-card")).toBeVisible();
  }

  async expectNotificationsPanelVisible() {
    await expect(this.page.getByTestId("notifications-panel")).toBeVisible();
  }

  async signOut() {
    await this.page.getByRole("button", { name: "Open profile menu" }).click();
    await this.page.getByRole("menuitem", { name: "Sign out" }).click();
  }
}
