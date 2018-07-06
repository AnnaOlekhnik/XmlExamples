package by.htp.xml.entity;

public class Father extends FamilyMember {

	private boolean military;

	public Father() {
		
	}

	public boolean isMilitary() {
		return military;
	}

	public void setMilitary(boolean military) {
		this.military = military;
	}

	@Override
	public String toString() {
		return "Father [military=" + military + ", Name= " + getName() + ", Surname= " + getSurname()
				+ ", Age= " + getAge() + "]";
	}

}
