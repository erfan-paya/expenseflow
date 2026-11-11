# ExpenseFlow

A console-based personal expense tracker built with Java 21. Add expenses, search and filter them, and view summary
reports — all from the terminal.

This is a learning project. The codebase is being actively refactored; see the Architecture section for current state
and pending work.

## Features

- Add an expense (description, category, amount)
- View all expenses
- View summary (total, average, most / cheapest)
- Search expenses by description
- Filter expenses by category
- View spending grouped by category
- Filter expenses by amount range
- Sort expenses
    - By description
    - By amount ascending
    - By amount descending
- Update an expense
- Delete an expense

## Requirements

- Java 21 (JDK)
- Maven 3.6+

No runtime dependencies. Test scope uses JUnit Jupiter 5.11.

## Run

```
mvn compile exec:java -Dexec.mainClass=io.github.erfanpaya.Main
```

The `-Dexec.mainClass` flag is redundant (the main class is also configured in `pom.xml`), but kept here so the command
is self-documenting without inspecting the pom.

## Test

```
mvn test
```

Currently 0 tests — TDD baseline. Tests are introduced in subsequent refactor phases (Category, Expense, ExpenseService,
and end-to-end CLI coverage).

## Project structure

```
expenseflow/
├── pom.xml
├── README.md
└── src/main/java/io/github/erfanpaya/
    ├── Main.java                       # entry point, owns Scanner lifecycle
    ├── cli/ExpenseFlowCli.java         # menu, input reading, output, dispatch
    ├── model/Expense.java              # expense entity
    └── service/ExpenseManager.java     # business logic + in-memory storage
```

## Architecture

Current layering is flat:

```
Main → ExpenseFlowCli → ExpenseManager → Expense
```

`ExpenseFlowCli` is currently a single class handling menus, input parsing, command dispatch, and report rendering.
`ExpenseManager` combines business logic with in-memory storage (no repository abstraction yet).

Refactor in progress. Target layering and one-way dependency rule (`cli → service → repository → model`) are pending the
repository abstraction work and will be documented here once enforced.