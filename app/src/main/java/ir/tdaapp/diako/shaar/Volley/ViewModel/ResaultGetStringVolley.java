package ir.tdaapp.diako.shaar.Volley.ViewModel;


import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class ResaultGetStringVolley {

    private ResaultCode resault;
    private String request;
    private String Message;

    public ResaultCode getResault() {
        return resault;
    }

    public void setResault(ResaultCode resault) {
        this.resault = resault;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
