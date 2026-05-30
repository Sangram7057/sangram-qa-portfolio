import { test } from "@playwright/test";
import { loginAndReachDashboard, signOutFromActiveSession } from "../src/workflows/authFlows";

test.describe("Logout module", () => {
  test("signed-in user can sign out from the profile menu @smoke", async ({ page }) => {
    await loginAndReachDashboard(page);
    await signOutFromActiveSession(page);
  });
});
