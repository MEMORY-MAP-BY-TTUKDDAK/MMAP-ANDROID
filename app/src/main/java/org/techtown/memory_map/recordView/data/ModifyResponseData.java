package org.techtown.memory_map;

import com.google.gson.annotations.SerializedName;

public class ModifyResponseData {
    @SerializedName("fieldCount")
    private int fieldCount;

    @SerializedName("affectedRows")
    private int affectedRows;

    @SerializedName("insertId")
    private int insertId;

    @SerializedName("serverStatus")
    private int serverStatus;

    @SerializedName("warningCount")
    private int warningCount;

    @SerializedName("message")
    private String message;

    @SerializedName("protocol41")
    private boolean protocol41;

    @SerializedName("changedRows")
    private int changedRows;

    public ModifyResponseData(int fieldCount, int affectedRows, int insertId, int serverStatus, int warningCount, String message, boolean protocol41, int changedRows) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.serverStatus = serverStatus;
        this.warningCount = warningCount;
        this.message = message;
        this.protocol41 = protocol41;
        this.changedRows = changedRows;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public int getAffectedRows() {
        return affectedRows;
    }

    public int getInsertId() {
        return insertId;
    }

    public int getServerStatus() {
        return serverStatus;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public String getMessage() {
        return message;
    }

    public boolean isProtocol41() {
        return protocol41;
    }

    public int getChangedRows() {
        return changedRows;
    }
}
