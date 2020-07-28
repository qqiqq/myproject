package spark;

public class words {
    public static void main(String[] args){
        if(args.length<3){
            System.out.println("method need thow parameter for [java|scala] [file_path] and [output_path]");
            System.exit(1);
        }

        if(args[0].equals("java")){
            try {
                wordCountJava wc = new wordCountJava(args[1],args[2]);
                wc.count();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(args[0].equals("scala")){
            try{
                wordCountScala wc = new wordCountScala();
                wc.wordCount(args[1],args[2]);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("you enter wrong option for language: " + args[0]);
            System.exit(1);
        }
    }
}
