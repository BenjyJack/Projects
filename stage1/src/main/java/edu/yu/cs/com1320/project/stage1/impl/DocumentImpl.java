package edu.yu.cs.com1320.project.stage1.impl;
import edu.yu.cs.com1320.project.stage1.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class DocumentImpl implements Document{
    //store variables
    private String text;
    private URI uri;
    private int hash;
    private byte[] pdfInfo;

    public DocumentImpl(URI uri, String txt, int textHash){
        this.uri = uri;
        this.text = txt.trim();
        this.hash = textHash;
    }
    public DocumentImpl(URI uri, String txt, int textHash, byte[] pdfBytes){
        this.uri = uri;
        this.text = txt;
        this.hash = textHash;
        this.pdfInfo = pdfBytes;
    }
    // pull the pdf version of the document
    public byte[] getDocumentAsPdf(){
        // if we have a pdf version already, return the original
        if(pdfInfo == null){
            this.TextToPDF();
        }
        //if we only have a string, send to TextToPDF
        return pdfInfo;
        //TODO confirm this works
    }
    // return the txt as a string
    public String getDocumentAsTxt(){
        return text.trim();
    }
    //return the hashCode of the txt string
    public int getDocumentTextHashCode(){
        return text.hashCode();
    }
    //return the uri
    public URI getKey(){
        return uri;
    }
    @Override
    public boolean equals(Object j) {
        if(this == j){
            return true;
        }
        if(!(j instanceof DocumentImpl)){
            return false;
        }
        DocumentImpl doc = (DocumentImpl) j;
        //the equality only happens if BOTH the URI and txt have equivalent hashCodes
        return (this.getKey().hashCode() == doc.getKey().hashCode() && this.hash == doc.getDocumentTextHashCode());
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
}