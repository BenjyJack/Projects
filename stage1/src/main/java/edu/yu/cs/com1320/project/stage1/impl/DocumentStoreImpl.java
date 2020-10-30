package edu.yu.cs.com1320.project.stage1.impl;

import edu.yu.cs.com1320.project.stage1.DocumentStore;
import edu.yu.cs.com1320.project.impl.HashTableImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class DocumentStoreImpl implements DocumentStore{
    // create Hash Table
    HashTableImpl table = new HashTableImpl();

    public int putDocument(InputStream input, URI uri, DocumentFormat format) {
        //check format and URI
        if (format == null || uri == null) {
            throw new IllegalArgumentException();
        }
        //create byte[] from inputStream
        byte[] info = fromInputToByte(input);
        //put document into the table
        if (format == DocumentFormat.PDF) {
            String text = null;
            try {
                PDDocument doc = PDDocument.load(info);
                text = new PDFTextStripper().getText(doc);
                doc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (text == null) {
                throw new IllegalArgumentException();
            }
            DocumentImpl doc = new DocumentImpl(uri, text, text.hashCode(), info);
            if (doc.equals(this.table.get(uri.hashCode()))) {
                return doc.getDocumentTextHashCode();
            }
            DocumentImpl old = (DocumentImpl) this.table.put(uri, doc);
            if (old == null) {
                return 0;
            }
            return old.getDocumentTextHashCode();
        }
        if (format == DocumentFormat.TXT) {
            String text = new String(info).trim();
            DocumentImpl entry = (DocumentImpl) this.table.get(uri.hashCode());
            if (entry != null && entry.getDocumentTextHashCode() == text.hashCode()) {
                return text.hashCode();
            }
            DocumentImpl doc = new DocumentImpl(uri, text, text.hashCode());
            DocumentImpl old = (DocumentImpl) this.table.put(uri.hashCode(), doc);
            if(old == null){
                return 0;
            }
            return old.getDocumentTextHashCode();
        }
        return 0;
    }

    // pulls the entire document from the table and return the pdf version
    public byte[] getDocumentAsPdf(URI uri) {
        DocumentImpl doc = (DocumentImpl) this.table.get(uri.hashCode());
        return doc.getDocumentAsPdf();
    }

    // pulls the entire document from the table and return the txt version
    public String getDocumentAsTxt(URI uri) {
        DocumentImpl doc = (DocumentImpl) this.table.get(uri.hashCode());
        return doc.getDocumentAsTxt();
    }

    //use the put method to delete the entire document from the table
    public boolean deleteDocument(URI uri) {
        return this.table.put(uri.hashCode(), null) != null;
    }

    private byte[] fromInputToByte(InputStream input) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int numRead;
        byte[] data = new byte[16384];
        try {
            while ((numRead = input.read(data, 0, data.length)) != -1) {
                buf.write(data, 0, numRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] info = buf.toByteArray();
        byte[] info = buf.toByteArray();
        byte[] info = buf.toByteArray();
        byte[] info = buf.toByteArray();
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }
}