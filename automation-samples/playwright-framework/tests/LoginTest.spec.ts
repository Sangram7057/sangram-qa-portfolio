import { expect, test } from "@playwright/test";
import { loginAndReachDashboard } from "../src/workflows/authFlows";

test.describe("Login module", () => {
  test("valid user can sign in and reach dashboard @smoke", async ({ page }) => {
    await loginAndReachDashboard(page);
    await expect(page).toHaveURL(/dashboard/);
  });
});
