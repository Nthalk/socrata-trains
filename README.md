Trains
================================================================================

This codebase is a solution to `PROBLEM ONE: TRAINS` as described by PROBLEM.txt

Strategy
---------

I first created a generic `Graph`, a `TrainStation` adapter for the `Graph`, and
a `LineParser` for the `TrainStation` adapter.

The `BreadthPather` is currently the only pather (see TODO), and it is a 
functional iterator that extends it's last given path set, and then extends it 
again after the first extension set is consumed.

The `PathMatcher` uses `Graph`'s `getE` and `getV` to determine validity of
paths without traversal.

The `TripMatcher` simply checks the `Path`'s first and last `Edge` to determine
whether the trip started and stopped at the right place.

Scripts
---------

Packaged are a couple of scripts to interact with the project. 

  1. `scripts/test.sh` runs the `sbt test` which triggers the ScalaTest suite.
  2. `scripts/run.sh` compiles and runs the main program with the `input.txt` 
    piped in.
  3. `scripts/verify.sh` runs the run script and diffs the output with the 
    `output.txt` file.
    
TODO
---------

  1. Implement `PathCostStore` and a 
     `CheapestBreadthPather(PathCostStore, costFn)` The shortest path algorithms 
     are terrible, and this store and pather would go a long way to pruning 
     inefficient paths from the breadth iterator.
     
  2. Implement a `VisitMatcher` that determines if a `Path` has visited 
     `Verticies` in a specific order, ignoring non-specified visitors.
  
Developing
---------

Make sure to run `sbt test` and `scalastyle` before committing.
