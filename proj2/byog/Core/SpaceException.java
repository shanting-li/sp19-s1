package byog.Core;

import java.io.Serializable;

public class SpaceException extends Exception implements Serializable {
    String message; //定义String类型变量
    public SpaceException(String ErrorMessage) {  //父类方法
        message = ErrorMessage;
    }
}
