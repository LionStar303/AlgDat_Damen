package de.hsmw.algDatDamen.saveManager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

public class TutorialSaveManager {

    private final ArrayList<Tutorial> tutorialList;
    private final ArrayList<Tutorial> tempProgressList;
    private static final String FILE_PATH = "tutorials.json";
    private Gson gson;

    public TutorialSaveManager() {
        this.tutorialList = new ArrayList<Tutorial>();
        this.tempProgressList = new ArrayList<Tutorial>();
        gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        loadTutorialProgress();
    }

    public void saveTutorialProgress() {
        // save chessboards to file
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tutorialList, writer);
            System.out.println(tutorialList.size() + " Tutorial progress saved to file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTutorialProgress() {

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found, returning empty list.");
            return;
        }

        // load progress out of file
        try (FileReader fileReader = new FileReader(FILE_PATH)) {
            Tutorial[] ProgressList = gson.fromJson(fileReader, Tutorial[].class);
            tutorialList.clear();
            for (Tutorial tut : ProgressList) {
                tempProgressList.add(tut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Tutorial> getTutorialList() {
        return tutorialList;
    }

    public int getProgress(Player player) {
        for (Tutorial tutorial : tempProgressList) {
            if (player == tutorial.getPlayer()) {
                return tutorial.getProgress();
            }
        }
        return 0;
    }

}
