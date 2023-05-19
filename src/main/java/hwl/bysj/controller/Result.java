package hwl.bysj.controller;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private String code; //200是正常，非200不正常
    private String msg;
    private Object data;

    public static Result success(Object data) {
        Result r = new Result();
        r.setCode("200");
        r.setData(data);
        r.setMsg("操作成功");
        return r;
    }
    public static Result fail(String msg) {
        Result r = new Result();
        r.setCode("500");
        r.setData(null);
        r.setMsg(msg);
        return r;

    }

}
