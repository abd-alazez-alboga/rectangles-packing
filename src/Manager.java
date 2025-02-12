
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Manager {
    private static final ArrayList<ArrayList<Rectangle>> allSets = new ArrayList();
    private static final ArrayList<TopLeft> dimensions = new ArrayList();
    private static int counter = 0;
    private static ArrayList<Rectangle> enteredRectangles = new ArrayList();
    private static final HashSet<String> canonicalValidSets = new HashSet();

    public static void setEnteredRectangles(ArrayList<Rectangle> rectangles) {
        enteredRectangles = new ArrayList(rectangles);
    }

    public static ArrayList<Rectangle> getEnteredRectangles() {
        return enteredRectangles;
    }

    public Manager() {
        if (allSets.isEmpty()) {
            this.start();
        }

    }

    public static ArrayList<TopLeft> getDimensions() {
        return dimensions;
    }

    public static ArrayList<ArrayList<Rectangle>> getAllSets() {
        return allSets;
    }

    public void start() {
        ArrayList<Rectangle> rectangles = new ArrayList();
        rectangles.add(new Rectangle("A", 2, 1));
        rectangles.add(new Rectangle("B", 2, 1));
        rectangles.add(new Rectangle("C", 3, 1));
        rectangles.add(new Rectangle("D", 3, 5));
        rectangles.add(new Rectangle("E", 4, 3));
        rectangles.add(new Rectangle("F", 4, 2));
        setEnteredRectangles(rectangles);
        allSets.clear();
        dimensions.clear();
        canonicalValidSets.clear();
        this.getSubsets(rectangles);
    }

    public void processNewSet(ArrayList<Rectangle> rectangles) {
        setEnteredRectangles(rectangles);
        allSets.clear();
        dimensions.clear();
        canonicalValidSets.clear();
        this.getSubsets(rectangles);
    }

    public ArrayList<Integer> getRectangleNeededInfo(ArrayList<Rectangle> rectangles) {
        ArrayList<Integer> neededInfo = new ArrayList();
        int space = 0;
        int sum_of_widths = 0;
        int sum_of_heights = 0;
        int max_width = 0;
        int max_height = 0;
        int min_width = Integer.MAX_VALUE;
        int min_height = Integer.MAX_VALUE;

        for(Rectangle rectangle : rectangles) {
            int width = rectangle.getWidth();
            int height = rectangle.getHeight();
            space += width * height;
            sum_of_widths += width;
            sum_of_heights += height;
            max_width = Math.max(max_width, width);
            max_height = Math.max(max_height, height);
            min_width = Math.min(min_width, width);
            min_height = Math.min(min_height, height);
        }

        Collections.addAll(neededInfo, new Integer[]{space, sum_of_widths, sum_of_heights, max_width, max_height, min_width, min_height});
        return neededInfo;
    }

    public boolean getPossibleWidthAndHeight(ArrayList<Integer> neededInfo, ArrayList<Rectangle> rectangles) {
        int space = (Integer)neededInfo.get(0);
        int sum_of_heights = (Integer)neededInfo.get(2);
        int max_width = (Integer)neededInfo.get(3);
        int max_height = (Integer)neededInfo.get(4);
        int h = max_height;

        boolean valid;
        for(valid = false; !valid && h <= sum_of_heights; ++h) {
            if (space % h == 0) {
                int w = space / h;
                valid = this.isValid(rectangles, w, h);
                if (valid) {
                    break;
                }
            }
        }

        return valid;
    }

    public boolean isValid(ArrayList<Rectangle> rectangles, int width, int height) {
        return this.generatePermutations(rectangles, 0, rectangles.size() - 1, width, height);
    }

    private boolean generatePermutations(ArrayList<Rectangle> rectangles, int start, int end, int width, int height) {
        if (start == end) {
            return this.findTopLeftCoordinates(rectangles, width, height);
        } else {
            for(int i = start; i <= end; ++i) {
                Collections.swap(rectangles, start, i);
                if (this.generatePermutations(rectangles, start + 1, end, width, height)) {
                    return true;
                }

                Collections.swap(rectangles, start, i);
            }

            return false;
        }
    }

    public boolean findTopLeftCoordinates(ArrayList<Rectangle> rectangles, int width, int height) {
        int[][] grid = new int[height][width];
        boolean allPlaced = true;
        ArrayList<Rectangle> validRectangles = new ArrayList();

        int i;
        for(i = 0; i < rectangles.size(); ++i) {
            Rectangle rectangle = (Rectangle)rectangles.get(i);
            boolean placed = false;

            for(int y = 0; y <= height - rectangle.getHeight(); ++y) {
                for(int x = 0; x <= width - rectangle.getWidth(); ++x) {
                    if (this.canPlaceRectangle(grid, x, y, rectangle)) {
                        this.placeRectangle(grid, x, y, rectangle);
                        validRectangles.add(new Rectangle(rectangle.getName(), i + 1, rectangle.getWidth(), rectangle.getHeight(), new TopLeft(x, y)));
                        rectangle.setTopLeft(new TopLeft(x, y));
                        placed = true;
                        break;
                    }
                }

                if (placed) {
                    break;
                }
            }

            if (!placed) {
                allPlaced = false;
                break;
            }
        }

        if (allPlaced && i == rectangles.size()) {
            ArrayList<Rectangle> canonicalSet = new ArrayList(validRectangles);
            Collections.sort(canonicalSet, (r1, r2) -> r1.getName().compareTo(r2.getName()));
            StringBuilder sb = new StringBuilder();

            for(Rectangle r : canonicalSet) {
                sb.append(r.getName()).append("|");
            }

            String key = sb.toString();
            if (!canonicalValidSets.contains(key)) {
                canonicalValidSets.add(key);
                ++counter;
                allSets.add(canonicalSet);
                dimensions.add(new TopLeft(width, height));
            }
        }

        return allPlaced;
    }

    private boolean canPlaceRectangle(int[][] grid, int startX, int startY, Rectangle rectangle) {
        for(int y = 0; y < rectangle.getHeight(); ++y) {
            for(int x = 0; x < rectangle.getWidth(); ++x) {
                if (grid[startY + y][startX + x] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private void placeRectangle(int[][] grid, int startX, int startY, Rectangle rectangle) {
        for(int y = 0; y < rectangle.getHeight(); ++y) {
            for(int x = 0; x < rectangle.getWidth(); ++x) {
                grid[startY + y][startX + x] = 1;
            }
        }

    }

    public ArrayList<ArrayList<Rectangle>> getSubsets(ArrayList<Rectangle> rectangles) {
        ArrayList<ArrayList<Rectangle>> subsets = new ArrayList();
        this.generateSubsets(rectangles, 0, new ArrayList(), subsets);
        return subsets;
    }

    private void generateSubsets(ArrayList<Rectangle> rectangles, int index, ArrayList<Rectangle> currentSet, ArrayList<ArrayList<Rectangle>> subsets) {
        if (index == rectangles.size()) {
            if (!currentSet.isEmpty() && this.getAPossibleSet(currentSet)) {
                subsets.add(new ArrayList(currentSet));
            }

        } else {
            this.generateSubsets(rectangles, index + 1, currentSet, subsets);
            currentSet.add((Rectangle)rectangles.get(index));
            this.generateSubsets(rectangles, index + 1, currentSet, subsets);
            currentSet.remove(currentSet.size() - 1);
        }
    }

    private boolean getAPossibleSet(ArrayList<Rectangle> rectangles) {
        ArrayList<Rectangle> sortedRectangles = new ArrayList(rectangles);
        Collections.sort(sortedRectangles, (r1, r2) -> r1.getName().compareTo(r2.getName()));
        ArrayList<Integer> neededInfo = this.getRectangleNeededInfo(sortedRectangles);
        return this.getPossibleWidthAndHeight(neededInfo, sortedRectangles);
    }
}
