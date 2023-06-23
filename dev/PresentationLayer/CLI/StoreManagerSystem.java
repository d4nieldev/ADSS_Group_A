package PresentationLayer.CLI;

public class StoreManagerSystem {
    HRSystem hrSystem;
    MemberSystem memberSystem;
    TransportSystem transportSystem;
    DriverSystem driverSystem;

    public StoreManagerSystem(HRSystem hrSystem, MemberSystem memberSystem, TransportSystem transportSystem, DriverSystem driverSystem) {
        this.hrSystem = hrSystem;
        this.memberSystem = memberSystem;
        this.transportSystem = transportSystem;
        this.driverSystem = driverSystem;
    }

    public void run(int loginId, int menu) {
        if (menu == 1) {
            hrSystem.run(loginId);
        }
        else if (menu == 2) {
            memberSystem.run(loginId);
        }
        else if (menu == 3) {
            transportSystem.run(loginId);
        }
        else if (menu == 4) {
            driverSystem.run(loginId);
        }
        else
            throw new IllegalArgumentException("Unknown menu: " + menu);
    }
}
