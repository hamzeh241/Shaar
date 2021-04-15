package ir.tdaapp.diako.shaar.Volley.ViewModel;

import org.json.JSONObject;

import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class ResaultGetJsonObjectVolley {
    private ResaultCode resault;
    private JSONObject object;
    private String Message;

    public ResaultCode getResault() {
        return resault;
    }

    public JSONObject getObject() {
        return object;
    }

    public void setResault(ResaultCode resault) {
        this.resault = resault;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
