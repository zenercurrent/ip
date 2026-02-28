# Zenerbot User Guide

Zenerbot is a lightweight task manager chatbot with a simple chat-style GUI. It helps you track todos, deadlines, and events using short commands.

###### by Isaac Goh 

---

## Quick Start
1. Ensure you have Java 17 or above installed.
2. Download the latest `zenerbot.jar`.
3. Copy the jar into the folder you want to use as Zenerbot’s “home folder”.
4. Open a terminal in that folder and run:

```bash
java -jar zenerbot.jar
```

A JavaFX window should appear. Type commands into the input box and press Enter (or click Send) to run them.

## Features

> ### Notes about command format
> - Commands are case-insensitive.
    e.g. todo read book and TODO read book work the same.
> - Words in UPPER_CASE are parameters you provide.
    e.g. in delete INDEX, INDEX is a number (1, 2, 3, ...)
> - Items in square brackets are optional.
> - Extra parameters for commands that do not need parameters are ignored.
> - **Date/time input format is described in the [Date & time](#date-&-time-formats) formats section.**


---

## Commands
### 1) List tasks: `list`

Shows all current tasks.

Format:
`list`

### 2) Add a todo: `todo`

Adds a _todo_ task.
A _todo_ is a task that has no fixed deadline.

Format:
`todo TASK_NAME`

Example:
`todo walk the dog`

### 3) Add a deadline: `deadline`

Adds a _deadline_ task with a due date/time.

Format:
`deadline TASK_NAME /by BY`

Examples:

`deadline submit report /by 23/2/2025`

`deadline submit report /by 1045`

`deadline submit report /by 23/2/2025 1045`

### 4) Add an event: `event`

Adds an _event_ task with a start and end date/time.

Format:
`event EVENT_NAME /from FROM /to TO`

Examples:

`event cs2103 meeting /from 23/2/2025 1400 /to 23/2/2025 1600`

`event lunch /from 1200 /to 1330`

`event conference /from 23/2/2025 /to 25/2/2025`

**Note: If the /to time is earlier than /from, Zenerbot will automatically swap them.**

### 5) Mark a task done: `mark`

Marks a task (by index) as completed.

Format:
`mark INDEX`

Example:
`mark 2`

### 6) Mark a task undone: `unmark`

Marks a task (by index) as not completed.

Format:
`unmark INDEX`

Example:
`unmark 2`

### 7) Delete a task: `delete`

Deletes a task (by index).

Format:
`delete INDEX`

Example:
`delete 3`

### 8) Find tasks by keyword: `find`

Find tasks whose names contain the given text.

Format:
`find KEYWORD [SEARCH_TERMS...]`

Examples:

`find report`

`find cs2103 meeting`

### 9) Exit: `bye`

Saves and exits the application.

Format:
`bye`
---
### Date & time formats

Zenerbot accepts flexible inputs for deadlines/events:

- **Date only format** `d/M/yyyy`
   - _Example: `23/2/2025`_
   - time defaults to **23:59** (deadlines, end of events), **00:00** (start of events)
- **Time only format** `HHmm` (24-hour)
   - _Example: `1045`_
   - date defaults to **today**
- **Combined date + time format** `d/M/yyyy HHmm`
   - Example: `23/2/2025 1045`

### Saving the data

Zenerbot automatically saves your task list to:

`./data/zener.txt` (relative to where you run the jar)

You do not need to manually save — Zenerbot saves whenever tasks change (e.g., `add`/`mark`/`unmark`).

### Editing the data file (advanced)

You can edit `./data/zener.txt` manually, but with caution.

> **Caution!**
>
> If you make the file invalid, some lines may fail to load. <br>
> It’s recommended to back up the file before editing.

---

## Command summary

| Action   | Format                               | Example                                     |
|----------|--------------------------------------|---------------------------------------------|
| List     | `list`                               | `list`                                      |
| Todo     | `todo TASK_NAME`                     | `todo read book`                            |
| Deadline | `deadline TASK_NAME /by BY`          | `deadline submit report /by 23/2/2025 1045` |
| Event    | `event EVENT_NAME /from FROM /to TO` | `event meeting /from 1400 /to 1530`         |
| Mark     | `mark INDEX`                         | `mark 1`                                    |
| Unmark   | `unmark INDEX`                       | `unmark 1`                                  |
| Delete   | `delete INDEX`                       | `delete 2`                                  |
| Find     | `find KEYWORD...`                    | `find report`                               |
| Exit     | `bye`                                | `bye`                                       |

