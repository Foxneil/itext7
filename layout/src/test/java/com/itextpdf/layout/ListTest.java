package com.itextpdf.layout;

import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.test.annotations.type.IntegrationTest;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.test.ExtendedITextTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class ListTest extends ExtendedITextTest{

    static final public String sourceFolder = "./src/test/resources/com/itextpdf/layout/ListTest/";
    static final public String destinationFolder = "./target/test/com/itextpdf/layout/ListTest/";

    @BeforeClass
    static public void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void nestedListTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "nestedListTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_nestedListTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        List romanList2 = new List(Property.ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");

        List romanList = new List(Property.ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add((ListItem) new ListItem("Three").add(romanList2));

        List list = new List(Property.ListNumberingType.DECIMAL).setSymbolIndent(20).
                add("One").add("Two").add("Three").add("Four").add((ListItem) new ListItem("Roman List").add(romanList)).
                add("Five").add("Six").add((ListItem) new ListItem().add(romanList2));
        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listNumberingTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listNumberingTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listNumberingTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        java.util.List<List> lists = new ArrayList<>();
        lists.add(new List(Property.ListNumberingType.DECIMAL));
        lists.add(new List(Property.ListNumberingType.ROMAN_LOWER));
        lists.add(new List(Property.ListNumberingType.ROMAN_UPPER));
        lists.add(new List(Property.ListNumberingType.ENGLISH_LOWER));
        lists.add(new List(Property.ListNumberingType.ENGLISH_UPPER));
        lists.add(new List(Property.ListNumberingType.GREEK_LOWER));
        lists.add(new List(Property.ListNumberingType.GREEK_UPPER));

        for (int i = 1; i <= 30; i++) {
            for (List list : lists) {
                list.add("Item #" + i);
            }
        }

        for (List list : lists) {
            document.add(list).add(new AreaBreak());
        }

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void divInListItemTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "divInListItemTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_divInListItemTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        ListItem item = new ListItem();
        item.add(new Div().add(new Paragraph("text")));
        document.add(new List().add(item));
        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listOverflowTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listOverflowTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listOverflowTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        Paragraph p = new Paragraph("Test String");
        List list = new List(Property.ListNumberingType.DECIMAL).
                add("first string").
                add("second string").
                add("third string").
                add("fourth string");

        for (int i = 0; i < 28; i++){
            document.add(p);
        }

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listOverflowTest02() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listOverflowTest02.pdf";
        String cmpFileName = sourceFolder + "cmp_listOverflowTest02.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        Paragraph p = new Paragraph("Test String");
        List list = new List(Property.ListNumberingType.DECIMAL).
                add("first string");
        ListItem item = new ListItem("second string").add(new Paragraph("third string"));
        list.add(item).
            add("fourth item");

        for (int i = 0; i < 28; i++){
            document.add(p);
        }

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listOverflowTest03() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listOverflowTest03.pdf";
        String cmpFileName = sourceFolder + "cmp_listOverflowTest03.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        Paragraph p = new Paragraph("Test String");
        List list = new List(Property.ListNumberingType.DECIMAL).
                setItemStartIndex(10).
                add("first string").
                add("second string").
                add("third string").
                add("fourth string");

        for (int i = 0; i < 28; i++){
            document.add(p);
        }

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void listEmptyItemTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "listEmptyItemTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_listEmptyItemTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        List list = new List(Property.ListNumberingType.GREEK_LOWER);
        list.add(new ListItem()).add(new ListItem()).add(new ListItem()).add("123").add((ListItem) new ListItem().add(new Div()));

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void imageInListTest01() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "imageInListTest01.pdf";
        String cmpFileName = sourceFolder + "cmp_imageInListTest01.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));

        Document document = new Document(pdfDocument);

        List list = new List(Property.ListNumberingType.GREEK_LOWER);
        PdfImageXObject xObject = new PdfImageXObject(ImageFactory.getImage(sourceFolder + "Desert.jpg"));
        Image image = new Image(xObject, 100);
        list.add(new ListItem()).add(new ListItem(image)).add(new ListItem()).add("123").add((ListItem) new ListItem().add(new Div().setHeight(70).setBackgroundColor(Color.RED)));

        document.add(list);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

}