project folder:
guptaNswati-prject05

Brief description of submitted files:

lazyTrees/LazySearchTree.java
    - BST with lazy deletion to keep track of deleted as well as non deleted nodes.
    
lazyTrees/Item.java  
    - An object of Item class represents item's name and count in the inventory
    
lazyTrees/PrintObject.java
    - a functor class that traverses and print out data from LazySearchTree

lazyTrees/Traverser.java
    - an interface to traverse nodes of LazySearchTree
    
lazyTrees/SuperMarket.java
    - Simulates the market scenario of buying and adding items to a store's inventory.
    - Implements BST with lazy deletion to keep track of total inventory (deleted and non deleted) and current inventory (non deleted only).    
    - Tests implementation of Item.java and LazySearchTree.java
    
lazyTrees/MillionSongDataSubset.java
    - One object of class MillionSongDataSubset parses a JSON data set and stores each entry in an array.

lazyTrees/SongEntry.java
    - One object of class SongEntry stores a simplified version of the genre data set from the Million Song Dataset.

lazyTrees/TimeConverter.java
    - Converts duration into a string representation.

lazyTrees/FoothillTunesStore.java
   - An object of type FoothillTunesStore creates an object of type MillionSongDataSubset, which in turn parses a JSON data set. 
   - The parsed data set is stored in an array of SongEntry objects.  
   - Creates library of user requested songs.
   - Implements BST with lazy deletion to keep track of total tunes ("deleted" + non deleted) and current tunes (non deleted only).  

lib/json-simple-1.1.1.jar
    - Java toolkit for JSON (JavaScript Object Notation) used to encode or decode JSON text.
    - https://code.google.com/p/json-simple/downloads/list
    
    
    
resources/inventory_log.txt
   - contains list of items

resources/inventory_short.txt
   - contains list of items for testing boundary cases

resources/inventory_invalid_removal.txt   
   - contains list of items for testing boundary cases
   
resources/inventory_findMax()_testCase.txt 
 - contains list of items for testing implementation of findMax() when maximum node is removed 

resources/tunes.txt
   - contains list of links

resources/tunes_empty.txt
   - contains empty playlist for testing boundary cases

resources/tunes_truncated.txt   
   - contains a small playlist for testing edge cases 
 
resources/music_genre_subset.json
   - contains songs in json format  

resources/RUN.txt
   - console output of SuperMarket.java
   
resources/RUN_Extra_Credit.txt
   - console output of FoothillTunesStore.java

README.txt
   - description of submitted files
    