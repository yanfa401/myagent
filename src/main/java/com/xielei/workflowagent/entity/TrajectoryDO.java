package com.xielei.workflowagent.entity;

/**
 * @author xielei
 * @date 2023/6/26 11:12
 */
public class TrajectoryDO {

    private String uuid;

    private String fieldName;

    private int seq;

    private String className;

    private String lineNumber;

    private String methodName;

    private String value;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TrajectoryDO{" +
                "uuid='" + uuid + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", seq=" + seq +
                ", className='" + className + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", methodName='" + methodName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
