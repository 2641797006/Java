/////////////////////////////////////////////////////////////////////////////////////////////
/**/                                                                                     /**/
/**/                                 import java.util.*;                                 /**/
/**/                                  import java.io.*;                                  /**/
/**/                                                                                     /**/
/**/                                  public class xx2{                                  /**/
/**/              public static void main(String[] args) throws Exception {              /**/
/**/                                if (args.length < 1) {                               /**/
/**/                           println("error: No input file");                          /**/
/**/                                       return;                                       /**/
/**/                                          }                                          /**/
/**/                    InputStream f = new FileInputStream(args[0]);                    /**/
/**/                 InputStreamReader reader = new InputStreamReader(f);                /**/
/**/                      StringBuffer sb = new StringBuffer("\n");                      /**/
/**/                                                                                     /**/
/**/                                  int c, hasBlank=0;                                 /**/
/**/                                      for (;;) {                                     /**/
/**/                                  c = reader.read();                                 /**/
/**/                                     if (c == -1)                                    /**/
/**/                                        break;                                       /**/
/**/                                    if (c == '\t')                                   /**/
/**/                                       c = ' ';                                      /**/
/**/                                    if (c == ' ')                                    /**/
/**/                                  if (hasBlank != 0)                                 /**/
/**/                                      continue;                                      /**/
/**/                                         else                                        /**/
/**/                                    hasBlank = 1;                                    /**/
/**/                                         else                                        /**/
/**/                                    hasBlank = 0;                                    /**/
/**/                                 sb.append((char)c);                                 /**/
/**/                                          }                                          /**/
/**/                                   sb.append('\n');                                  /**/
/**/                                                                                     /**/
/**/                                 Box box = new Box();                                /**/
/**/                               box.set(sb.toString());                               /**/
/**/                                     print(box);                                     /**/
/**/                                          }                                          /**/
/**/                                                                                     /**/
/**/                 static <T> void print(T t) { System.out.print(t); }                 /**/
/**/               static <T> void println(T t) { System.out.println(t); }               /**/
/**/   static void printf(String fmt, Object... args) { System.out.printf(fmt, args); }  /**/
/**/                                          }                                          /**/
/**/                                                                                     /**/
/////////////////////////////////////////////////////////////////////////////////////////////
