import { McpServer } from "@modelcontextprotocol/server";
import { StdioServerTransport } from "@modelcontextprotocol/server/stdio";
import { chromium, Page } from "playwright";
import * as z from "zod/v4";
import { runRegressionFlow, runSanityFlow } from "../src/workflows/mcpFlows";

const server = new McpServer({
  name: "playwright-qa-regression-sanity-server",
  version: "1.0.0",
});

type SuiteResult = {
  suite: string;
  checks: string[];
  finalUrl: string;
};

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
    const result = await withBrowser(runSanityFlow, "sanity", headless);
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
    const result = await withBrowser(runRegressionFlow, "regression", headless);
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
