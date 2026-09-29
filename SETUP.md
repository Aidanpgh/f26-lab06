# Setup

You need a working Java toolchain and the course agent tool.

## 1. Java and Maven

- Install a JDK, version 21 or newer. Check with `java --version`.
- Install Maven 3.8 or newer. Check with `mvn --version`.

## 2. Get the repo

Fork the starter, clone your fork, and work inside it. Commit as you go.
Milestone 1 asks you to write a prediction before you run anything, and a
commit is the cheapest way to show a TA that you wrote it first.

## 3. Build and run the tests

From this directory:

```
mvn -B test
```

## 4. What green looks like

Maven runs the two modules in order. Look for `Tests run: 5, Failures: 0`
(the api module) and then `Tests run: 7, Failures: 0` (the consumer), then a
reactor summary with three SUCCESS rows (the parent, `lab06-api`,
`lab06-consumer`) ending in `BUILD SUCCESS`.
That is the starting state. If it is red before you have changed anything, that
is an environment problem. Sort it out first.

If a build goes red later, read which module failed and at which stage.

## 5. Editor

Use any editor or IDE you like. VS Code, IntelliJ IDEA, and Eclipse all import
a Maven project directly. Open this folder (the one with the parent `pom.xml`)
rather than a module folder, so the editor sees both modules and the
dependency between them.

## 6. Course agent tool

Point your agent at this folder.
It can make the code changes in milestones 1 and 2. The
writing in `CONTRACT.md` is your job.

## 7. Continuous integration

CI is configured in `.github/workflows/ci.yml`. It runs `mvn -B test` on every
push. GitHub disables workflows on a fresh fork, so enable them from the
Actions tab if you want it running.
