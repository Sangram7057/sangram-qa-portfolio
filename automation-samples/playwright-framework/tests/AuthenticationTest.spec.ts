import { test } from "@playwright/test";
import { loginAndReachDashboard, signOutFromActiveSession } from "../src/workflows/authFlows";

test.describe("Authentication module", () => {
  test("critical authentication path covers login, dashboard load, and sign-out @sanity", async ({
    page,
  }) => {
    await loginAndReachDashboard(page);
    await signOutFromActiveSession(page);
  });
});
