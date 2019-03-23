import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Room> rooms;
    static int LuckyNode, RocketNode;

    public static void main(String[] args) {
        int roomSize, corridorSize;
        try {
            File file = new File("input.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(fileInputStream);
            roomSize = scanner.nextInt();
            corridorSize = scanner.nextInt();
            rooms = new ArrayList<>();
            for (int i = 0; i < roomSize - 1; i++) {
                Room mainRoom = createMainRoom(i + 1, scanner.next());
                rooms.add(mainRoom);
            }
            Room goalRoom = createGoalRoom(roomSize);
            rooms.add(goalRoom);

            LuckyNode = scanner.nextInt();
            RocketNode = scanner.nextInt();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineItems = line.split(" ");
                if (lineItems.length == 3) {
                    Room neighborRoom = createNeighborsRoom(Integer.parseInt(lineItems[1]), lineItems[2]);
                    rooms.get(Integer.parseInt(lineItems[0]) - 1).getNeighbors().add(neighborRoom);
                }
            }

            String traverse = traverse(LuckyNode, RocketNode, rooms, "");

            if (traverse != null) {
                String ans = convertAnswer(traverse);
                System.out.println(ans);
            } else
                System.out.println("can't solve!");
        } catch (FileNotFoundException e) {
            System.out.println("invalid input path!");
        }

    }

    public static Room createMainRoom(int roomID, String colorCode) {
        Room room = new Room();
        room.setID(roomID);
        room.setColor(Color.findByColorCode(colorCode));
        room.setNeighbors(new ArrayList<>());
        room.setCorridor(null);
        room.setVisitedRooms(new ArrayList<>());
        return room;
    }

    public static Room createGoalRoom(int roomID) {
        Room goalRoom = new Room();
        goalRoom.setID(roomID);
        goalRoom.setCorridor(null);
        goalRoom.setNeighbors(null);
        goalRoom.setColor(null);
        goalRoom.setVisitedRooms(new ArrayList<>());
        return goalRoom;
    }

    public static Room createNeighborsRoom(int destinationRoomID, String corridorColor) {
        Room neighborRoom = new Room();
        neighborRoom.setID(destinationRoomID);
        neighborRoom.setColor(rooms.get(destinationRoomID - 1).getColor());
        neighborRoom.setNeighbors(null);
        neighborRoom.setCorridor(Color.findByColorCode(corridorColor));
        neighborRoom.setVisitedRooms(null);
        return neighborRoom;
    }

    public static String traverse(int LuckyNode, int RocketNode, ArrayList<Room> rooms, String traverse) {
        String currentState = "Lucky Node:" + Integer.toString(LuckyNode) + "---Rocket Node:" + Integer.toString(RocketNode);
        String[] allStates = traverse.split("\n");
        for (int i = 0; i < allStates.length; i++) {
            if (currentState.equals(allStates[i])) {
                System.out.println(traverse);
                System.out.println("-------------------------------STARVATION-------------------------------");
                return null;
            }
        }
        traverse += currentState + "\n";
        if (rooms.get(LuckyNode - 1).getColor() == null || rooms.get(RocketNode - 1).getColor() == null) {
            return traverse;
        }

        boolean isRepeated = false;
        for (int i = 0; i < rooms.get(LuckyNode - 1).getVisitedRooms().size(); i++) {
            if (RocketNode == rooms.get(LuckyNode - 1).getVisitedRooms().get(i)) {
                isRepeated = true;
                break;
            }
        }
        if (isRepeated)
            return null;
        else {

            // Lucky move
            for (int i = 0; i < rooms.get(LuckyNode - 1).getNeighbors().size(); i++) {
                if (rooms.get(LuckyNode - 1).getNeighbors().get(i).getCorridor().equal(rooms.get(LuckyNode - 1).getColor())
                        || rooms.get(LuckyNode - 1).getNeighbors().get(i).getCorridor().equal(rooms.get(RocketNode - 1).getColor())) {
                    int newLuckyNode = rooms.get(LuckyNode - 1).getNeighbors().get(i).getID();
                    String res = traverse(newLuckyNode, RocketNode, rooms, traverse);
                    if (res != null)
                        return res;
                }
            }

            // Rocket move
            for (int i = 0; i < rooms.get(RocketNode - 1).getNeighbors().size(); i++) {
                if (rooms.get(RocketNode - 1).getNeighbors().get(i).getCorridor().equal(rooms.get(LuckyNode - 1).getColor())
                        || rooms.get(RocketNode - 1).getNeighbors().get(i).getCorridor().equal(rooms.get(RocketNode - 1).getColor())) {
                    int newRocketNode = rooms.get(RocketNode - 1).getNeighbors().get(i).getID();
                    String res = traverse(LuckyNode, newRocketNode, rooms, traverse);
                    if (res != null)
                        return res;
                }
            }

            rooms.get(RocketNode - 1).getVisitedRooms().add(LuckyNode);
            rooms.get(LuckyNode - 1).getVisitedRooms().add(RocketNode);
        }
        System.out.println(traverse);
        System.out.println("-------------------------------DEADLOCK-------------------------------");
        return null;
    }

    private static String convertAnswer(String oldAns) {
        String convertedAns = "";
        String[] oldAnsLines = oldAns.split("\n");
        for (int i = 1; i < oldAnsLines.length; i++) {
            String[] lastPositions = oldAnsLines[i-1].split("---");
            String[] currentPositions = oldAnsLines[i].split("---");
            String lastLuckyPosition = "", lastRocketPosition = "", currentLuckyPosition = "", currentRocketPosition = "";
            for (int j = 0; j < lastPositions.length; j++) {
                if (lastPositions[j].contains("Lucky"))
                    lastLuckyPosition = lastPositions[j].split(":")[1];
                else if (lastPositions[j].contains("Rocket"))
                    lastRocketPosition = lastPositions[j].split(":")[1];
            }
            for (int j = 0; j < currentPositions.length; j++) {
                if (currentPositions[j].contains("Lucky"))
                    currentLuckyPosition = currentPositions[j].split(":")[1];
                else if (currentPositions[j].contains("Rocket"))
                    currentRocketPosition = currentPositions[j].split(":")[1];
            }
            if (!currentLuckyPosition.equals(lastLuckyPosition))
                convertedAns += "L : " + currentLuckyPosition + "\n";
            if (!currentRocketPosition.equals(lastRocketPosition))
                convertedAns += "R : " + currentRocketPosition+ "\n";

        }
        return convertedAns;
    }

}
