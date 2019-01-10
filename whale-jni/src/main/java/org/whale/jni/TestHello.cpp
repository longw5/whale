#include "E:\eclipse\myspace\whale\whale-jni\src\main\java\org\whale\jni\jni.h"
#include "TestHello.h"
JNIEXPORT void JNICALL Java_TestHello_hello(JNIEnv *, jclass, jstring){
    printf("Hello world!\n");
    return;
}