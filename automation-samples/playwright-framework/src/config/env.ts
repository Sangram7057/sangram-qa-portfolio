import dotenv from "dotenv";

dotenv.config();

export const env = {
  baseUrl: process.env.BASE_URL ?? "https://example.test-app.local",
  testUsername: process.env.TEST_USERNAME ?? "demo.user",
  testPassword: process.env.TEST_PASSWORD ?? "replace-me",
};
