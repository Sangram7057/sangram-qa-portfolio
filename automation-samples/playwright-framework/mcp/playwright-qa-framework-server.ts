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
  name: "playwright-qa-framework-server",
  version: "1.0.0",
});

async function withPage(run: (page: Page) => Promise<string>) {
  const browser = await chromium.launch({ headless: true });
  const page = await browser.newPage();

  try {
    return await run(page);
  } finally {
    await browser.close();
  }
}

server.registerTool(
  "run_smoke_dashboard_check",
  {
    description: "Run the framework smoke flow that validates login and dashboard summary visibility.",
    inputSchema: z.object({}),
  },
  async () => {
    const text = await withPage(async (page) => {
      const loginPage = new LoginPage(page);
      const dashboardPage = new DashboardPage(page);

      await loginPage.open(env.baseUrl);
      await loginPage.login(users.validUser.username, users.validUser.password);
      await dashboardPage.expectLoaded();

      return "Smoke suite completed: login and dashboard summary validated.";
    });

    return { content: [{ type: "text", text }] };
  }
);

server.registerTool(
  "run_sanity_auth_check",
  {
    description: "Run the framework sanity flow that validates login and sign-out behavior.",
    inputSchema: z.object({}),
  },
  async () => {
    const text = await withPage(async (page) => {
      const loginPage = new LoginPage(page);
      const dashboardPage = new DashboardPage(page);

      await loginPage.open(env.baseUrl);
      await loginPage.login(users.validUser.username, users.validUser.password);
      await dashboardPage.expectLoaded();
      await dashboardPage.signOut();
      await loginPage.expectVisible();

      return "Sanity suite completed: authentication critical path validated.";
    });

    return { content: [{ type: "text", text }] };
  }
);

server.registerTool(
  "run_regression_accounts_transactions",
  {
    description: "Run the framework regression flow across accounts search and transaction filters.",
    inputSchema: z.object({}),
  },
  async () => {
    const text = await withPage(async (page) => {
      const loginPage = new LoginPage(page);
      const dashboardPage = new DashboardPage(page);
      const accountsPage = new AccountsPage(page);
      const transactionsPage = new TransactionsPage(page);

      await loginPage.open(env.baseUrl);
      await loginPage.login(users.validUser.username, users.validUser.password);
      await dashboardPage.expectLoaded();
      await accountsPage.open(env.baseUrl);
      await accountsPage.search("primary");
      await accountsPage.expectResultsVisible();
      await transactionsPage.open(env.baseUrl);
      await transactionsPage.filterByLast30Days();
      await transactionsPage.expectFilteredResultsVisible();

      return "Regression suite completed: accounts and transactions flows validated.";
    });

    return { content: [{ type: "text", text }] };
  }
);

async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
}

main().catch((error) => {
  console.error("Failed to start Playwright QA framework MCP server:", error);
  process.exit(1);
});
