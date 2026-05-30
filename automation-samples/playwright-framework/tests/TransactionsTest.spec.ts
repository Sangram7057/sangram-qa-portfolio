import { test } from "@playwright/test";
import { loginAndReachDashboard } from "../src/workflows/authFlows";
import { reviewTransactionsModule } from "../src/workflows/moduleFlows";

test.describe("Transactions module", () => {
  test("authenticated user can apply the last 30 days filter @sanity", async ({ page }) => {
    await loginAndReachDashboard(page);
    await reviewTransactionsModule(page, "last-30-days");
  });

  test("transactions filters can be reused across repeated module visits @regression", async ({
    page,
  }) => {
    await loginAndReachDashboard(page);
    await reviewTransactionsModule(page, "last-30-days");
    await reviewTransactionsModule(page, "last-7-days");
  });
});
