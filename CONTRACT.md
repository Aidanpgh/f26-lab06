# Contract Worksheet

One section per milestone. Fill each one in as you go, in order. Write each
prediction before you run anything. That is the part a TA asks about.

Keep it short and specific. Point at methods, call sites, and error text.

---

## Milestone 1: The notes overload

### Prediction (write this before you run the build, and you can deliberate with your agent)

**Will the consumer, untouched, still compile and pass?** Yes or no.

The consumer's existing call sites pass 4 arguments, so the compiler still resolves them to the unchanged 4-argument createBooking. The new overload is invisible to them, so I think that the consumer will still compile and pass. 

**Why.** What does the compiler do with the consumer's existing call sites once
the new overload exists?

As mentioned before the compiler still resolves them to the old 4-arguement version of createBooking.

### What happened

**The result.** What the build printed for each module.

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running edu.cmu.cs214.booking.InMemoryBookingServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.048 s -- in edu.cmu.cs214.booking.InMemoryBookingServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running edu.cmu.cs214.frontdesk.FrontDeskTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.059 s -- in edu.cmu.cs214.frontdesk.FrontDeskTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for lab06-booking-parent 1.0.0:
[INFO] 
[INFO] lab06-booking-parent ............................... SUCCESS [  0.003 s]
[INFO] lab06-api .......................................... SUCCESS [  0.958 s]
[INFO] lab06-consumer ..................................... SUCCESS [  0.418 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS

**If your prediction was wrong,** say what you missed.

**Is an additive change always safe in Java?** One case where adding something
to an API still breaks a caller, if you can name one.

If you were to write a new closs that implements BookingApi then add another function to BookingApi, then 
your new class would need to implement the new function, so it would break. 


---

## Milestone 2: The request object

### Prediction (write this before you run the build)

**Will the untouched consumer still compile and pass?** Yes or no, and if no,
which module goes red and whether at compile time or test time.

No, the untouched consumer will not compile. The cosumer module will go red and 
the consumers tests will not run at all.

**Where.** Name the call sites you expect to be affected, if any.

I expect the following call sites to fail:

1.  FrontDesk.java:33
2.  FrontDesk.jave:27

**What about the tests in `api/`, after you update them?** And whether their
result is evidence about the consumer.

The tests should compile and pass but this says nothing about the consumer because it is written against
the old version of the code and the tests do not say anything about if they compile

### Step 1: after the fold

**What the build printed.** Paste it for each module, including file and
line for anything that failed.

[INFO] Scanning for projects...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:
[INFO] 
[INFO] lab06-booking-parent                                               [pom]
[INFO] lab06-api                                                          [jar]
[INFO] lab06-consumer                                                     [jar]
[INFO] 
[INFO] -----------------< edu.cmu.cs214:lab06-booking-parent >-----------------
[INFO] Building lab06-booking-parent 1.0.0                                [1/3]
[INFO]   from pom.xml
[INFO] --------------------------------[ pom ]---------------------------------
[INFO] 
[INFO] ----------------------< edu.cmu.cs214:lab06-api >-----------------------
[INFO] Building lab06-api 1.0.0                                           [2/3]
[INFO]   from api/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.4.0:resources (default-resources) @ lab06-api ---
[INFO] skip non existing resourceDirectory /Users/aidanplummer/Desktop/17-214/f26-lab06/api/src/main/resources
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ lab06-api ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- resources:3.4.0:testResources (default-testResources) @ lab06-api ---
[INFO] skip non existing resourceDirectory /Users/aidanplummer/Desktop/17-214/f26-lab06/api/src/test/resources
[INFO] 
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ lab06-api ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- surefire:3.5.6:test (default-test) @ lab06-api ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running edu.cmu.cs214.booking.InMemoryBookingServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.053 s -- in edu.cmu.cs214.booking.InMemoryBookingServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --------------------< edu.cmu.cs214:lab06-consumer >--------------------
[INFO] Building lab06-consumer 1.0.0                                      [3/3]
[INFO]   from consumer/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.4.0:resources (default-resources) @ lab06-consumer ---
[INFO] skip non existing resourceDirectory /Users/aidanplummer/Desktop/17-214/f26-lab06/consumer/src/main/resources
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ lab06-consumer ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 1 source file with javac [debug deprecation release 21] to target/classes
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /Users/aidanplummer/Desktop/17-214/f26-lab06/consumer/src/main/java/edu/cmu/cs214/frontdesk/FrontDesk.java:[27,19] method createBooking in interface edu.cmu.cs214.booking.BookingApi cannot be applied to given types;
  required: edu.cmu.cs214.booking.BookingRequest
  found:    java.lang.String,long,long,<nulltype>
  reason: actual and formal argument lists differ in length
[ERROR] /Users/aidanplummer/Desktop/17-214/f26-lab06/consumer/src/main/java/edu/cmu/cs214/frontdesk/FrontDesk.java:[33,19] method createBooking in interface edu.cmu.cs214.booking.BookingApi cannot be applied to given types;
  required: edu.cmu.cs214.booking.BookingRequest
  found:    java.lang.String,long,long,java.lang.String
  reason: actual and formal argument lists differ in length
[INFO] 2 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for lab06-booking-parent 1.0.0:
[INFO] 
[INFO] lab06-booking-parent ............................... SUCCESS [  0.002 s]
[INFO] lab06-api .......................................... SUCCESS [  1.042 s]
[INFO] lab06-consumer ..................................... FAILURE [  0.367 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.494 s
[INFO] Finished at: 2026-10-01T14:55:24-04:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.13.0:compile (default-compile) on project lab06-consumer: Compilation failure: Compilation failure: 
[ERROR] /Users/aidanplummer/Desktop/17-214/f26-lab06/consumer/src/main/java/edu/cmu/cs214/frontdesk/FrontDesk.java:[27,19] method createBooking in interface edu.cmu.cs214.booking.BookingApi cannot be applied to given types;
[ERROR]   required: edu.cmu.cs214.booking.BookingRequest
[ERROR]   found:    java.lang.String,long,long,<nulltype>
[ERROR]   reason: actual and formal argument lists differ in length
[ERROR] /Users/aidanplummer/Desktop/17-214/f26-lab06/consumer/src/main/java/edu/cmu/cs214/frontdesk/FrontDesk.java:[33,19] method createBooking in interface edu.cmu.cs214.booking.BookingApi cannot be applied to given types;
[ERROR]   required: edu.cmu.cs214.booking.BookingRequest
[ERROR]   found:    java.lang.String,long,long,java.lang.String
[ERROR]   reason: actual and formal argument lists differ in length
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
[ERROR] 
[ERROR] After correcting the problems, you can resume the build with the command
[ERROR]   mvn <args> -rf :lab06-consumer

**Which module's tests ran, and which did not.** And what that tells you about
who can detect a contract break.

The tests ran for the booking service and not the consumer. This tells us tha tthe consumers 
code was the only one that could detect a break

### Step 2: the deprecation path

**What you added.** The signatures that came back, and what they delegate to.

I added back both old signatures, createBooking(String, long, long, String) and createBooking(String, long, long, String, String), as @Deprecated default methods on BookingApi. Each builds a BookingRequest from its arguments (setting the waitlist key, and notes for the 5-argument version) and calls createBooking(BookingRequest), which holds the only real implementation. The @deprecated javadoc on each names createBooking(BookingRequest) as the replacement.

**The warnings.** Paste one deprecation warning line from the build log (from
a `mvn -B clean test` run, since a rerun with nothing to compile prints none).

[WARNING] /Users/aidanplummer/Desktop/17-214/f26-lab06/consumer/src/main/java/edu/cmu/cs214/frontdesk/FrontDesk.java:[27,19] createBooking(java.lang.String,long,long,java.lang.String) in edu.cmu.cs214.booking.BookingApi has been deprecated

**What the deprecation path resolves.** Who can now build that could not build
during step 1, and who is on which schedule.

Who can now build is the consumer and the consumer team does not have to change anything today,
but the deprecation warnings tell them to migrate.

**What the warnings accomplish that a README note would not.** Be concrete
about where the warning shows up and who sees it without looking for it.

The warnings show up on a compile, so anyone who is testing the code will see them.
This means that the consumer team will see them without looking for them. Also,
the warning shows the exact locaiton, so the developer who is calling the deprecated method will see it.
Callers who use the old method will not.

---

## Milestone 3: The misuse critique

Not coded. One misuse, one redesign, one cost. Discuss it with your TA.

### The misuse

**What is easy to get wrong.** One specific thing about the API surface.

**The call site.** File and line in `consumer/`, with the call. Show the
code that a reader cannot understand without opening the javadoc, or that a
caller could get wrong with the compiler still happy.

**What goes wrong when it happens.** Silent bad behavior, wrong data, a crash
somewhere far away?

### The redesign

**The proposal.** Types, enums, factories, or whatever you are proposing. Show
the new signature and the new call site.

**Why the mistake is now hard or impossible to make.** Point at the mechanism,
such as the compiler, a validating constructor, or an exhaustive switch.

### One tradeoff

**What it costs.** Something real, such as caller ceremony, migration burden
against the deprecation path you just built, or more types for a newcomer to
learn. "No real downside" does not count.

**When the price is worth paying.** A condition under which it is.
