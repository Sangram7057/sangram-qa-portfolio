import type { Page } from "@playwright/test";
import { env } from "../config/env";
import { loginAndReachDashboard, signOutFromActiveSession } from "./authFlows";
import { reviewAccountsModule, reviewProfileModule, reviewTransactionsModule } from "./moduleFlows";

export type SmokeFlowResult = {
  pageTitle: string;
  currentUrl: string;
  checks: string[];
};

export async function runDashboardSmokeFlow(
  page: Page,
  checks: string[]
): Promise<SmokeFlowResult> {
  const { dashboardPage } = await loginAndReachDashboard(page, env.baseUrl);
  checks.push("Login route opened and authentication completed");
  checks.push("User landed on dashboard successfully");

  await dashboardPage.expectNotificationsPanelVisible();
  checks.push("Notifications panel is visible");

  return {
    pageTitle: await page.title(),
    currentUrl: page.url(),
    checks,
  };
}

export async function capturePublicPage(
  page: Page,
  checks: string[],
  route: string,
  baseUrl: string
): Promise<SmokeFlowResult> {
  await page.goto(`${baseUrl}${route}`, { waitUntil: "domcontentloaded" });
  checks.push(`Opened ${route} successfully`);

  const pageTitle = await page.title();
  checks.push(`Captured title: ${pageTitle}`);

  return {
    pageTitle,
    currentUrl: page.url(),
    checks,
  };
}

export async function runAuthenticationFlow(page: Page, checks: string[]) {
  await loginAndReachDashboard(page, env.baseUrl);
  checks.push("Login flow reached the dashboard successfully");

  await signOutFromActiveSession(page);
  checks.push("Sign out returned the user to the login page");
}

export async function runAccountsTransactionsFlow(page: Page, checks: string[]) {
  await loginAndReachDashboard(page, env.baseUrl);
  checks.push("Dashboard summary is visible after login");

  await reviewAccountsModule(page, "primary", env.baseUrl);
  checks.push("Accounts module loaded and search interaction completed");

  await reviewTransactionsModule(page, "last-30-days", env.baseUrl);
  checks.push("Transactions module loaded and filter flow completed");
}

export async function runSanityFlow(page: Page, checks: string[]) {
  await runAuthenticationFlow(page, checks);
}

export async function runRegressionFlow(page: Page, checks: string[]) {
  await runAccountsTransactionsFlow(page, checks);
  await reviewProfileModule(page, env.baseUrl);
  checks.push("Profile page loaded successfully");
}
