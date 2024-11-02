package de.hsmw.algDatDamen;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class ChessBoardAdapter extends TypeAdapter<ChessBoard> {

    @Override
    public void write(JsonWriter out, ChessBoard chessBoard) throws IOException {
        out.beginObject();
        out.name("size").value(chessBoard.getSize());
        out.name("console").value(chessBoard.isConsole());
        out.name("originCorner").value(chessBoard.getOriginCorner().toString()); // Beispiel: Passe `toString` für Location an
        out.name("direction").value(chessBoard.getDirection().toString()); // Beispiel: Passe `toString` für Vector an
        out.name("oCWhite").value(chessBoard.isOCWhite());
        out.name("collisionCarpets").value(chessBoard.isCollisionCarpets());

        // Serialize queens list
        out.name("queens");
        out.beginArray();
        for (Queen queen : chessBoard.getQueens()) {
            out.beginObject();
            out.name("x").value(queen.getX());
            out.name("y").value(queen.getY());
            out.name("isSuperQueen").value(queen.isSuperQueen());
            out.endObject();
        }
        out.endArray();

        out.endObject();
    }

    @Override
    public ChessBoard read(JsonReader in) throws IOException {
        ChessBoard chessBoard = new ChessBoard();
        ArrayList<Queen> queens = new ArrayList<>();

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "size":
                    chessBoard.setSize(in.nextInt());
                    break;
                case "console":
                    chessBoard.setConsole(in.nextBoolean());
                    break;
                case "originCorner":
                    //chessBoard.setOriginCorner(Location.fromString(in.nextString())); // Beispiel: Ersetze mit Parser für Location
                    break;
                case "direction":
                    //chessBoard.setDirection(Vector.fromString(in.nextString())); // Beispiel: Ersetze mit Parser für Vector
                    break;
                case "oCWhite":
                    chessBoard.setOCWhite(in.nextBoolean());
                    break;
                case "collisionCarpets":
                    chessBoard.setCollisionCarpets(in.nextBoolean());
                    break;
                case "queens":
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        Queen queen = new Queen();
                        while (in.hasNext()) {
                            switch (in.nextName()) {
                                case "x":
                                    queen.setX(in.nextInt());
                                    break;
                                case "y":
                                    queen.setY(in.nextInt());
                                    break;
                                case "isSuperQueen":
                                    queen.setSuperQueen(in.nextBoolean());
                                    break;
                            }
                        }
                        in.endObject();
                        queens.add(queen);
                    }
                    in.endArray();
                    chessBoard.setQueens(queens);
                    break;
            }
        }
        in.endObject();
        
        return chessBoard;
    }
}
