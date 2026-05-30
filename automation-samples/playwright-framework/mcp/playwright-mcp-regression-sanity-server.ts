import { McpServer } from "@modelcontextprotocol/server";
import { StdioServerTransport } from "@modelcontextprotocol/server/stdio";
import { chromium, Page } from "playwright";
import * as z from "zod/v4";
import { env } from "../src/config/env";
import { users } from "../src/data/test-users";
import { AccountsPage } from "../src/pages/AccountsPage";
import { DashboardPage } from "../src/pages/DashboardPage";
import { LoginPage } from "../src/pages/LoginPage";
import { TransactionsPage } from "../src/pages/TransactionsPage";

const server = new McpServer({
  name: "playwright-qa-regression-sanity-server",
  version: "1.0.0",
});

type SuiteResult = {
  suite: string;
  checks: string[];
  finalUrl: string;
};

async function login(page: Page, checks: string[]) {
  const loginPage = new LoginPage(page);
  await loginPage.open(env.baseUrl);
  await loginPage.login(users.validUser.username, users.validUser.password);
  await page.waitForURL("**/dashboard");
  checks.push("Login flow succeeded and redirected to dashboard");
}

async function withBrowser(
  run: (page: Page, checks: string[]) => Promise<void>,
  suite: string,
  headless: boolean
): Promise<SuiteResult> {
  const browser = await chromium.launch({ headless });
  const page = await browser.newPage();
  const checks: string[] = [];

  try {
    await run(page, checks);

    return {
      suite,
      checks,
      finalUrl: page.url(),
    };
  } finally {
    await browser.close();
  }
}

async function runSanitySuite(headless: boolean) {
  return withBrowser(async (page, checks) => {
    const dashboardPage = new DashboardPage(page);

    await login(page, checks);
    await dashboardPage.expectLoaded();
    checks.push("Dashboard heading and account summary are visible");

    await dashboardPage.signOut();
    await page.waitForURL("**/login");
    checks.push("Sign out returned the user to login page");
  }, "sanity", headless);
}

async function runRegressionSuite(headless: boolean) {
  return withBrowser(async (page, checks) => {
    const dashboardPage = new DashboardPage(page);
    const accountsPage = new AccountsPage(page);
    const transactionsPage = new TransactionsPage(page);

    await login(page, checks);
    await dashboardPage.expectLoaded();
    checks.push("Dashboard summary is visible after login");

    await accountsPage.open(env.baseUrl);
    await accountsPage.search("primary");
    await accountsPage.expectResultsVisible();
    checks.push("Accounts module loaded and search interaction completed");

    await transactionsPage.open(env.baseUrl);
    await transactionsPage.filterByLast30Days();
    await transactionsPage.expectFilteredResultsVisible();
    checks.push("Transactions module loaded and filter flow completed");

    await page.goto(`${env.baseUrl}/profile`, { waitUntil: "domcontentloaded" });
    await page.getByRole("heading", { name: "Profile" }).waitFor();
    checks.push("Profile page loaded successfully");
  }, "regression", headless);
}

function formatResult(result: SuiteResult) {
  return [
    `Suite: ${result.suite}`,
    `Final URL: ${result.finalUrl}`,
    "Checks:",
    ...result.checks.map((check) => `- ${check}`),
  ].join("\n");
}

server.registerTool(
  "run_sanity_suite",
  {
    description:
      "Run a Playwright MCP sanity suite that checks login, dashboard visibility, and sign-out behavior.",
    inputSchema: z.object({
      headless: z.boolean().default(true),
    }),
  },
  async ({ headless }) => {
    const result = await runSanitySuite(headless);
    return {
      content: [{ type: "text", text: formatResult(result) }],
    };
  }
);

server.registerTool(
  "run_regression_suite",
  {
    description:
      "Run a Playwright MCP regression suite across accounts, transactions, and profile workflows.",
    inputSchema: z.object({
      headless: z.boolean().default(true),
    }),
  },
  async ({ headless }) => {
    const result = await runRegressionSuite(headless);
    return {
      content: [{ type: "text", text: formatResult(result) }],
    };
  }
);

async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
}

main().catch((error) => {
  console.error("Failed to start Playwright MCP regression/sanity server:", error);
  process.exit(1);
});
