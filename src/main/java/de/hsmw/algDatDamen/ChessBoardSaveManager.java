package de.hsmw.algDatDamen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.bukkit.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ChessBoardSaveManager {

    private final ArrayList<ChessBoard> cbList;
    private static final String FILE_PATH = "chessboards.json";
    private Gson gson;

    public ChessBoardSaveManager() {
        this.cbList = new ArrayList<ChessBoard>();
        gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED, Modifier.STATIC).registerTypeAdapter(Location.class, new LocationAdapter()).setPrettyPrinting().create();
        loadChessBoards();
    }

    public void saveChessBoards() {
        //save chessboards to file
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cbList, writer);
            System.out.println(cbList.size() + " ChessBoards saved to file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadChessBoards() {

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found, returning empty list.");
            return;
        }

        //load chessboards out of file
        try (FileReader fileReader = new FileReader(FILE_PATH)) {
            ChessBoard[] cbArray = gson.fromJson(fileReader, ChessBoard[].class);
            cbList.clear();
            for (ChessBoard cb : cbArray) {
                cbList.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public ArrayList<ChessBoard> getCbList() {
        return cbList;
    }

}