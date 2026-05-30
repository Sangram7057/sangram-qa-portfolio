export function extractTagsFromTitle(title: string) {
  return title.match(/@\w[\w-]*/g) ?? [];
}

export function collectTags(explicitTags: string[] | undefined, title: string) {
  return Array.from(new Set([...(explicitTags ?? []), ...extractTagsFromTitle(title)])).sort();
}
