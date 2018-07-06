package by.htp.xml.parser.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.htp.xml.entity.FamilyEnum;
import by.htp.xml.entity.FamilyMember;
import by.htp.xml.entity.Father;
import by.htp.xml.entity.Mother;
import by.htp.xml.entity.Child;
import by.htp.xml.entity.Family;
import by.htp.xml.parser.FamilyParser;

public class StaxFamilyParser implements FamilyParser {

	private List<Family> families = new ArrayList();
	private Family family;
	private List<Child> children = new ArrayList<Child>();
	private Father father = new Father();
	private FamilyMember current = new FamilyMember();
	private Mother mother = new Mother();
	private Child child = new Child();

	@Override
	public List<Family> parseFamilyDoc(String path) {

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		try {
			InputStream input = new FileInputStream(path);
			XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
			families = process(reader);

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return families;
	}

	private List<Family> process(XMLStreamReader reader) {

		Family family = null;
		FamilyEnum en = null;
		try {
			while (reader.hasNext()) {
				int type = reader.next();
				switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					String localName = reader.getLocalName();
					en = FamilyEnum.valueOf(localName.toUpperCase().replace("-", "_"));
					switch (en) {
					case FAMILY:
						family = new Family();
						Integer id = Integer.parseInt(reader.getAttributeValue(null, "id"));
						family.setId(id);
						break;
					case MOTHER:
						mother = new Mother();
						String maidenName = reader.getAttributeValue(null, "maiden-name");
						mother.setMaidenName(maidenName);
						current = mother;
						break;
					case FATHER:
						father = new Father();
						current = father;
						break;
					case CHILD:
						child = new Child();
						current = child;
						break;
					case MILITARY:
						father.setMilitary(true);
						break;
					}
					break;

				case XMLStreamConstants.CHARACTERS:
					String text = reader.getText().trim();
					if (text.isEmpty()) {
						break;
					}
					switch (en) {
					case NAME:
						current.setName(text);
						break;
					case SURNAME:
						current.setSurname(text);
						break;
					case AGE:
						current.setAge(Integer.valueOf(text));
						break;

					case GENDER:
						child.setGender(text);
						break;
					}
					break;

				case XMLStreamConstants.END_ELEMENT:
					localName = reader.getLocalName();
					en = FamilyEnum.valueOf(localName.toUpperCase().replace("-", "_"));
					switch (en) {
					case FAMILY:
						families.add(family);
						break;
					case MOTHER:
						family.setMother(mother);
						break;
					case FATHER:
						family.setFather(father);
						break;
					case CHILD:
						children.add(child);
						break;
					case CHILDREN:
						family.setChildren(children);
						children = new ArrayList();
					}
				}
			}
		} catch (

		NumberFormatException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return families;
	}
}
