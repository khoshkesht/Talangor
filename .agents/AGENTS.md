Project rules:
- Use Kotlin only.
- Use Jetpack Compose, not XML layouts.
- Use Material 3.
- Use MVVM.
- Keep business logic outside Composables.
- Do not add backend or network calls.
- Do not add AI APIs in MVP.
- All UI must be in Persian
- Persian UI should be RTL-friendly.
- After changes, run build or explain why it cannot be run.

Agent:
- Always respond in Persian, even if my prompts are written in English.
- After every set of changes, suggest a clear commit title and ask for approval before pushing to the repository.


## Documentation Rules

Maintain a project log in `progress.md`.

After every prompt you process:

* Append a new entry to `progress.md`. Never overwrite or remove previous entries.
* Include the **original user prompt exactly as received**, without any modifications, edits, or formatting changes.
* Write a concise, plain-English summary of what you implemented or changed.
* List the completed actions in chronological order.
* Record important decisions, assumptions, and reasons for them.
* If a task is only partially completed, clearly state what remains to be done.
* Use Markdown headings, bullet lists, and code blocks where appropriate to keep the document readable.

The documentation must always stay synchronized with the current state of the project and be updated immediately after each completed prompt.
