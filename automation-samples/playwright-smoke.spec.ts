import { expect, test } from "@playwright/test";

const baseUrl = process.env.BASE_URL ?? "https://example.test-app.local";
const username = process.env.TEST_USERNAME ?? "demo.user";
const password = process.env.TEST_PASSWORD ?? "replace-me";

test.describe("Dashboard smoke coverage", () => {
  test("valid user can sign in and view dashboard widgets", async ({ page }) => {
    await page.goto(`${baseUrl}/login`);

    await page.getByLabel("Username").fill(username);
    await page.getByLabel("Password").fill(password);
    await page.getByRole("button", { name: "Sign in" }).click();

    await expect(page).toHaveURL(/dashboard/);
    await expect(page.getByRole("heading", { name: "Dashboard" })).toBeVisible();
    await expect(page.getByTestId("account-summary-card")).toBeVisible();
    await expect(page.getByTestId("notifications-panel")).toBeVisible();
  });

  test("user can sign out cleanly from the header menu", async ({ page }) => {
    await page.goto(`${baseUrl}/dashboard`);

    await page.getByRole("button", { name: "Open profile menu" }).click();
    await page.getByRole("menuitem", { name: "Sign out" }).click();

    await expect(page).toHaveURL(/login/);
    await expect(page.getByRole("button", { name: "Sign in" })).toBeVisible();
  });
});
