import { test } from "@playwright/test";
import { AccountsPage } from "../../src/pages/AccountsPage";
import { DashboardPage } from "../../src/pages/DashboardPage";
import { LoginPage } from "../../src/pages/LoginPage";
import { TransactionsPage } from "../../src/pages/TransactionsPage";
import { users } from "../../src/data/test-users";

test.describe("Regression suite", () => {
  test("user can search accounts and filter transactions", async ({ page }) => {
    const loginPage = new LoginPage(page);
    const dashboardPage = new DashboardPage(page);
    const accountsPage = new AccountsPage(page);
    const transactionsPage = new TransactionsPage(page);

    await loginPage.open();
    await loginPage.login(users.validUser.username, users.validUser.password);
    await dashboardPage.expectLoaded();

    await accountsPage.open();
    await accountsPage.search("primary");
    await accountsPage.expectResultsVisible();

    await transactionsPage.open();
    await transactionsPage.filterByLast30Days();
    await transactionsPage.expectFilteredResultsVisible();
  });
});
