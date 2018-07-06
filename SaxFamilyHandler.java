package by.htp.xml.parser.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.htp.xml.entity.Child;
import by.htp.xml.entity.Family;
import by.htp.xml.entity.FamilyEnum;
import by.htp.xml.entity.FamilyMember;
import by.htp.xml.entity.Father;
import by.htp.xml.entity.Mother;

public class SaxFamilyHandler extends DefaultHandler {

	private List<Family> families = new ArrayList();
	private Family family;
	private List<Child> children = new ArrayList<Child>();
	private Father father = new Father();
	private FamilyMember current = new FamilyMember();
	private Mother mother = new Mother();
	private Child child = new Child();
	private StringBuilder text;

	public List<Family> getFamilyList() {
		return families;
	}

	public void startDocument() throws SAXException {
		System.out.println("Parsing started.");
	}

	public void endDocument() throws SAXException {
		System.out.println("Parsing ended.");
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		System.out.println("startElement -> " + " localName:" + localName + ", qName: " + qName);
		text = new StringBuilder();
		
		if (qName.equals("family")) {
			family = new Family();
			family.setId((Integer.parseInt(attributes.getValue("id"))));
		}
		if (qName.equals("mother")) {
			mother = new Mother();
			mother.setMaidenName(attributes.getValue("maiden-name"));
			current = mother;
		}
		if (qName.equals("father")) {
			father = new Father();
			current = father;
		}
		if (qName.equals("child")) {
			child = new Child();
			current = child;
		}
	}

	public void characters(char[] buffer, int start, int length) {
		text.append(buffer, start, length);

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		FamilyEnum en = FamilyEnum.valueOf(localName.toUpperCase().replace("-", "_"));
		switch (en) {
		case NAME:
			current.setName(text.toString());
			break;
		case SURNAME:
			current.setSurname(text.toString());
			break;
		case AGE:
			current.setAge(Integer.parseInt(text.toString()));
			break;
		case MOTHER:
			current = mother;
			family.setMother(mother);
			break;
		case MILITARY:
			father.setMilitary(true);
			break;
		case GENDER:
			child.setGender(text.toString());
		case FATHER:
			family.setFather(father);
			break;
		case CHILD:
			current = child;
			children.add(child);
			break;
		case CHILDREN:
			family.setChildren(children);
			children = new ArrayList();
			break;
		case FAMILY:
			families.add(family);//add(family);
			System.out.println();
			System.out.println();
			break;
		}

	}

}
