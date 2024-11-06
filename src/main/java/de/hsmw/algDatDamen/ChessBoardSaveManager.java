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

    private final ArrayList<MChessBoard> cbList;
    private static final String FILE_PATH = "chessboards.json";
    private Gson gson;

    public ChessBoardSaveManager() {
        this.cbList = new ArrayList<MChessBoard>();
        gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationAdapter()).setPrettyPrinting().create();
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
            MChessBoard[] cbArray = gson.fromJson(fileReader, MChessBoard[].class);
            cbList.clear();
            for (MChessBoard cb : cbArray) {
                cbList.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public ArrayList<MChessBoard> getCbList() {
        return cbList;
    }

}