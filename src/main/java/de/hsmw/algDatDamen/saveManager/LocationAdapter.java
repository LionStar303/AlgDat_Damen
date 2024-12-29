package de.hsmw.algDatDamen.saveManager;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Custom GSON TypeAdapter for serializing and deserializing Bukkit.Location
 * objects.
 */
public class LocationAdapter extends TypeAdapter<Location> {

    @Override
    public void write(JsonWriter writer, Location location) throws IOException {
        writer.beginObject();
        writer.name("x").value(location.getX());
        writer.name("y").value(location.getY());
        writer.name("z").value(location.getZ());
        writer.name("world").value(location.getWorld().getName());
        writer.endObject();
    }

    @Override
    public Location read(JsonReader reader) throws IOException {
        reader.beginObject();
        Double x = null, y = null, z = null;
        String worldName = null;

        while (reader.hasNext()) {
            String fieldName = reader.nextName();
            switch (fieldName) {
                case "x":
                    x = reader.nextDouble();
                    break;
                case "y":
                    y = reader.nextDouble();
                    break;
                case "z":
                    z = reader.nextDouble();
                    break;
                case "world":
                    worldName = reader.nextString();
                    break;
                default:
                    reader.skipValue(); // Skip any unexpected fields
                    break;
            }
        }
        reader.endObject();

        // Handle null values and validate world existence
        if (x == null || y == null || z == null || worldName == null) {
            throw new IllegalStateException("Invalid Location: Missing coordinates or world");
        }
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("World not found: " + worldName);
        }

        return new Location(world, x, y, z);
    }

}
