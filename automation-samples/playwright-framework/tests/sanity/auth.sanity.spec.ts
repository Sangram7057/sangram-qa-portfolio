import { expect, test } from "@playwright/test";
import { users } from "../../src/data/test-users";
import { DashboardPage } from "../../src/pages/DashboardPage";
import { LoginPage } from "../../src/pages/LoginPage";

test.describe("Sanity suite", () => {
  test("login and sign-out critical path works", async ({ page }) => {
    const loginPage = new LoginPage(page);
    const dashboardPage = new DashboardPage(page);

    await loginPage.open();
    await loginPage.login(users.validUser.username, users.validUser.password);
    await dashboardPage.expectLoaded();
    await dashboardPage.signOut();

    await expect(page).toHaveURL(/login/);
    await loginPage.expectVisible();
  });
});
