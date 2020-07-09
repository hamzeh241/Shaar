package ir.tdaapp.diako.shaar.Model;

/**
 * Created by Diako on 6/17/2019.
 */

public class Id_FeaturesGroup {
    private int GroupId;
    private int FeaturesId;

    public Id_FeaturesGroup(int Id,int FeaturesId){
        this.GroupId=Id;
        this.FeaturesId=FeaturesId;
    }


    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getFeaturesId() {
        return FeaturesId;
    }

    public void setFeaturesId(int featuresId) {
        FeaturesId = featuresId;
    }
}
