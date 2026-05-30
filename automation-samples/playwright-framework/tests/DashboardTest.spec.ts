import { test } from "@playwright/test";
import { loginAndReachDashboard } from "../src/workflows/authFlows";

test.describe("Dashboard module", () => {
  test("dashboard widgets are visible after login @smoke", async ({ page }) => {
    const { dashboardPage } = await loginAndReachDashboard(page);
    await dashboardPage.expectNotificationsPanelVisible();
  });
});
