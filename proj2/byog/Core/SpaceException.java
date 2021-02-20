package byog.Core;

public class SpaceException extends Exception{
    String message; //定义String类型变量
    public SpaceException(String ErrorMessage) {  //父类方法
        message = ErrorMessage;
    }
}
