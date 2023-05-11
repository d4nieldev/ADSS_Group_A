package BusinessLayer.Suppliers;

import DataAccessLayer.DTOs.ContactDTO;

public class Contact {
    private String phone;
    private String name;
    private ContactDTO contactDTO;

    // // Constructor
    // public Contact(String phone, String name) {
    //     this.phone = phone;
    //     this.name = name;
    // }

    public Contact(ContactDTO contactDTO) {
        this.phone = contactDTO.getPhone();
        this.name = contactDTO.getName();
        this.contactDTO = contactDTO;
    }

    // Getter and setter for phone
    public String getPhone() {
        return phone;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        contactDTO.setName(name);
    }

    // Get DTO
    public ContactDTO getContactDTO() {
        return contactDTO;
    }

    // Equal override
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) obj;
        return this.phone == other.phone && this.name == other.name;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
