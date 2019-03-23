public enum Visit {
    UNDISCOVERED(1),
    DISCOVERED(2),
    PROCESSED(3);
    private int visitMode;

    private Visit(int visitMode) {
        this.visitMode = visitMode;
    }

    public int getVisitMode() {
        return visitMode;
    }

    public void setVisitMode(int visitMode) {
        this.visitMode = visitMode;
    }

    public static Visit findByVisitMode(int visitMode) {
        for (Visit item : Visit.values())
            if (item.getVisitMode() == visitMode)
                return item;
        return null;
    }
}
