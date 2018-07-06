package by.htp.xml.entity;

public class Child extends FamilyMember {

	private String gender;

	public Child() {
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String toString() {
		return getName().toString() + " | " + getSurname().toString() + " | " + getAge() + " years old " + " | "
				+ getGender().toString();
	}

}
