package edu.yu.cs.com1320.project.stage5.impl;

import com.google.gson.*;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;

import java.io.FileWriter;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * created by the document store and given to the BTree via a call to BTree.setPersistenceManager
 */
public class DocumentPersistenceManager implements PersistenceManager<URI, Document> {
    private File baseDir;

    public DocumentPersistenceManager(File baseDir){
        this.baseDir = baseDir;
    }

    private JsonSerializer<DocumentImpl> documentJsonSerializer = (DocumentImpl val, Type typeOfSource, JsonSerializationContext context) -> {
        JsonObject object = new JsonObject();
        Gson gson = new Gson();
        String text = val.getDocumentAsTxt();
        object.addProperty("text", text);
        object.addProperty("URI", val.getKey().toString());
        object.addProperty("hashcode", val.getDocumentTextHashCode());
        object.addProperty("wordMap", gson.toJson(val.getWordMap()));
        return object;
    };

    @Override
    public void serialize(URI uri, Document val) throws IOException {
        Gson gson =new GsonBuilder().registerTypeAdapter(DocumentImpl.class, documentJsonSerializer).setPrettyPrinting().create();
        Type documentType =new TypeToken<DocumentImpl>(){}.getType();
        String directories = uri.getRawPath();
        directories.replaceAll("//", File.separator);
        File file = new File(baseDir, uri.getAuthority() + File.separator + directories);
        file.mkdirs();
        file = new File(baseDir, uri.getAuthority() + File.separator + directories + ".json");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(gson.toJson(val, documentType));
        writer.close();
    }

    @Override
    public Document deserialize(URI uri) throws IOException {
        String directory = uri.getPath();
        File file = new File(baseDir, uri.getAuthority() + File.separator + directory + ".json");
        Scanner scanner = new Scanner(file);
        String json = new String();
        while (scanner.hasNextLine()) {
            String data = scanner.nextLine();
            json = json + data;
        }
        scanner.close();
        Gson gson = new GsonBuilder().registerTypeAdapter(DocumentImpl.class, documentJsonDeserializer).setPrettyPrinting().create();
        Type type = new TypeToken<DocumentImpl>(){}.getType();
        DocumentImpl document = gson.fromJson(json, type);
        return document;
    }
    private JsonDeserializer<DocumentImpl> documentJsonDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
        Gson gson = new Gson();
        String text = json.getAsJsonObject().get("text").getAsString();
        URI uri = null;
        try {
            uri = new URI(json.getAsJsonObject().get("URI").getAsString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int hashcode = json.getAsJsonObject().get("hashcode").getAsInt();
        Type type2 = new TypeToken<HashMap<String, Integer>>(){}.getType();
        HashMap<String, Integer> wordMap = gson.fromJson(json.getAsJsonObject().get("wordMap").getAsString(), type2);
        DocumentImpl doc = new DocumentImpl(uri, text, hashcode);
        doc.setWordMap(wordMap);
        return doc;
    };
}
