package ir.tdaapp.diako.shaar.Volley.ViewModel;

import org.json.JSONArray;

import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class ResaultGetJsonArrayVolley {
    private ResaultCode resault;
    private JSONArray jsonArray;
    private String Message="";

    public ResaultCode getResault() {
        return resault;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setResault(ResaultCode resault) {
        this.resault = resault;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
