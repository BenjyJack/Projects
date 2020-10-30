package edu.yu.cs.com1320.project.stage2.impl;

import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.stage2.DocumentStore;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.Command;
import java.util.function.Function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class DocumentStoreImpl implements DocumentStore {
    // create Hash Table
    private HashTableImpl table = new HashTableImpl();
    private StackImpl stack =  new StackImpl();

    public int putDocument(InputStream input, URI uri, DocumentFormat format) {
        if (format == null || uri == null) {
            throw new IllegalArgumentException();
        }
        byte[] docInfo = fromInputToByte(input);
        return putIntoTable(uri, format, docInfo);
    }
    private int putIntoTable(URI uri, DocumentFormat format, byte[] docInfo){
        if (format == DocumentFormat.PDF) {
            return storePDF(uri, docInfo);
        }
        if (format == DocumentFormat.TXT) {
            return storeTXT(uri, docInfo);
        }
        return 0;
    }
    private int storeTXT(URI uri, byte[] docInfo){
        String text = new String(docInfo).trim();
        DocumentImpl doc = new DocumentImpl(uri, text, text.hashCode());
        DocumentImpl old = (DocumentImpl) this.table.get(uri.hashCode());
        if (old == null){
            return addedNew(uri, doc);
        }
        if (doc.equals(old)) {
            Command doneNothing = new Command (uri, doneNothingUndo);
            this.stack.push(doneNothing);
            return text.hashCode();
        }
        Command replacedDoc = new Command(uri, replacedDocUndo);
        this.stack.push(replacedDoc);
        this.table.put(uri.hashCode(), doc);
        return old.getDocumentTextHashCode();
    }
    private Function<URI, Boolean> doneNothingUndo = (URI uri) -> {
        return true;
    };
    private Function<URI, Boolean> replacedDocUndo = (URI uri) -> {
        this.table.put(uri.hashCode(), this.table.get(uri.hashCode()));
        return true;
    };
    private Function<URI, Boolean> addedNewUndo = (URI uri) -> {
        this.table.put(uri.hashCode(), null);
        return true;
    };
    private int storePDF(URI uri, byte[] docInfo){
        DocumentImpl doc = createPDFDoc(uri, docInfo);
        DocumentImpl old = (DocumentImpl) this.table.get(uri.hashCode());
        if (doc.equals(old)) {
            Command doneNothing = new Command (uri, doneNothingUndo);
            this.stack.push(doneNothing);
            return old.getDocumentTextHashCode();
        }
        if (old == null) {
            return addedNew(uri, doc);
        }
        Command replacedDoc = new Command(uri, replacedDocUndo);
        this.stack.push(replacedDoc);
        this.table.put(uri.hashCode(), doc);
        return old.getDocumentTextHashCode();
    }
    private int addedNew(URI uri, DocumentImpl doc){
        Command addedNew = new Command(uri, addedNewUndo);
        this.stack.push(addedNew);
        this.table.put(uri.hashCode(), doc);
        return 0;
    }
    private DocumentImpl createPDFDoc(URI uri, byte[] info){
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
        return new DocumentImpl(uri, text, text.hashCode(), info);
    }
    public byte[] getDocumentAsPdf(URI uri) {
        DocumentImpl doc = (DocumentImpl) this.table.get(uri.hashCode());
        return doc.getDocumentAsPdf();
    }
    public String getDocumentAsTxt(URI uri) {
        DocumentImpl doc = (DocumentImpl) this.table.get(uri.hashCode());
        return doc.getDocumentAsTxt();
    }
    public boolean deleteDocument(URI uri) {
        Command replacedDoc = new Command(uri, replacedDocUndo);
        this.stack.push(replacedDoc);
        return this.table.put(uri.hashCode(), null) != null;
    }
    protected DocumentImpl getDocument(URI uri){
        return (DocumentImpl) this.table.get(uri.hashCode());
    }
    public void undo() throws IllegalStateException {
        Command undo = (Command) stack.pop();
        undo.undo();
    }

    public void undo(URI uri) throws IllegalStateException {
        StackImpl holdingStack = new StackImpl();
        Command command = (Command) stack.pop();
        boolean nullCheck = true;
        do {
            if(command == null){
                nullCheck = false;
                break;
            }
            holdingStack.push(command);
            command = (Command) stack.pop();
        } while (command.getUri() != uri);
        if(nullCheck) {
            command.undo();
        }
        while (holdingStack.size() != 0){
            Command command2 = (Command) holdingStack.pop();
            stack.push(command2);
        }
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
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }
}