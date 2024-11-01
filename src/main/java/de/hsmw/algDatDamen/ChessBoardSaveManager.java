package de.hsmw.algDatDamen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ChessBoardSaveManager {
    private static final String FILE_PATH = "chessboards.json";
    private Gson gson;

    public ChessBoardSaveManager() {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    public void saveChessBoard(ChessBoard chessBoard) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(chessBoard, writer);
            System.out.println("ChessBoard " + chessBoard.getSize() + "saved to file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ChessBoard> getChessBoards() {

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found, returning empty list.");
            return new ArrayList<ChessBoard>();
        }

        //load chessboards out of file
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type chessBoardListType = new TypeToken<ArrayList<ChessBoard>>() {}.getType();
            return gson.fromJson(reader, chessBoardListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<ChessBoard>();
    }
}