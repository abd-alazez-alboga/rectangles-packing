# Rectangles Packing

Rectangles Packing is a Java application that solves a neat puzzle: given a set of rectangles, the program finds all the possible ways to combine them into one larger, complete rectangle without gaps or overlaps. It’s like figuring out the perfect puzzle where every piece must fit exactly right!

## What the Project Does

- Generates All Possibilities:  
  The application first generates every possible subset of the entered rectangles. Then, for each subset, it tests all the different orders (permutations) in which the rectangles could be arranged.

- Checks for Valid Configurations:  
  For each ordering, the program tries to place the rectangles onto a grid to see if they form a neat, continuous larger rectangle with no missing spaces and no overlaps.

- Eliminates Duplicate Solutions:  
  Since the order of rectangles doesn’t matter once a valid configuration is found (e.g., A | B is the same as B | A), the project uses a canonicalization process to record each unique valid set only once.

- Interactive GUI:  
  The project comes with a user-friendly interface built using Java Swing. The Home window shows all the valid rectangle sets, and there’s also a New Set Form where you can input your own rectangle dimensions and see the updated results.

## How It Works

- Manager Class:  
  This is where the magic happens. The Manager handles:
  - Generating all possible subsets of rectangles.
  - Testing every permutation for a valid arrangement on a grid.
  - Canonicalizing and recording each unique valid configuration.
  
- Home Class:  
  The Home class is responsible for displaying the results. It shows:
  - A panel with details about the entered rectangles.
  - A scrollable area where each valid rectangle configuration is drawn.
  - Buttons to load new sets and update the display.
  
- NewSetForm Class:  
  When you want to try out a different set of rectangles, the NewSetForm pops up. You enter:
  - The number of rectangles.
  - For each rectangle, its name, width, and height.
  - The form validates your input before passing it to the Manager to recompute the valid configurations.

## Keys

- Algorithmic Challenge:  
  It combines recursion, backtracking, and clever duplicate detection to solve a tricky layout puzzle.
  
- Practical GUI Application:  
  Not only does it solve the problem, but it also displays the solutions in a clear, interactive way. You can see how different rectangle configurations work in real time.
  
## How to Use It

1. Default Setup:  
   When you run the project, it starts with a default set of rectangles. The Manager processes these, and the Home interface shows all the valid ways to pack them into one large rectangle.

2. Custom Sets:  
   Hit the New Set button to open the form. Enter the number of rectangles and fill in the details for each one (name, width, and height). Submit your input to see your own set of valid configurations.
