package com.netscape.utrain.response;

import com.netscape.utrain.model.A_SpaceListModel;

public class SpaceDetailResponse {

    /**
     * status : true
     * code : 200
     * data : {"id":12,"name":"Boxing","images":"[\"1573300983663.jpeg\"]","description":"gold medalist only","price_hourly":48,"price_daily":50,"availability_week":"Friday-Thursday","open_hours_from":"17:32:00","open_hours_to":"22:32:00","location":"A-40 phase 8B, Industrial Area, Sahibzada Ajit Singh Nagar, Punjab, India","latitude":"30.707549476138578","longitude":"76.69786602258682","created_by":297,"params":null,"state":"0","created_at":"2019-11-09 12:03:03","updated_at":"2019-11-09 12:03:03","deleted_at":null}
     */

    private boolean status;
    private int code;
    private A_SpaceListModel data;

    public A_SpaceListModel getData() {
        return data;
    }

    public void setData(A_SpaceListModel data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
