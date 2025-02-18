.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 invokestatic Output/read()I
 istore 0
 iload 0
 ldc 10
 if_icmplt L1
 goto L2
L1:
 iload 0
 ldc 1
 iadd 
 dup
 istore 0
 pop
 iload 0
 invokestatic Output/print(I)V
L2:
 invokestatic Output/read()I
 istore 0
 invokestatic Output/read()I
 istore 1
 invokestatic Output/read()I
 istore 2
 iload 0
 iload 1
 if_icmpgt L3
 goto L4
L3:
 iload 0
 iload 2
 if_icmpgt L5
 goto L6
L5:
 iload 0
 invokestatic Output/print(I)V
 goto L7
L6:
 iload 2
 invokestatic Output/print(I)V
L7:
 goto L8
L4:
 iload 1
 iload 2
 if_icmpgt L9
 goto L10
L9:
 iload 1
 invokestatic Output/print(I)V
 goto L11
L10:
 iload 2
 invokestatic Output/print(I)V
L11:
L8:
 invokestatic Output/read()I
 istore 1
 ldc 0
 istore 0
L12:
 iload 0
 iload 1
 if_icmplt L13
 goto L14
L13:
 iload 0
 invokestatic Output/print(I)V
 iload 0
 ldc 1
 iadd 
 dup
 istore 0
 pop
 goto L12
L14:
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

