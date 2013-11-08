package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

public class Foods {

	private int _id;
	private int name;
	private int is_kosher;
	private int information;

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getIs_kosher() {
		return is_kosher;
	}

	public void setIs_kosher(int is_kosher) {
		this.is_kosher = is_kosher;
	}

	public int getInformation() {
		return information;
	}

	public void setInformation(int information) {
		this.information = information;
	}

}
