# scala-homework

The goal of the assignment was to deliver simple console application,
allowing to search all text files placed in given directory for specific
words.

## Building and running the code

Development of the code was done with usage of [nix-shell](https://nixos.org/nix/).
Nix is an incredible package manager (and much more), which deliver hermetic,
reproducible, deterministic build environments.

To start working in the very same shell the application was developed and tested, 
one should install nix and then simply type the following command in project root:

```
$ nix-shell --pure

```
Above command should drop into the shell, with appropriate Java, Scala and Sbt
being ready for usage (additionally, code should pre-compile).

Both application execution and testing were done with sbt - try the following
commands to compile, run and test the app:

```
$ nix-shell --pure
$ sbt compile
$ sbt test
$ sbt run <path_to_directory> <path_to_other_directory>
```

### Notice

As the point of excercise was not focused around project 'infrastructure', I was not focusing much on the 
building process. However, given enough time, I would much rather prefer having a proper [Bazel](https://bazel.build/)
project setup, which has a great Java dependecy management (and more neat features.), in contrast
to somewhat flakey sbt.

## Assumptions made

Due to time constraints, the following assumptions / decision were made:

* If user provides a path to directory, its sub-directories are not processed
* Words may be composed of various unicode charaters
* Words are separated by spaces
* Words may not contain punctuaction (which made parsing French harder, afaik)
* Words are subject to tokenization process and are considered equal 
when their token counterparts are equal (simple string comparision)

## Note on memory representation

As a result of directory scanning, the in-memory representation of file contents is created.
It is as simple as dictionary, where keys are tokens and values are sets of files that contained 
given token. This allows for O(1) search for any token, therefore allowing to lookup arbitrary 
quantity of words at a constant time (balanced by memory footprint).

### Tokenization

Each file is read line-by-line, assuming each word is separated by spaces.
Then the tokenization process ensues, which removes any punctuaction signs,
performs [NFKC normalization](https://en.wikipedia.org/wiki/Unicode_equivalence#Normal_forms)
and finally lowercases the words.

### Ranking formula

File rank is calculated as a simple ratio between number of distinct tokens which were found in 
given file and a total number of distinct tokens which are searched for.

## Future improvements

1. Expand unit tests
2. Improve tokenization process (turn numbers into words, expand abbreviations, remove stop words etc.)
3. Improve ranking formula (seems that a total number of occurences of given word could be used as a weight)
4. Include logging capabilities
5. Make the application more verbose in terms of what went wrong (do not silently swallow errors)
6. Include ```typelevel/simulacrum``` and ```cats``` libraries
7. Parallelize the work being done by the application
