import { expect, Page } from "@playwright/test";
import { users } from "../data/test-users";
import { DashboardPage } from "../pages/DashboardPage";
import { LoginPage } from "../pages/LoginPage";

export async function loginAndReachDashboard(page: Page, baseUrl?: string) {
  const loginPage = new LoginPage(page);
  const dashboardPage = new DashboardPage(page);

  await loginPage.open(baseUrl);
  await loginPage.expectVisible();
  await loginPage.login(users.validUser.username, users.validUser.password);
  await expect(page).toHaveURL(/dashboard/);
  await dashboardPage.expectLoaded();

  return { loginPage, dashboardPage };
}

export async function signOutFromActiveSession(page: Page) {
  const dashboardPage = new DashboardPage(page);
  const loginPage = new LoginPage(page);

  await dashboardPage.signOut();
  await expect(page).toHaveURL(/login/);
  await loginPage.expectVisible();

  return { dashboardPage, loginPage };
}
