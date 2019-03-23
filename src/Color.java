public enum Color {
    BLUE("B", 1),
    RED("R", 2),
    GREEN("G", 3),
    YELLOW("Y", 4);

    private String colorCode;
    private int colorID;

    private Color(String colorCode, int colorID) {
        this.colorCode = colorCode;
        this.colorID = colorID;
    }

    public static Color findByColorCode(String colorCode) {
        for (Color item : Color.values())
            if (item.colorCode.equals(colorCode))
                return item;
        return null;
    }

    public boolean equal(Color color) {
        return colorID == color.getColorID();
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }
}
