# Scala Kata Starter Code

This repository contains starter code for attempting a Scala kata exercise.

It assumes that you have Java, Scala and Scala Build Tool (SBT) installed.

If you haven't yet installed these, it's worth following the instructions on the Scala refresh exercises regarding Scala setup and installation

https://github.com/techreturners/scala_coding_exercises

## Instructions

To utilise this starter code

### 1. Create a new folder on your computer that will house the starter code.

For example if you are working on a bowling game kata, using the command line you would do:

```
mkdir bowling-game-kata
```

### 2. Change to the directory and pull this code

Next navigate to that directory and `git pull` this code

```
cd bowling-game-kata
```

Initialise git

```
git init
```

And then pull the starter code

```
git pull https://github.com/techreturners/lm-code-kata-scala-starter.git
```

Once the code has been pulled then rename the branch to **main**

```
git branch -M main
````

### 3. Open up GitHub.com and create a new repository

Go to GitHub.com and create a new repository.

Give the repository a name - suggest naming the repository the same name as your folder

Make sure it is **Private**

Then leave everything else as blank. So do NOT create a README, GitIgnore or Licence.

Click **Create repository**

### 4. Copy URL of new repository

You should then see a screen telling you how to push to the repository.

Copy the URL of the repository. For example if a user called **pluto** had created a repository called **bowling-game-kata** then the URL would be:

https://github.com/pluto/bowling-game-kata.git

### 5. Push starter code back to repository

Then back on your computer whilst within your newly created directory. 

Configure your GitHub origin server (for where you will be pushing code back to)

```
git remote add origin URL_YOU_COPIED
```

Replacing the **URL_YOU_COPIED** with the correct URL. For example:

```
git remote add origin https://github.com/pluto/bowling-game-kata.git
```

Now you can push the code to your repository

```
git push -u origin main
```

### 6. Make sure you can run the tests

You should now be able to run the Scala tests either from the command line or your editor (such as IntelliJ)

```
sbt test
```

Should produce output similar to the following:

```
[info] welcome to sbt 1.6.2 (Azul Systems, Inc. Java 11.0.14)
[info] loading global plugins from /Users/someuser/.sbt/1.0/plugins
[info] loading project definition from /Users/someuser/Developer/techreturners/yrtt/lm-code-kata-scala-starter/project
[info] loading settings for project root from build.sbt ...
[info] set current project to lm-code-kata-scala-starter (in build file:/Users/someuser/Developer/techreturners/yrtt/lm-code-kata-scala-starter/)
[info] compiling 1 Scala source to /Users/someuser/Developer/techreturners/yrtt/lm-code-kata-scala-starter/target/scala-2.13/classes ...
[info] compiling 1 Scala source to /Users/someuser/Developer/techreturners/yrtt/lm-code-kata-scala-starter/target/scala-2.13/test-classes ...
[info] AppTest:
[info] A string from the app
[info] - should be Hi from Tech Returners
[info] Run completed in 193 milliseconds.
[info] Total number of tests run: 1
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 3 s, completed 30 Apr 2022, 22:05:47
```

If you do see it run the tests then you're all ready to go 🙌

### 7. Utilise repository as normal

Now you can continue to utilise the repository as normal, committing and pushing as normal.


