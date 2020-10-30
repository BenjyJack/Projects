package edu.yu.cs.com1320.project.stage3.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage3.DocumentStore;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore {
    private HashTableImpl<URI, DocumentImpl> table = new HashTableImpl();
    private StackImpl<Undoable> undoableStack = new StackImpl();
    private HashMap<URI, StackImpl<DocumentImpl>> uriToDocumentStacks= new HashMap();
    private TrieImpl<DocumentImpl> trie = new TrieImpl();
    private String keyword;

    public int putDocument(InputStream input, URI uri, DocumentFormat format) {
        nullCheck(uri, format);
        byte[] docInfo = fromInputToByte(input);
        return putIntoTable(uri, format, docInfo);
    }

    private void nullCheck(URI uri, DocumentFormat format) {
        if (format == null || uri == null) {
            throw new IllegalArgumentException();
        }
    }

    private byte[] fromInputToByte(InputStream input) {
        if (input == null) {
            return null;
        }
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

    private int putIntoTable(URI uri, DocumentFormat format, byte[] docInfo) {
        if (docInfo == null) {
            DocumentImpl doc = table.get(uri);
            if (doc == null) {
                deleteDocument(uri);
                return 0;
            }
            deleteDocument(uri);
            return doc.getDocumentTextHashCode();
        }
        if (format == DocumentFormat.PDF) {
            return storePDF(uri, docInfo);
        }
        if (format == DocumentFormat.TXT) {
            return storeTXT(uri, docInfo);
        }
        return 0;
    }

    private int storePDF(URI uri, byte[] docInfo) {
        DocumentImpl doc = createPDFDoc(uri, docInfo);
        return insertDocument(uri, doc);
    }

    private int storeTXT(URI uri, byte[] docInfo) {
        String text = new String(docInfo).trim();
        DocumentImpl doc = new DocumentImpl(uri, text, text.hashCode());

        return insertDocument(uri, doc);
    }

    private DocumentImpl createPDFDoc(URI uri, byte[] info) {
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
        return new DocumentImpl(uri, text.trim(), text.hashCode(), info);
    }

    private int insertDocument(URI uri, DocumentImpl doc) {
        DocumentImpl old = this.table.get(uri);
        if (old == null) {
            return addedNew(uri, doc);
        }
        if (doc.equals(old)) {
            this.undoableStack.push(null);
        } else {
            GenericCommand<URI> replacedDoc = new GenericCommand<>(uri, undoAdd);
            this.undoableStack.push(replacedDoc);
            for (String word : old.getDocumentAsTxt().split(" ")) {
                this.trie.delete(word, old);
            }
            for (String word : doc.getDocumentAsTxt().split(" ")) {
                this.trie.put(word, doc);
            }
            HashSet<URI> set = new HashSet<URI>();
            set.add(uri);
            this.uriToDocumentStacks.get(uri).push(doc);
            this.table.put(uri, doc);
        }
        return old.getDocumentTextHashCode();
    }

    private int addedNew(URI uri, DocumentImpl doc) {
        this.table.put(uri, doc);
        for (String word : doc.getDocumentAsTxt().split(" ")) {
            this.trie.put(word, doc);
        }
        GenericCommand<URI> added = new GenericCommand<>(uri, undoAdd);
        this.undoableStack.push(added);
        StackImpl<DocumentImpl> documentStack = new StackImpl<>();
        this.uriToDocumentStacks.put(uri, documentStack);
        documentStack.push(doc);
        return 0;
    }

    private Function<URI, Boolean> undoAdd = (URI uri) -> {
        DocumentImpl doc1 = this.uriToDocumentStacks.get(uri).pop();
        DocumentImpl doc2 = this.uriToDocumentStacks.get(uri).peek();
        if(doc1 == null){
            return false;
        }
        this.table.put(uri, doc2);
        for (String word : doc1.getDocumentAsTxt().split(" ")) {
            this.trie.delete(word, doc1);
        }
        if(doc2 == null){
            return true;
        }
        for (String word : doc2.getDocumentAsTxt().split(" ")) {
            this.trie.put(word, doc2);
        }
        return true;
    };
    private Function<URI, Boolean> undoDelete = (URI uri) -> {
        DocumentImpl doc = this.uriToDocumentStacks.get(uri).pop();
        for (String word : doc.getDocumentAsTxt().split(" ")) {
            this.trie.put(word, doc);
        }
        this.table.put(uri, doc);
        return true;
    };

    public byte[] getDocumentAsPdf(URI uri) {
        DocumentImpl doc = this.getDocument(uri);
        if (doc == null) {
            return null;
        }
        return doc.getDocumentAsPdf();
    }

    public String getDocumentAsTxt(URI uri) {
        DocumentImpl doc = this.getDocument(uri);
        if (doc == null) {
            return null;
        }
        return doc.getDocumentAsTxt();
    }

    public boolean deleteDocument(URI uri) {
        DocumentImpl doc = this.table.get(uri);
        if (doc == null) {
            this.undoableStack.push(null);
            return false;
        }
        GenericCommand<URI> replacedDoc = new GenericCommand<>(uri, this.undoDelete);
        this.undoableStack.push(replacedDoc);
        HashSet<URI> set = new HashSet<>();
        set.add(uri);
        return this.table.put(uri, null) != null;
    }

    private boolean deleteDocument(URI uri, CommandSet commands) {
        DocumentImpl doc = this.table.get(uri);
        if (doc == null) {
            commands.addCommand(null);
            return false;
        }
        GenericCommand<URI> replacedDoc = new GenericCommand<>(uri, this.undoDelete);
        commands.addCommand(replacedDoc);
        HashSet<URI> set = new HashSet<>();
        set.add(uri);
        return this.table.put(uri, null) != null;
    }

    public void undo() throws IllegalStateException {
        Undoable undo = (Undoable) undoableStack.pop();
        if (undo == null) {
            throw new IllegalStateException();
        }
        undo.undo();
    }

    public void undo(URI uri) throws IllegalStateException {
        StackImpl<Undoable> holdingStack = new StackImpl<>();
        StackImpl<HashSet<DocumentImpl>> docHoldingStack = new StackImpl<>();
        while (true) {
            Undoable command = this.undoableStack.pop();
            if (command == null) {
                throw new IllegalStateException();
            }
            if (command instanceof GenericCommand) {
                if (((GenericCommand) command).getTarget() == uri) {
                    command.undo();
                    break;
                }
            }
            if (command instanceof CommandSet) {
                if (((CommandSet<URI>) command).containsTarget(uri)) {
                    CommandSet command2 = (CommandSet<URI>) command;
                    command2.undo(uri);
                    break;
                }
            }
            holdingStack.push(command);
        }
        while (holdingStack.size() != 0) {
            Undoable command2 = holdingStack.pop();
            this.undoableStack.push(command2);
        }
    }

    protected DocumentImpl getDocument(URI uri) {
        return (DocumentImpl) this.table.get(uri);
    }

    private Comparator<DocumentImpl> byWordCount = (DocumentImpl doc1, DocumentImpl doc2) -> {
        int num1 = doc1.wordCount(keyword);
        int num2 = doc2.wordCount(keyword);
        if (num1 == num2) {
            return 0;
        }
        if (num1 > num2) {
            return 1;
        }
        return -1;
    };
    private Comparator<DocumentImpl> byPrefixCount = (DocumentImpl doc1, DocumentImpl doc2) -> {
        int num1 = doc1.getDocumentAsTxt().split(keyword).length;
        int num2 = doc2.getDocumentAsTxt().split(keyword).length;
        if (num1 == num2) {
            return 0;
        }
        if (num1 > num2) {
            return 1;
        }
        return -1;
    };

    public List<String> search(String keyword) {
        this.keyword = keyword;
        List<DocumentImpl> docs = this.trie.getAllSorted(keyword, byWordCount);
        if (docs == null) {
            return new ArrayList<>(0);
        }
        List<String> text = new ArrayList<>(docs.size());
        for (DocumentImpl doc : docs) {
            text.add(doc.getDocumentAsTxt());
        }
        return text;
    }

    public List<byte[]> searchPDFs(String keyword) {
        this.keyword = keyword;
        List<DocumentImpl> docs = this.trie.getAllSorted(keyword, byWordCount);
        if (docs == null) {
            return new ArrayList<>(0);
        }
        List<byte[]> text = new ArrayList<>(docs.size());
        for (DocumentImpl doc : docs) {
            text.add(doc.getDocumentAsPdf());
        }
        return text;
    }

    public List<String> searchByPrefix(String prefix) {
        this.keyword = prefix;
        List<DocumentImpl> docs = this.trie.getAllWithPrefixSorted(prefix, byPrefixCount);
        if (docs == null) {
            return new ArrayList<>(0);
        }
        List<String> text = new ArrayList<>(docs.size());
        for (DocumentImpl doc : docs) {
            text.add(doc.getDocumentAsTxt());
        }
        return text;
    }

    public List<byte[]> searchPDFsByPrefix(String prefix) {
        this.keyword = prefix;
        List<DocumentImpl> docs = this.trie.getAllWithPrefixSorted(prefix, byPrefixCount);
        if (docs == null) {
            return new ArrayList<>(0);
        }
        List<byte[]> text = new ArrayList<>(docs.size());
        for (DocumentImpl doc : docs) {
            text.add(doc.getDocumentAsPdf());
        }
        return text;
    }

    public Set<URI> deleteAll(String key) {
        Set<DocumentImpl> docSet = this.trie.deleteAll(key);
        if (docSet == null) {
            return new HashSet<URI>(0);
        }
        CommandSet commands = new CommandSet();
        Set<URI> returnSet = new HashSet<URI>();
        return getUris(docSet, commands, returnSet);
    }

    public Set<URI> deleteAllWithPrefix(String prefix) {
        Set<DocumentImpl> docSet = this.trie.deleteAllWithPrefix(prefix);
        if (docSet == null) {
            return new HashSet<URI>(0);
        }
        CommandSet commands = new CommandSet();
        Set<URI> returnSet = new HashSet<URI>(docSet.size());
        return getUris(docSet, commands, returnSet);
    }

    private Set<URI> getUris(Set<DocumentImpl> docSet, CommandSet commands, Set<URI> returnSet) {
        for (DocumentImpl doc : docSet) {
            URI uri = doc.getKey();
            returnSet.add(uri);
            deleteDocument(uri, commands);
        }
        this.undoableStack.push(commands);
        return returnSet;
    }
}
