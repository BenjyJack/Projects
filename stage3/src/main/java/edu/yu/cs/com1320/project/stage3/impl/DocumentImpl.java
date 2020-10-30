package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.stage3.Document;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

public class DocumentImpl implements Document {
    private String text;
    private URI uri;
    private int hash;
    private byte[] pdfInfo;
    private HashMap words;

    public DocumentImpl(URI uri, String txt, int textHash){
        this.uri = uri;
        this.text = txt.trim();
        this.hash = textHash;
        this.words = splitWord(txt);
    }

    public DocumentImpl(URI uri, String txt, int textHash, byte[] pdfBytes){
        this.uri = uri;
        this.text = txt;
        this.hash = textHash;
        this.pdfInfo = pdfBytes;
        this.words = splitWord(txt);
    }
    public byte[] getDocumentAsPdf() {
        if(pdfInfo == null){
            this.TextToPDF();
        }
        return pdfInfo;
    }
    private void TextToPDF(){
        //create  variables for pdf file
        String name = this.text;
        String message = this.text;
        byte[] data = null;
        try {
            //create pdf document
            PDDocument document = new PDDocument();
            try {
                PDPage page = new PDPage();
                document.addPage(page);

                PDFont font = PDType1Font.HELVETICA_BOLD;

                PDPageContentStream contents = new PDPageContentStream(document, page);
                contents.beginText();
                contents.setFont(font, 30);
                contents.newLineAtOffset(50, 700);
                contents.showText(message);
                contents.endText();
                contents.close();

                document.save(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //take pdf file and create to byte[]
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            document.close();
            data = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfInfo = data;
    }

    public String getDocumentAsTxt() {
        return text.trim();
    }

    public int getDocumentTextHashCode() {
        return text.trim().hashCode();
    }

    public URI getKey() {
        return uri;
    }

    public int wordCount(String word) {
        if(words.containsKey(word.toLowerCase())) {
            return (int) words.get(word.toLowerCase());
        }
        return 0;
    }

    private HashMap splitWord (String text){
        String[] split = text.toLowerCase().split(" ");
        HashMap wordCounter = new HashMap<String, Integer>();
        for (int i = 0; i < split.length; i++) {
            if(wordCounter.containsKey(split[i])){
                wordCounter.put(split[i], (int) wordCounter.get(split[i]) +1);
            }else{
                wordCounter.put(split[i], 1);
            }
        }
        return wordCounter;
    }

}
