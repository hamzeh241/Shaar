package ir.tdaapp.diako.shaar.ViewModel;

/**
 * Created by Diako on 7/5/2019.
 */

public class VM_ItemFeature {

    private String Value;
    private int FkFeatures;
    private int FkFormat;
    private String Title;

    public VM_ItemFeature(String Value, int FkFeatures, int FkFormat) {
        this.FkFeatures = FkFeatures;
        this.Value = Value;
        this.FkFormat = FkFormat;
    }

    public VM_ItemFeature() {
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public int getFkFeatures() {
        return FkFeatures;
    }

    public void setFkFeatures(int fkFeatures) {
        FkFeatures = fkFeatures;
    }

    public int getFkFormat() {
        return FkFormat;
    }

    public void setFkFormat(int fkFormat) {
        FkFormat = fkFormat;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
