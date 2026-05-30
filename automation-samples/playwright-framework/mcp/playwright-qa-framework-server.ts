import { McpServer } from "@modelcontextprotocol/server";
import { StdioServerTransport } from "@modelcontextprotocol/server/stdio";
import { chromium, Page } from "playwright";
import * as z from "zod/v4";
import { runAccountsTransactionsFlow, runAuthenticationFlow, runDashboardSmokeFlow } from "../src/workflows/mcpFlows";

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
      const result = await runDashboardSmokeFlow(page, []);

      return `Smoke suite completed: ${result.checks.join("; ")}`;
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
      const checks: string[] = [];
      await runAuthenticationFlow(page, checks);

      return `Sanity suite completed: ${checks.join("; ")}`;
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
      const checks: string[] = [];
      await runAccountsTransactionsFlow(page, checks);

      return `Regression suite completed: ${checks.join("; ")}`;
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
