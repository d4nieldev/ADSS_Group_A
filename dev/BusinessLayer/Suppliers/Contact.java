package BusinessLayer.Suppliers;

public class Contact {
    private String phone;
    private int supplierId;
    private String name;

    //Constructor
    public Contact(String phone, int supplierId, String name) {
        this.phone = phone;
        this.supplierId = supplierId;
        this.name = name;
    }

    //Getter and setter for phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Equal override
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) obj;
        return this.phone==other.phone && this.name==other.name && this.supplierId==other.supplierId;
    }

}
