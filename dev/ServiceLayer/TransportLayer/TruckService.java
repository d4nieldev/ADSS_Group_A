package ServiceLayer.TransportLayer;

import BussinessLayer.TransPortLayer.TruckFacade;

public class TruckService {

    private TruckFacade truckFacade = TruckFacade.getInstance();

    /**
     * create new truck and return message
     *
     * @return
     */
    public String addTruck(String plateNumber, String model, int weightNeto, int weightMax){
        truckFacade.newTrack(plateNumber, model, weightNeto, weightMax);
        return "New truck add to facade";
    }

    /**
     * remove truck from facade and return message
     *
     * @return
     */
    public String removeTruck(String plateNumber){
        truckFacade.removeTruck(plateNumber);
        return "The truck" + plateNumber + "remove from Facade";
    }

    /**
     * print details of truck
     *
     * @return
     */
    public String printTruck(String plateNum){
        if (!truckFacade.hasTruck(plateNum))
            return "there no truck with this plate Number";
        return truckFacade.getTruck(plateNum).toString();
    }

    /**
     * return if the truck is available
     *
     * @return
     */
    public String isAvailable(String plateNum){
        if (!truckFacade.hasTruck(plateNum))
            return "there no truck with this plate Number";
        else if(truckFacade.getTruck(plateNum).isAvailable())
            return "true";
        else
            return "false";
    }

    /**
     * set weight neto of truck
     *
     * @return
     */
    public String setWeightNeto(String plate, int news){
        if (!truckFacade.hasTruck(plate))
            return "there no truck with this plate Number";
        else{
            truckFacade.getTruck(plate).setWeightNeto(news);
            return "success";
        }
    }

    /**
     * set plate number of truck
     *
     * @return
     */
    public String setPlateNumber(String plate, String news){
        if (!truckFacade.hasTruck(plate))
            return "there no truck with this plate Number";
        else{
            truckFacade.getTruck(plate).setPlateNumber(news);
            return "success";
        }
    }

    /**
     * set model of truck
     *
     * @return
     */
    public String setModel(String plate, String model){
        if (!truckFacade.hasTruck(plate))
            return "there no truck with this plate Number";
        else{
            truckFacade.getTruck(plate).setModel(model);
            return "success";
        }
    }


    /**
     * set weight max of truck
     *
     * @return
     */
    public String setWeightMax(String plate, int news){
        if (!truckFacade.hasTruck(plate))
            return "there no truck with this plate Number";
        else{
            truckFacade.getTruck(plate).setWeightMax(news);
            return "success";
        }
    }


    /**
     * set status of available of truck
     *
     * @return
     */
    public String setAvailable(String plate, boolean news){
        if (!truckFacade.hasTruck(plate))
            return "there no truck with this plate Number";
        else{
            truckFacade.getTruck(plate).setAvailable(news);
            return "success";
        }
    }

}
