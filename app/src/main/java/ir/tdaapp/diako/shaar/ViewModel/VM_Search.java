package ir.tdaapp.diako.shaar.ViewModel;

import java.math.BigInteger;

/**
 * Created by Diako on 7/17/2019.
 */

public class VM_Search {

    private int Target=1;
    private int TypeHome=0;
    private int CountRoom;
    private BigInteger MinPrice,MaxPrice;
    private BigInteger MinMortgage,MaxMortgage;
    private BigInteger MinArea,MaxArea;
    private int LocationId;
    private int Page=0;
    private boolean FullRent=false;
    private boolean FullMortgage=false;
    private boolean Old_New=false;
    private boolean New_old=false;
    private boolean Expensiv_Cheap=false;
    private boolean Cheap_Expensiv=false;
    private boolean Room_l_h=false;
    private boolean Room_h_l=false;

    public VM_Search(){}

    public int getTypeHome() {
        return TypeHome;
    }

    public void setTypeHome(int typeHome) {
        TypeHome = typeHome;
    }

    public int getCountRoom() {
        return CountRoom;
    }

    public void setCountRoom(int countRoom) {
        CountRoom = countRoom;
    }

    public BigInteger getMinPrice() {
        return MinPrice;
    }

    public void setMinPrice(BigInteger minPrice) {
        MinPrice = minPrice;
    }

    public BigInteger getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(BigInteger maxPrice) {
        MaxPrice = maxPrice;
    }

    public BigInteger getMinMortgage() {
        return MinMortgage;
    }

    public void setMinMortgage(BigInteger minMortgage) {
        MinMortgage = minMortgage;
    }

    public BigInteger getMaxMortgage() {
        return MaxMortgage;
    }

    public void setMaxMortgage(BigInteger maxMortgage) {
        MaxMortgage = maxMortgage;
    }

    public BigInteger getMinArea() {
        return MinArea;
    }

    public void setMinArea(BigInteger minArea) {
        MinArea = minArea;
    }

    public BigInteger getMaxArea() {
        return MaxArea;
    }

    public void setMaxArea(BigInteger maxArea) {
        MaxArea = maxArea;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public boolean isFullRent() {
        return FullRent;
    }

    public void setFullRent(boolean fullRent) {
        FullRent = fullRent;
    }

    public boolean isFullMortgage() {
        return FullMortgage;
    }

    public void setFullMortgage(boolean fullMortgage) {
        FullMortgage = fullMortgage;
    }

    public boolean isOld_New() {
        return Old_New;
    }

    public void setOld_New(boolean old_New) {
        Old_New = old_New;
    }

    public boolean isNew_old() {
        return New_old;
    }

    public void setNew_old(boolean new_old) {
        New_old = new_old;
    }

    public boolean isExpensiv_Cheap() {
        return Expensiv_Cheap;
    }

    public void setExpensiv_Cheap(boolean expensiv_Cheap) {
        Expensiv_Cheap = expensiv_Cheap;
    }

    public boolean isCheap_Expensiv() {
        return Cheap_Expensiv;
    }

    public void setCheap_Expensiv(boolean cheap_Expensiv) {
        Cheap_Expensiv = cheap_Expensiv;
    }

    public boolean isRoom_l_h() {
        return Room_l_h;
    }

    public void setRoom_l_h(boolean room_l_h) {
        Room_l_h = room_l_h;
    }

    public boolean isRoom_h_l() {
        return Room_h_l;
    }

    public void setRoom_h_l(boolean room_h_l) {
        Room_h_l = room_h_l;
    }
}
