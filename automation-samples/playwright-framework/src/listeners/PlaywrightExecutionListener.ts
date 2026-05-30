import path from "node:path";
import type {
  FullConfig,
  FullResult,
  Reporter,
  Suite,
  TestCase,
  TestResult,
} from "@playwright/test/reporter";
import { collectTags } from "../utils/tagUtils";
import {
  buildRunTimestamp,
  ensureDirectory,
  formatDuration,
  resolveFrameworkPath,
  toErrorMessage,
  writeJsonFile,
  writeTextFile,
} from "../utils/reportUtils";

type ExecutionRecord = {
  title: string;
  file: string;
  status: TestResult["status"];
  outcome: ReturnType<TestCase["outcome"]>;
  duration: string;
  retry: number;
  tags: string[];
  error?: string;
};

class PlaywrightExecutionListener implements Reporter {
  private readonly executedTests: ExecutionRecord[] = [];
  private runStartedAt = new Date();
  private runDirectory = "";

  onBegin(config: FullConfig, suite: Suite) {
    this.runStartedAt = new Date();
    const reportRoot = ensureDirectory(resolveFrameworkPath("test-results", "listener-artifacts"));
    this.runDirectory = ensureDirectory(path.join(reportRoot, buildRunTimestamp(this.runStartedAt)));

    writeTextFile(
      path.join(this.runDirectory, "run-info.txt"),
      [
        "Framework: Playwright",
        `Started: ${this.runStartedAt.toISOString()}`,
        `Workers: ${config.workers}`,
        `Projects: ${config.projects.map((project) => project.name).join(", ") || "default"}`,
        `Total tests discovered: ${suite.allTests().length}`,
      ].join("\n")
    );

    console.log(`[listener] Starting Playwright execution with ${suite.allTests().length} tests.`);
  }

  onTestBegin(test: TestCase) {
    const tags = collectTags(test.tags, test.title);
    const tagLabel = tags.length > 0 ? ` ${tags.join(" ")}` : "";
    console.log(`[listener] Running ${test.title}${tagLabel}`);
  }

  onTestEnd(test: TestCase, result: TestResult) {
    const tags = collectTags(test.tags, test.title);

    this.executedTests.push({
      title: test.title,
      file: test.location.file,
      status: result.status,
      outcome: test.outcome(),
      duration: formatDuration(result.duration),
      retry: result.retry,
      tags,
      error: result.error ? toErrorMessage(result.error) : undefined,
    });

    console.log(`[listener] Completed ${test.title}: ${result.status}`);
  }

  onEnd(result: FullResult) {
    const finishedAt = new Date();
    const summary = this.executedTests.reduce(
      (counts, testRecord) => {
        counts[testRecord.status] = (counts[testRecord.status] ?? 0) + 1;
        return counts;
      },
      {} as Record<string, number>
    );
    const outcomes = this.executedTests.reduce(
      (counts, testRecord) => {
        counts[testRecord.outcome] = (counts[testRecord.outcome] ?? 0) + 1;
        return counts;
      },
      {} as Record<string, number>
    );

    const payload = {
      status: result.status,
      startedAt: this.runStartedAt.toISOString(),
      finishedAt: finishedAt.toISOString(),
      duration: formatDuration(finishedAt.getTime() - this.runStartedAt.getTime()),
      summary,
      outcomes,
      tests: this.executedTests,
    };

    writeJsonFile(path.join(this.runDirectory, "execution-summary.json"), payload);
    writeTextFile(
      path.join(this.runDirectory, "execution-summary.txt"),
      [
        `Overall status: ${result.status}`,
        `Started: ${this.runStartedAt.toISOString()}`,
        `Finished: ${finishedAt.toISOString()}`,
        `Duration: ${payload.duration}`,
        `Passed: ${summary.passed ?? 0}`,
        `Failed: ${summary.failed ?? 0}`,
        `Skipped: ${summary.skipped ?? 0}`,
        `Timed out: ${summary.timedOut ?? 0}`,
        `Interrupted: ${summary.interrupted ?? 0}`,
        `Outcome expected: ${outcomes.expected ?? 0}`,
        `Outcome flaky: ${outcomes.flaky ?? 0}`,
        `Outcome skipped: ${outcomes.skipped ?? 0}`,
        `Outcome unexpected: ${outcomes.unexpected ?? 0}`,
      ].join("\n")
    );

    console.log(
      `[listener] Playwright execution finished: ${result.status}. Summary saved to ${this.runDirectory}`
    );
  }
}

export default PlaywrightExecutionListener;
