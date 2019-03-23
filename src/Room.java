import java.util.ArrayList;

public class Room {
    private int ID;
    private Color color;
    private Color corridor;
    private ArrayList<Room> neighbors;
    private ArrayList<Integer> visitedRooms;

    public Room() {

    }

    public Room(int ID, Color color, Color corridor, ArrayList<Room> neighbors) {
        this.ID = ID;
        this.color = color;
        this.corridor = corridor;
        this.neighbors = neighbors;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getCorridor() {
        return corridor;
    }

    public void setCorridor(Color corridor) {
        this.corridor = corridor;
    }

    public ArrayList<Room> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Room> neighbors) {
        this.neighbors = neighbors;
    }

    public ArrayList<Integer> getVisitedRooms() {
        return visitedRooms;
    }

    public void setVisitedRooms(ArrayList<Integer> visitedRooms) {
        this.visitedRooms = visitedRooms;
    }
}
