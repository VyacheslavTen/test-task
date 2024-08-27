package core;

import java.util.ArrayList;

public class UserListData {

        private boolean isSuccess;

        private int errorCode;

        private Object errorMessage;

        private ArrayList<Integer> idList;

        private String gender;

        public UserListData(boolean isSuccess, int errorCode, Object errorMessage, ArrayList<Integer> idList) {
            this.isSuccess = isSuccess;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.idList = idList;

    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public ArrayList<Integer> getIdList() {
        return idList;
    }

    public UserListData(String gender) {
            this.gender = gender;
    }
}
