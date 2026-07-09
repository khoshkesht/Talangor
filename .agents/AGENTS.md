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

Maintain a concise project log in `progress.md`.

After every completed prompt:

- Append a new entry to `progress.md`. Never modify or delete previous entries.
- Include the original user prompt exactly as received.
- Write a brief summary (1–3 sentences) of the completed work.
- List only the key changes or completed tasks.
- If the task is incomplete, add a single line describing what remains.

Keep the documentation minimal and focused. Record only information that is important for understanding the project's current state. Avoid implementation details, explanations, reasoning, assumptions, or repetitive information unless they are critical.