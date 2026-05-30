import { McpServer } from "@modelcontextprotocol/server";
import { StdioServerTransport } from "@modelcontextprotocol/server/stdio";
import { chromium } from "playwright";
import * as z from "zod/v4";

const baseUrl = process.env.BASE_URL ?? "https://example.test-app.local";
const username = process.env.TEST_USERNAME ?? "demo.user";
const password = process.env.TEST_PASSWORD ?? "replace-me";

const server = new McpServer({
  name: "playwright-qa-smoke-server",
  version: "1.0.0",
});

type SmokeResult = {
  pageTitle: string;
  currentUrl: string;
  checks: string[];
};

async function runDashboardSmoke(headless: boolean): Promise<SmokeResult> {
  const browser = await chromium.launch({ headless });
  const page = await browser.newPage();
  const checks: string[] = [];

  try {
    await page.goto(`${baseUrl}/login`, { waitUntil: "domcontentloaded" });

    await page.getByLabel("Username").fill(username);
    await page.getByLabel("Password").fill(password);
    await page.getByRole("button", { name: "Sign in" }).click();

    await page.waitForURL("**/dashboard");
    checks.push("User redirected to dashboard after login");

    await page.getByRole("heading", { name: "Dashboard" }).waitFor();
    checks.push("Dashboard heading is visible");

    await page.getByTestId("account-summary-card").waitFor();
    checks.push("Account summary card is visible");

    await page.getByTestId("notifications-panel").waitFor();
    checks.push("Notifications panel is visible");

    return {
      pageTitle: await page.title(),
      currentUrl: page.url(),
      checks,
    };
  } finally {
    await browser.close();
  }
}

async function capturePublicPage(route: string, headless: boolean): Promise<SmokeResult> {
  const browser = await chromium.launch({ headless });
  const page = await browser.newPage();
  const checks: string[] = [];

  try {
    await page.goto(`${baseUrl}${route}`, { waitUntil: "domcontentloaded" });
    checks.push(`Opened ${route} successfully`);

    const pageTitle = await page.title();
    checks.push(`Captured title: ${pageTitle}`);

    return {
      pageTitle,
      currentUrl: page.url(),
      checks,
    };
  } finally {
    await browser.close();
  }
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
