import java.util.List;

public abstract class TabularAlgorithm {
    protected String[] labels;
    protected List<int[]> rows;

    protected abstract String[] getLabels();
    protected abstract List<int[]> getRows();

    protected static final int mod(int a, int b) {
        return (a % b + b) % b;
    }

    protected final void printTable() {
        String[] labels = getLabels();
        List<int[]> rows = getRows();
        int[] maxWidthOfColumns = new int[labels.length];
        for(int i = 0; i < labels.length; i++) {
            int maxWidth = labels[i].length();
            for(int j = 0; j < rows.size(); j++) {
                int currentWidth = String.valueOf(rows.get(j)[i]).length();
                if(currentWidth > maxWidth) {
                    maxWidth = currentWidth;
                }
            }
            maxWidthOfColumns[i] = maxWidth;
        }
        String[] spaces = new String[12];
        spaces[0] = "";
        for(int i = 1; i < 12; i++) {
            spaces[i] = spaces[i - 1] + " ";
        }
        StringBuilder header = new StringBuilder();
        for(int i = 0; i < labels.length; i++) {
            header.append(" " + getNumSpaces(spaces, Math.max(maxWidthOfColumns[i] - labels[i].length(), 0)) + labels[i] + " " + (i == labels.length - 1 ? "" : "|"));
        }
        System.out.println(header);
        StringBuilder dashes = new StringBuilder();
        for(int i = 0; i < header.length(); i++) {
            dashes.append("-");
        }
        System.out.println(dashes);
        StringBuilder headerFormatBuilder = new StringBuilder();
        for(int i = 0; i < labels.length; i++) {
            headerFormatBuilder.append(" %" + maxWidthOfColumns[i] + "d " + (i != labels.length - 1 ? "|" : ""));
        }
        headerFormatBuilder.append("%n");
        String headerFormat = headerFormatBuilder.toString();
        for(int i = 0; i < rows.size(); i++) {
            int[] row = rows.get(i);
            for(int j = 0; j < row.length; j++) {
                System.out.printf(" %" + maxWidthOfColumns[j] + "d " + (j != labels.length - 1 ? "|" : "%n"), row[j]);
            }
        }
    }

    private static String getNumSpaces(String[] spaces, int n) {
        StringBuilder space = new StringBuilder(spaces[Math.min(n, spaces.length - 1)]);
        for(int i = spaces.length; i <= n; i++) {
            space.append(" ");
        }
        return space.toString();
    }
}
