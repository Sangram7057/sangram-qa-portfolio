import { test } from "@playwright/test";
import { loginAndReachDashboard } from "../src/workflows/authFlows";
import { reviewProfileModule } from "../src/workflows/moduleFlows";

test.describe("Profile module", () => {
  test("authenticated user can open the profile summary page @regression", async ({ page }) => {
    await loginAndReachDashboard(page);
    await reviewProfileModule(page);
  });
});
