import { expect, Page } from "@playwright/test";

export class ProfilePage {
  constructor(private readonly page: Page) {}

  async open(baseUrl?: string) {
    const targetUrl = baseUrl ? `${baseUrl}/profile` : "/profile";
    await this.page.goto(targetUrl, { waitUntil: "domcontentloaded" });
  }

  async expectLoaded() {
    await expect(this.page.getByRole("heading", { name: "Profile" })).toBeVisible();
    await expect(this.page.getByTestId("profile-summary-card")).toBeVisible();
  }
}
