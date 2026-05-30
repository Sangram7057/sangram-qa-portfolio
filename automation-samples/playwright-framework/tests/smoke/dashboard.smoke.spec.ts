import { expect, test } from "@playwright/test";
import { users } from "../../src/data/test-users";
import { DashboardPage } from "../../src/pages/DashboardPage";
import { LoginPage } from "../../src/pages/LoginPage";

test.describe("Smoke suite", () => {
  test("valid user can sign in and view dashboard summary", async ({ page }) => {
    const loginPage = new LoginPage(page);
    const dashboardPage = new DashboardPage(page);

    await loginPage.open();
    await loginPage.login(users.validUser.username, users.validUser.password);
    await dashboardPage.expectLoaded();

    await expect(page).toHaveURL(/dashboard/);
  });
});
