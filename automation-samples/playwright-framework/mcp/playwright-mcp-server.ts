import { McpServer } from "@modelcontextprotocol/server";
import { StdioServerTransport } from "@modelcontextprotocol/server/stdio";
import { chromium, Page } from "playwright";
import * as z from "zod/v4";
import { env } from "../src/config/env";
import { capturePublicPage, runDashboardSmokeFlow } from "../src/workflows/mcpFlows";

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
    const result = await withBrowser(headless, async (page, checks) =>
      runDashboardSmokeFlow(page, checks)
    );

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
    const result = await withBrowser(headless, async (page, checks) =>
      capturePublicPage(page, checks, route, env.baseUrl)
    );

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
