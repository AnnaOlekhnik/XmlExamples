package by.htp.xml.entity;

public class Mother extends FamilyMember {

	@Override
	public String toString() {
		return "Mother [maidenName= " + maidenName + ", Name= " + getName() + ", Surname=" + getSurname()
				+ ", Age= " + getAge() + "]";
	}

	private String maidenName;

	public Mother() {
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

}
