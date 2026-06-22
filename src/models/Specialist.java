package models;

// LSP: Specialist IS-A Doctor and can be used anywhere Doctor is expected
// without breaking behaviour — getSpecialization(), getName(), getId() all work correctly
public class Specialist extends Doctor {
    private String subSpecialization;
    private int    yearsOfExperience;

    public Specialist(int id, String name, String specialization,
                      String subSpecialization, int yearsOfExperience) {
        super(id, name, specialization);
        this.subSpecialization = subSpecialization;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getSubSpecialization() { return subSpecialization; }
    public int    getYearsOfExperience() { return yearsOfExperience; }

    @Override
    public String toString() {
        return "Specialist{id=" + getId() + ", name='" + getName() +
               "', specialization='" + getSpecialization() +
               "', sub='" + subSpecialization + "', experience=" + yearsOfExperience + "yrs}";
    }
}
