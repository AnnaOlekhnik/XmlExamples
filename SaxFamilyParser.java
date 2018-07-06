package by.htp.xml.parser.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.htp.xml.entity.Families;
import by.htp.xml.entity.Family;
import by.htp.xml.parser.FamilyParser;

public class SaxFamilyParser implements FamilyParser {

	public List<Family> parseFamilyDoc(String path) {

		List<Family> familyList = null;
		try {
			SaxFamilyHandler handler = new SaxFamilyHandler();
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
			reader.parse(path);

			familyList = handler.getFamilyList();

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return familyList;
	}

}
