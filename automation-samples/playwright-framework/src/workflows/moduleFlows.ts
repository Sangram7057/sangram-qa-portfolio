import { Page } from "@playwright/test";
import { AccountsPage } from "../pages/AccountsPage";
import { ProfilePage } from "../pages/ProfilePage";
import { TransactionsPage } from "../pages/TransactionsPage";

export async function reviewAccountsModule(page: Page, searchTerm = "primary", baseUrl?: string) {
  const accountsPage = new AccountsPage(page);

  await accountsPage.open(baseUrl);
  await accountsPage.search(searchTerm);
  await accountsPage.expectSearchTerm(searchTerm);
  await accountsPage.expectResultsVisible();

  return accountsPage;
}

export async function reviewTransactionsModule(
  page: Page,
  dateRange = "last-30-days",
  baseUrl?: string
) {
  const transactionsPage = new TransactionsPage(page);

  await transactionsPage.open(baseUrl);
  await transactionsPage.filterByDateRange(dateRange);
  await transactionsPage.expectDateRange(dateRange);
  await transactionsPage.expectFilteredResultsVisible();

  return transactionsPage;
}

export async function reviewProfileModule(page: Page, baseUrl?: string) {
  const profilePage = new ProfilePage(page);

  await profilePage.open(baseUrl);
  await profilePage.expectLoaded();

  return profilePage;
}
