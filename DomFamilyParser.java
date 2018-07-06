package by.htp.xml.parser.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.htp.xml.entity.Child;
import by.htp.xml.entity.Family;
import by.htp.xml.entity.FamilyMember;
import by.htp.xml.entity.Father;
import by.htp.xml.entity.Mother;
import by.htp.xml.parser.FamilyParser;

public class DomFamilyParser implements FamilyParser {
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Family> parseFamilyDoc(String path) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Family family = new Family();
		List<Family> families = new ArrayList();
		Mother mother = new Mother();
		Father father = new Father();
		Child child = new Child();
		List<Child> children = new ArrayList();
		FamilyMember current = new FamilyMember();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(path);

			NodeList familyList = document.getElementsByTagName("family");
			
			for (int i = 0; i < familyList.getLength(); i++) {
				Node f = familyList.item(i);
				family = new Family();
				children = new ArrayList();
				
				if (f.getNodeType() == Node.ELEMENT_NODE) {
					Element familyElement = (Element) f;
					family.setId(Integer.parseInt(familyElement.getAttribute("id")));
					NodeList familyMember = familyElement.getChildNodes();
					
					for (int j = 0; j < familyMember.getLength(); j++) {
						Node member = familyMember.item(j);
						
						if (member.getNodeType() == Node.ELEMENT_NODE) {
							Element memberElement = (Element) member;
							current = new FamilyMember();

							if ((member.getNodeName()).equals("mother")) {
								mother = (Mother) createMemberOfFamily(mother, memberElement);
								String maidenName = memberElement.getAttribute("maiden-name");
								mother.setMaidenName(maidenName);
								family.setMother(mother);
								mother = new Mother();
							}
							if ((member.getNodeName()).equals("father")) {
								father = (Father) createMemberOfFamily(father, memberElement);
								if (memberElement.getElementsByTagName("military").getLength() > 0) {
									father.setMilitary(true);
								} else {
									father.setMilitary(false);
								}
								family.setFather(father);
								father = new Father();
							}
							if ((member.getNodeName()).equals("children")) {
								NodeList childrenList = memberElement.getChildNodes();
								
								for (int h = 0; h < childrenList.getLength(); h++) {
									Node childrenMember = childrenList.item(h);
									
									if (childrenMember.getNodeType() == Node.ELEMENT_NODE) {
										Element childElement = (Element) childrenMember;
										child = (Child) createMemberOfFamily(child, childElement);
										child.setGender(
												memberElement.getElementsByTagName("gender").item(0).getTextContent());
										children.add(child);
										child = new Child();
									}
								}
								family.setChildren(children);
							}
						}
					}
				}
				families.add(family);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return families;
	}

	public FamilyMember createMemberOfFamily(FamilyMember current, Element memberElement) {

		current.setName(memberElement.getElementsByTagName("name").item(0).getTextContent());
		current.setSurname(memberElement.getElementsByTagName("surname").item(0).getTextContent());
		current.setAge(Integer.valueOf(memberElement.getElementsByTagName("age").item(0).getTextContent()));
		
		return current;
	}

}
