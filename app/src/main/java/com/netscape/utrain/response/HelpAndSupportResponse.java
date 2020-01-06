package com.netscape.utrain.response;

import com.netscape.utrain.model.ErrorModel;

public class HelpAndSupportResponse {

    /**
     * status : true
     * code : 201
     * data : {"message":"Submitted Successfully","contact":{"message":"hello i have some issues","media":"1578319667955.png","created_by":424,"updated_at":"2020-01-06 19:37:47","created_at":"2020-01-06 19:37:47","id":2}}
     */

    private boolean status;
    private int code;
    private DataBean data;
    private ErrorModel errorModel;

    public ErrorModel getErrorModel() {
        return errorModel;
    }

    public void setErrorModel(ErrorModel errorModel) {
        this.errorModel = errorModel;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * message : Submitted Successfully
         * contact : {"message":"hello i have some issues","media":"1578319667955.png","created_by":424,"updated_at":"2020-01-06 19:37:47","created_at":"2020-01-06 19:37:47","id":2}
         */

        private String message;
        private ContactBean contact;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ContactBean getContact() {
            return contact;
        }

        public void setContact(ContactBean contact) {
            this.contact = contact;
        }

        public static class ContactBean {
            /**
             * message : hello i have some issues
             * media : 1578319667955.png
             * created_by : 424
             * updated_at : 2020-01-06 19:37:47
             * created_at : 2020-01-06 19:37:47
             * id : 2
             */

            private String message;
            private String media;
            private int created_by;
            private String updated_at;
            private String created_at;
            private int id;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getMedia() {
                return media;
            }

            public void setMedia(String media) {
                this.media = media;
            }

            public int getCreated_by() {
                return created_by;
            }

            public void setCreated_by(int created_by) {
                this.created_by = created_by;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
