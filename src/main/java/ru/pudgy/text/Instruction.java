package ru.pudgy.text;



public interface Instruction {
    boolean isSubjectOnly();
    boolean isPredicate();
    boolean isObjectOnly();
    Object getSubject();
    void setSubject(Object subject);
    Object getAction();
    void setAction(Object action);
    Object getObject();
    void setObject(Object object);
    double compare(Instruction other);
}
