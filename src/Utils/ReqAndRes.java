package Utils;

public class ReqAndRes {
   public class BasicResp{
       String message;
       int status;

       BasicResp(){}
       public BasicResp(String message, int status) {
           this.message = message;
           this.status = status;
       }
   }
}
