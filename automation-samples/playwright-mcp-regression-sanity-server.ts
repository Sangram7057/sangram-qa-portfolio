import { McpServer } from "@modelcontextprotocol/server";
import { StdioServerTransport } from "@modelcontextprotocol/server/stdio";
import { chromium, Page } from "playwright";
import * as z from "zod/v4";

const baseUrl = process.env.BASE_URL ?? "https://example.test-app.local";
const username = process.env.TEST_USERNAME ?? "demo.user";
const password = process.env.TEST_PASSWORD ?? "replace-me";

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
  await page.goto(`${baseUrl}/login`, { waitUntil: "domcontentloaded" });
  await page.getByLabel("Username").fill(username);
  await page.getByLabel("Password").fill(password);
  await page.getByRole("button", { name: "Sign in" }).click();
  await page.waitForURL("**/dashboard");
  checks.push("Login flow succeeded and redirected to dashboard");
}

async function withBrowser(run: (page: Page, checks: string[]) => Promise<void>, suite: string): Promise<SuiteResult> {
  const browser = await chromium.launch({ headless: true });
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

async function runSanitySuite() {
  return withBrowser(async (page, checks) => {
    await login(page, checks);

    await page.getByRole("heading", { name: "Dashboard" }).waitFor();
    checks.push("Dashboard heading is visible");

    await page.getByTestId("account-summary-card").waitFor();
    checks.push("Account summary card is visible");

    await page.getByRole("button", { name: "Open profile menu" }).click();
    await page.getByRole("menuitem", { name: "Sign out" }).click();
    await page.waitForURL("**/login");
    checks.push("Sign out returned the user to login page");
  }, "sanity");
}

async function runRegressionSuite() {
  return withBrowser(async (page, checks) => {
    await login(page, checks);

    await page.goto(`${baseUrl}/accounts`, { waitUntil: "domcontentloaded" });
    await page.getByRole("heading", { name: "Accounts" }).waitFor();
    checks.push("Accounts module loaded successfully");

    await page.getByLabel("Search accounts").fill("primary");
    await page.getByTestId("account-grid").waitFor();
    checks.push("Account search interaction completed");

    await page.goto(`${baseUrl}/transactions`, { waitUntil: "domcontentloaded" });
    await page.getByRole("heading", { name: "Transactions" }).waitFor();
    checks.push("Transactions module loaded successfully");

    await page.getByLabel("Date range").selectOption("last-30-days");
    await page.getByRole("button", { name: "Apply filters" }).click();
    await page.getByTestId("transactions-table").waitFor();
    checks.push("Transaction filter flow completed");

    await page.goto(`${baseUrl}/profile`, { waitUntil: "domcontentloaded" });
    await page.getByRole("heading", { name: "Profile" }).waitFor();
    checks.push("Profile page loaded successfully");
  }, "regression");
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
    inputSchema: z.object({}),
  },
  async () => {
    const result = await runSanitySuite();
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
    inputSchema: z.object({}),
  },
  async () => {
    const result = await runRegressionSuite();
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
