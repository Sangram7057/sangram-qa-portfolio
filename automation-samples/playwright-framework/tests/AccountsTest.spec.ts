import { test } from "@playwright/test";
import { loginAndReachDashboard } from "../src/workflows/authFlows";
import { reviewAccountsModule } from "../src/workflows/moduleFlows";

test.describe("Accounts module", () => {
  test("authenticated user can search primary accounts @sanity", async ({ page }) => {
    await loginAndReachDashboard(page);
    await reviewAccountsModule(page, "primary");
  });

  test("accounts grid remains stable across common search refinements @regression", async ({
    page,
  }) => {
    await loginAndReachDashboard(page);
    await reviewAccountsModule(page, "primary");
    await reviewAccountsModule(page, "savings");
  });
});
