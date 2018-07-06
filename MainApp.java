package by.htp.xml.run;

import java.util.List;

import by.htp.xml.entity.Families;
import by.htp.xml.entity.Family;
import by.htp.xml.parser.FamilyParser;
import by.htp.xml.parser.dom.DomFamilyParser;
import by.htp.xml.parser.sax.SaxFamilyParser;
import by.htp.xml.parser.stax.StaxFamilyParser;

public class MainApp {

	public static void main(String[] args) {

//		FamilyParser parser = new StaxFamilyParser();
		 FamilyParser parser = new DomFamilyParser();
		// FamilyParser parser = new SaxFamilyParser();
		// Families families = parser.parseFamilyDoc("resources/family.xml");
		List<Family> families = parser.parseFamilyDoc("resources/family.xml");

		for (Family family : families) {
			System.out.println(family.toString());
		}
	}

}
