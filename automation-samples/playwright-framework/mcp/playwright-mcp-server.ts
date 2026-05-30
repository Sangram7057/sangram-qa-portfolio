import { McpServer } from "@modelcontextprotocol/server";
import { StdioServerTransport } from "@modelcontextprotocol/server/stdio";
import { chromium, Page } from "playwright";
import * as z from "zod/v4";
import { env } from "../src/config/env";
import { users } from "../src/data/test-users";
import { DashboardPage } from "../src/pages/DashboardPage";
import { LoginPage } from "../src/pages/LoginPage";

const server = new McpServer({
  name: "playwright-qa-smoke-server",
  version: "1.0.0",
});

type SmokeResult = {
  pageTitle: string;
  currentUrl: string;
  checks: string[];
};

async function withBrowser(
  headless: boolean,
  run: (page: Page, checks: string[]) => Promise<SmokeResult>
): Promise<SmokeResult> {
  const browser = await chromium.launch({ headless });
  const page = await browser.newPage();

  try {
    return await run(page, []);
  } finally {
    await browser.close();
  }
}

async function runDashboardSmoke(headless: boolean): Promise<SmokeResult> {
  return withBrowser(headless, async (page, checks) => {
    const loginPage = new LoginPage(page);
    const dashboardPage = new DashboardPage(page);

    await loginPage.open(env.baseUrl);
    checks.push("Login route opened successfully");

    await loginPage.login(users.validUser.username, users.validUser.password);
    await page.waitForURL("**/dashboard");
    checks.push("User redirected to dashboard after login");

    await dashboardPage.expectLoaded();
    checks.push("Dashboard heading and account summary are visible");

    await page.getByTestId("notifications-panel").waitFor();
    checks.push("Notifications panel is visible");

    return {
      pageTitle: await page.title(),
      currentUrl: page.url(),
      checks,
    };
  });
}

async function capturePublicPage(route: string, headless: boolean): Promise<SmokeResult> {
  return withBrowser(headless, async (page, checks) => {
    await page.goto(`${env.baseUrl}${route}`, { waitUntil: "domcontentloaded" });
    checks.push(`Opened ${route} successfully`);

    const pageTitle = await page.title();
    checks.push(`Captured title: ${pageTitle}`);

    return {
      pageTitle,
      currentUrl: page.url(),
      checks,
    };
  });
}

server.registerTool(
  "run_dashboard_smoke",
  {
    description:
      "Run a Playwright smoke login flow and verify key dashboard widgets for a QA regression check.",
    inputSchema: z.object({
      headless: z.boolean().default(true),
    }),
  },
  async ({ headless }) => {
    const result = await runDashboardSmoke(headless);

    return {
      content: [
        {
          type: "text",
          text: [
            `Smoke run completed`,
            `Title: ${result.pageTitle}`,
            `URL: ${result.currentUrl}`,
            `Checks:`,
            ...result.checks.map((check) => `- ${check}`),
          ].join("\n"),
        },
      ],
    };
  }
);

server.registerTool(
  "capture_public_page",
  {
    description:
      "Open a public route in Playwright and return the resolved page title and URL for a lightweight MCP-driven check.",
    inputSchema: z.object({
      route: z.string().default("/"),
      headless: z.boolean().default(true),
    }),
  },
  async ({ route, headless }) => {
    const result = await capturePublicPage(route, headless);

    return {
      content: [
        {
          type: "text",
          text: [
            `Page capture completed`,
            `Title: ${result.pageTitle}`,
            `URL: ${result.currentUrl}`,
            `Checks:`,
            ...result.checks.map((check) => `- ${check}`),
          ].join("\n"),
        },
      ],
    };
  }
);

async function main() {
  const transport = new StdioServerTransport();
  await server.connect(transport);
}

main().catch((error) => {
  console.error("Failed to start Playwright MCP server:", error);
  process.exit(1);
});
