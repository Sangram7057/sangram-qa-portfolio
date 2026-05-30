import { env } from "../config/env";

export const users = {
  validUser: {
    username: env.testUsername,
    password: env.testPassword,
  },
};
